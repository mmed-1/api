package centre.ajial.webApi.services;

import centre.ajial.webApi.dtos.LoginRequest;
import centre.ajial.webApi.repositories.PersonRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@AllArgsConstructor
public class UserService {

    private final PersonRepo repo;
    private AuthenticationManager manager;
    private JwtService jwtService;

    public ResponseEntity<?> verification(LoginRequest request, BindingResult result) {
        //test if data are in the good way
        if (result.hasErrors())
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);

        try {
            Authentication authentication = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            return ResponseEntity.ok(jwtService.generateToken(request.getEmail()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Invalid username or password!", HttpStatus.UNAUTHORIZED);
        }
    }
}
