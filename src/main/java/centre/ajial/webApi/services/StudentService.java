package centre.ajial.webApi.services;

import centre.ajial.webApi.models.Student;
import centre.ajial.webApi.repositories.PersonRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@AllArgsConstructor
public class StudentService {

    private final PersonRepo repo;

    public ResponseEntity<?> saveStudent(Student student, BindingResult result) {
        //test the constraints
        if (result.hasErrors())
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        else if (repo.existsByEmail(student.getEmail()))
            return new ResponseEntity<>("Email exist in the system!", HttpStatus.BAD_REQUEST);
        else if (repo.existsByCin(student.getCin()))
            return new ResponseEntity<>("CIN exist in the system!", HttpStatus.BAD_REQUEST);
        else if (repo.searchByCne(student.getCne()) > 0)
            return new ResponseEntity<>("CNE exist in the system!", HttpStatus.BAD_REQUEST);

        student.setPassword(PersonRepo.ENCODER.encode(student.getPassword()));
        repo.save(student);
        return new ResponseEntity<>("created!", HttpStatus.OK);
    }
}
