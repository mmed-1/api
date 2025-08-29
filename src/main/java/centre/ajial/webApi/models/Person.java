package centre.ajial.webApi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "persons")
@Data @NoArgsConstructor @AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(length = 15, nullable = false, unique = true)
    @NotBlank(message = "cin is required")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only number and letters are allowed!")
    protected String cin;

    @Column(length = 50, nullable = false)
    @NotBlank(message = "first name is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only letters are allowed!")
    protected String firstName;

    @Column(length = 50, nullable = false)
    @NotBlank(message = "last name is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only letters are allowed!")
    protected String lastName;

    @Column(length = 150, nullable = false, unique = true)
    @NotBlank(message = "email is required!")
    @Email(message = "email is invalid!")
    protected String email;

    @Column(columnDefinition = "TINYTEXT", nullable = false)
    @NotBlank(message = "Password is required!")
    @Size(min = 12)
    @Pattern(regexp = "^[^-=]+$", message = "Cannot contain '-' or '='")
    protected String password;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "Birthday is required")
    protected Date birthday;

    @Column(length = 20, nullable = false)
    @NotBlank(message = "Nationality is required!")
    @Pattern(regexp = "^[a-zA-Z]+$")
    protected String nationality;
}