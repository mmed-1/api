package centre.ajial.webApi.controllers;

import centre.ajial.webApi.models.Professor;
import centre.ajial.webApi.services.ProfessorService;
import jakarta.validation.Valid;
import org.hibernate.query.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfessorController {

    private final ProfessorService service;

    public ProfessorController(ProfessorService service) {
        this.service = service;
    }

    @PostMapping("/professor/add")
    public ResponseEntity<?> save(@Valid @RequestBody Professor professor, BindingResult result) {
        return service.save(professor, result);
    }

    @DeleteMapping("/professor/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @GetMapping("/professors")
    public ResponseEntity<?> getProfessors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return service.getProfessors(page, size);
    }
}
