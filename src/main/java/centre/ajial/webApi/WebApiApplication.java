package centre.ajial.webApi;

import centre.ajial.webApi.models.Admin;
import centre.ajial.webApi.models.Person;
import centre.ajial.webApi.repositories.PersonRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@SpringBootApplication
public class WebApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApiApplication.class, args);
	}

	@Bean
	CommandLineRunner save(PersonRepo repo) {
		return args -> {
			Person person = new Admin();
			person.setCin("Q12345678");
			person.setEmail("email@email.com");
			person.setPassword(PersonRepo.ENCODER.encode("a@1234"));
			person.setFirstName("mr");
			person.setLastName("robot");
			person.setBirthday(new SimpleDateFormat("yyyy/MM/dd").parse("1999/09/08"));
			person.setNationality("Brazilian");
			repo.save(person);
		};
	}

}
