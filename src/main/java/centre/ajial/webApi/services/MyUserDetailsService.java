package centre.ajial.webApi.services;

import centre.ajial.webApi.current.user.UserPrincipal;
import centre.ajial.webApi.models.Person;
import centre.ajial.webApi.repositories.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PersonRepo repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = repo.findByEmail(email);
        if (person == null)
            throw new UsernameNotFoundException("User Not Found!!!");
        return new UserPrincipal(person);
    }
}
