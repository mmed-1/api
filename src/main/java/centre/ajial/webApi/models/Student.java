package centre.ajial.webApi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "students")
@Data @AllArgsConstructor @NoArgsConstructor
public class Student extends Person {
    @Column(length = 20, nullable = false, unique = true)
    @NotBlank(message = "CNE is required")
    @Pattern(regexp = "^[A-Z0-9]+$")
    private String cne;

    @ManyToOne
    @JoinColumn(
            name = "admin_id",
            foreignKey = @ForeignKey(name = "admin_stu_fk")
    )
    private Admin admin;
}
