package com.example.person.location.controller;

import com.example.person.location.model.Location;
import com.example.person.location.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationRepository repository;

    @GetMapping
    public Iterable<Location> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{name}")
    public Location findByName(@PathVariable String name) {
        return repository.findByName(name);
    }

    @PostMapping
    public Location save(@RequestBody Location location) {
        return repository.save(location);
    }
}