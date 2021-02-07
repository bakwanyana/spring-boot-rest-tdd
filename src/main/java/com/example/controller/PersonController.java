package com.example.controller;

import com.example.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/person/{id}")
    public ResponseEntity getPersonById(@PathVariable Long id) {
        return ResponseEntity.ok(personService.getPerson(id));
    }
}
