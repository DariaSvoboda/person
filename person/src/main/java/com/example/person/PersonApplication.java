package com.example.person;

import com.example.person.person.model.Person;
import com.example.person.person.repository.PersonRepository;
import com.example.person.location.model.Location;
import com.example.person.location.repository.LocationRepository;
import com.example.person.weather.model.Weather;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PersonApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public CommandLineRunner demo(PersonRepository personRepository, LocationRepository locationRepository) {
		return args -> {
			locationRepository.save(new Location(0, "Москва", 55.75, 37.61));

			personRepository.save(new Person("Иван", "Москва"));

			System.out.println("Данные для теста добавлены");
		};
	}
}