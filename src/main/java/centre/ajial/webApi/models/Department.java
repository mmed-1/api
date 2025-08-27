package centre.ajial.webApi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "departments")
@Data @AllArgsConstructor @NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80, nullable = false, unique = true)
    @NotBlank(message = "Department name is required!")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only letters are allowed!")
    private String name;

    @Column(columnDefinition = "TINYTEXT", nullable = false)
    @NotBlank(message = "Department name is required!")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only letters are allowed!")
    private String description;

    @ManyToOne
    @JoinColumn(
            name = "admin_id",
            foreignKey = @ForeignKey(name = "admin_dep_fk")
    )
    private Admin admin;
}
