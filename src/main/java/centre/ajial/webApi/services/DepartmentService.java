package centre.ajial.webApi.services;

import centre.ajial.webApi.models.Department;
import centre.ajial.webApi.repositories.DepartmentRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepo repository;

    public DepartmentService(DepartmentRepo repository) {
        this.repository = repository;
    }

    public ResponseEntity<?> save(Department department, BindingResult result) {
        if (result.hasErrors())
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        else if (repository.existsByName(department.getName()))
            return new ResponseEntity<>("This department exist in the system", HttpStatus.BAD_REQUEST);
        repository.save(department);
        return new ResponseEntity<>("created!", HttpStatus.OK);
    }

    public ResponseEntity<?> delete(Long id) {
        Optional<Department> department = repository.findById(id);
        if(department.isEmpty())
            return new ResponseEntity<>("Error with this id", HttpStatus.BAD_REQUEST);
        repository.deleteById(id);
        return new ResponseEntity<>("deleted!", HttpStatus.OK);
    }

    public ResponseEntity<?> update(Long id, Department department, BindingResult rs) {
        if(rs.hasErrors())
            return new ResponseEntity<>(rs.getAllErrors(), HttpStatus.BAD_REQUEST);
        Optional<Department> department1 = repository.findById(id);
        if(department1.isEmpty())
            return new ResponseEntity<>("Error!", HttpStatus.BAD_REQUEST);
        repository.save(department);
        return new ResponseEntity<>("Updated!", HttpStatus.OK);
    }

    public ResponseEntity<?> getDepartments(int page, int size) {
        return new ResponseEntity<>(repository.findAll(PageRequest.of(page, size)), HttpStatus.OK);
    }

    public ResponseEntity<?> getProfessorsOfaDepartment(Long id) {
        Optional<Department> department = repository.findById(id);
        if(department.isEmpty())
            return new ResponseEntity<>("Error with the id!", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(department.get().getProfessors(), HttpStatus.OK);
    }
}
