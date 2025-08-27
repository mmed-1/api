package centre.ajial.webApi.controllers;

import centre.ajial.webApi.models.Student;
import centre.ajial.webApi.services.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping("/student/add")
    public ResponseEntity<?> save(@Valid @RequestBody Student student, BindingResult result) {
        return service.saveStudent(student, result);
    }
}
