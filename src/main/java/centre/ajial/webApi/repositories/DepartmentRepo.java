package centre.ajial.webApi.repositories;

import centre.ajial.webApi.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {
    boolean existsByName(String name);
}
