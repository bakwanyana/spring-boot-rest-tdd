package com.example.controller;


import com.example.model.Person;
import com.example.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest implements PersonControllerTestInterface{
    private PersonService personService = mock(PersonService.class);
    private PersonController personController = new PersonController(personService);

    @Test
    public void testThatResponseEntityReturnedWhenResourceFound() {
        Person person = new Person(ID, NAME, SURNAME);
        doReturn(person).when(personService).getPerson(ID);

        ResponseEntity responseEntity = personController.getPersonById(ID);
        verify(personService, times(1)).getPerson(ID);
        assertNotNull(responseEntity);
        assertEquals(person, responseEntity.getBody());

    }
}
