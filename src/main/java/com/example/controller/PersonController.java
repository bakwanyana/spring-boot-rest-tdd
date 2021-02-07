package com.example.controller;

import com.example.exception.ResourceNotFoundException;
import com.example.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

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

    @ExceptionHandler(ResourceNotFoundException.class)
    private void handleResourceNotFoundException(HttpServletResponse response) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(Exception.class)
    private void handleGenericExceptions(HttpServletResponse response) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
