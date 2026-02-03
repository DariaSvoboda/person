package com.example.person.person.controller;

import com.example.person.location.model.Location;
import com.example.person.person.model.Person;
import com.example.person.person.repository.PersonRepository;
import com.example.person.weather.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public Iterable<Person> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Person> findById(@PathVariable int id) {
        return repository.findById(id);
    }
    @GetMapping("/{id}/weather")
    public Map<String, Object> getFullInfo(@PathVariable int id) {
        Person person = repository.findById(id).orElseThrow();

        String locationUrl = "http://localhost:8080/location/" + person.getLocation();
        Location location = restTemplate.getForObject(locationUrl, Location.class);

        if (location == null) {
            throw new RuntimeException("Location not found for city: " + person.getLocation());
        }

        String weatherUrl = "http://localhost:8080/weather?lat=" + location.getLat() + "&lon=" + location.getLon();
        Weather weather = restTemplate.getForObject(weatherUrl, Weather.class);

        return Map.of(
                "person", person,
                "weather", weather
        );
    }


    @PostMapping
    public ResponseEntity<?> save(@RequestBody Person person) {
        if (person.getId() != 0 && repository.findById(person.getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person already exists");
        }
        return new ResponseEntity<>(repository.save(person), HttpStatus.CREATED);
    }
}