package centre.ajial.webApi.services;

import centre.ajial.webApi.mail.SendEmail;
import centre.ajial.webApi.models.Person;
import centre.ajial.webApi.models.Student;
import centre.ajial.webApi.repositories.PersonRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.awt.print.Pageable;
import java.util.Optional;


@Service
@AllArgsConstructor
public class StudentService {

    private final PersonRepo repo;
    private SendEmail send;

    public ResponseEntity<?> save(Student student, BindingResult result) {
        //test the constraints
        if (result.hasErrors())
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        else if (repo.existsByEmail(student.getEmail()))
            return new ResponseEntity<>("Email exist in the system!", HttpStatus.BAD_REQUEST);
        else if (repo.existsByCin(student.getCin()))
            return new ResponseEntity<>("CIN exist in the system!", HttpStatus.BAD_REQUEST);
        else if (repo.searchByCne(student.getCne()) > 0)
            return new ResponseEntity<>("CNE exist in the system!", HttpStatus.BAD_REQUEST);

        String password = student.getPassword();
        student.setPassword(PersonRepo.ENCODER.encode(student.getPassword()));
        repo.save(student);
        try {
            send.sendEmail(student, password);
        } catch (Exception e) {
            repo.delete(student);
            return new ResponseEntity<>("Mail error! " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("created!", HttpStatus.OK);
    }

    public ResponseEntity<?> delete(Long id) {
        Optional<Person> person =repo.findById(id);
        if (person.isEmpty())
            return new ResponseEntity<>("No one exist with this id in the system!", HttpStatus.BAD_REQUEST);
        else if (!(person.get() instanceof Student))
            return new ResponseEntity<>("There are no student with this id!", HttpStatus.BAD_REQUEST);
        //actual delete
        repo.deleteById(id);
        return new ResponseEntity<>("Deleted!", HttpStatus.OK);
    }

    public ResponseEntity<?> getStudents(int page, int size) {
        return new ResponseEntity<>(repo.findAllStudents(PageRequest.of(page, size)), HttpStatus.OK);
    }
}
