package centre.ajial.webApi.controllers;

import centre.ajial.webApi.models.Department;
import centre.ajial.webApi.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @PostMapping("/department/add")
    public ResponseEntity<?> save(@Valid @RequestBody Department department, BindingResult result) {
        return service.save(department, result);
    }

    @PutMapping("/department/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Department department,
                                    BindingResult rs) {
        return service.update(id, department, rs);
    }

    @DeleteMapping("/department/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @GetMapping("/departments")
    public ResponseEntity<?> get(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int size)
    {
        return service.getDepartments(page, size);
    }

    @GetMapping("/department/{id}/professors")
    public  ResponseEntity<?> getProfessors(@PathVariable Long id) {
        return service.getProfessorsOfaDepartment(id);
    }
}
