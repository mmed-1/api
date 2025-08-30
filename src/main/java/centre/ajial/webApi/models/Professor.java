package centre.ajial.webApi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "professors")
@Data @AllArgsConstructor @NoArgsConstructor
public class Professor extends Person {
    @Column(columnDefinition = "DECIMAL(12, 2)")
    @NotBlank(message = "Salary is required!")
    private double salary;

    @ManyToOne
    @JoinColumn(
            name = "admin_id",
            foreignKey = @ForeignKey(name = "admin_prof_fk")
    )
    private Admin admin;

    @ManyToOne
    @JoinColumn(
            name = "department_id",
            foreignKey = @ForeignKey(name = "pr_dep_fk")
    )
    private Department department;
}
