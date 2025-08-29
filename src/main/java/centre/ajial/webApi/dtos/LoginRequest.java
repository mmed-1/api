package centre.ajial.webApi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Email is required!")
    @Email(message = "Invalid email!")
    private String email;

    //@Size(min = 12)
    @Pattern(regexp = "^[^-=]+$", message = "Cannot contain '-' or '='")
    private String password;
}
