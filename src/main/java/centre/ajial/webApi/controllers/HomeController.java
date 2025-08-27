package centre.ajial.webApi.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String hello(HttpServletRequest request) {
        return "Welcome to this api ur session id: *" + request.getSession().getId() + "*";
    }

}
