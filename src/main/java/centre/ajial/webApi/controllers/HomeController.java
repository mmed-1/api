package centre.ajial.webApi.controllers;

import centre.ajial.webApi.dtos.LoginRequest;
import centre.ajial.webApi.models.Person;
import centre.ajial.webApi.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class HomeController {

    private UserService service;

    @GetMapping("/")
    public String hello(HttpServletRequest request) {
        return "Welcome to this api ur session id: *" + request.getSession().getId() + "*";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, BindingResult result) {
        return service.verification(request, result);
    }

}
