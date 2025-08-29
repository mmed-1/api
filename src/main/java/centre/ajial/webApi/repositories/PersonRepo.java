package centre.ajial.webApi.repositories;

import centre.ajial.webApi.models.Person;
import centre.ajial.webApi.models.Professor;
import centre.ajial.webApi.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonRepo extends JpaRepository<Person, Long> {
    BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder(12);

    boolean existsByEmail(String email);
    boolean existsByCin(String cin);
    @Query(
            value = """
                SELECT COUNT(*)
                FROM students s, persons p
                WHERE s.id = p.id AND s.cne = :cne
            """,
            nativeQuery = true
    )
    int searchByCne(@Param("cne") String cne);
    Person findByEmail(String email);

    @Query(
            value = """
                SELECT *
                FROM persons p, students s
                WHERE p.id = s.id
            """,
            countQuery = """
                SELECT COUNT(*)
                FROM persons p, students s
                WHERE p.id = s.id
             """,
            nativeQuery = true
    )
    Page<Student> findAllStudents(Pageable pageable);

    @Query(
            value = """
                SELECT *
                FROM person p, professors pf
                WHERE p.id = pf.id
            """,
            countQuery = """
                SELECT COUNT(*)
                FROM person p, professors pf
                WHERE p.id = pf.id
            """,
            nativeQuery = true
    )
    Page<Professor> findAllProfessors(Pageable pageable);
}
