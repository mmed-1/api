package centre.ajial.webApi.services;

import centre.ajial.webApi.mail.SendEmail;
import centre.ajial.webApi.models.Person;
import centre.ajial.webApi.models.Professor;
import centre.ajial.webApi.repositories.PersonRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfessorService {

    private final PersonRepo repo;
    private final SendEmail sendEmail;

    public ResponseEntity<?> save(Professor professor, BindingResult result) {
        if (result.hasErrors())
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        else if (repo.existsByEmail(professor.getEmail()))
            return new ResponseEntity<>("The email professor exist in the system!", HttpStatus.BAD_REQUEST);
        else if (repo.existsByCin(professor.getCin()))
            return new ResponseEntity<>("The cin professor exist in the system!", HttpStatus.BAD_REQUEST);

        String password = professor.getPassword();
        professor.setPassword(PersonRepo.ENCODER.encode(professor.getPassword()));
        repo.save(professor);
        try {
            sendEmail.sendEmail(professor, password);
        } catch (Exception e) {
            repo.delete(professor);
            return new ResponseEntity<>("Mail error! " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Created!", HttpStatus.OK);
    }

    public ResponseEntity<?> delete(Long id) {
        Optional<Person> person = repo.findById(id);
        if (person.isEmpty())
            return new ResponseEntity<>("No one exist with this id in the system!", HttpStatus.BAD_REQUEST);
        else if (!(person.get() instanceof Professor))
            return new ResponseEntity<>("There are no professor with this id!", HttpStatus.BAD_REQUEST);

        repo.deleteById(id);
        return new ResponseEntity<>("Deleted!", HttpStatus.OK);
    }

    public ResponseEntity<?> getProfessors(int page, int size) {
        return new ResponseEntity<>(repo.findAllProfessors(PageRequest.of(page, size)), HttpStatus.OK);
    }
}
