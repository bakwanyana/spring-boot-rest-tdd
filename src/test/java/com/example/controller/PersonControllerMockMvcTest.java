package com.example.controller;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Person;
import com.example.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
@ActiveProfiles("dev")
public class PersonControllerMockMvcTest implements PersonControllerTestInterface {

    /*This demonstrates a test that mocks out embedded servlet container */

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonService personService; // this could always be an interface

    @Test
    public void thatOkIsReturnedWhenPersonRequestedExists() throws Exception {

        Person person = new Person(ID, NAME, SURNAME);
        doReturn(person).when(personService).getPerson(ID);

        mockMvc.perform(get("/person/{id}",  ID))
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.surname").value(SURNAME));

        verify(personService, times(1)).getPerson(ID);

    }

    @Test
    public void thatNotFoundIsReturnedWhenPersonNotFound() throws Exception {
        doThrow(new ResourceNotFoundException()).when(personService).getPerson(ID);

        mockMvc.perform(get("/person/{id}",  ID))
                .andExpect(status().isNotFound());

        verify(personService, times(1)).getPerson(ID);

    }

    @Test
    public void thatInternalServerErrorIsReturnedWhenExceptionOccurs() throws Exception {
        doThrow(new RuntimeException()).when(personService).getPerson(ID);

        mockMvc.perform(get("/person/{id}",  ID))
                .andExpect(status().isInternalServerError());

        verify(personService, times(1)).getPerson(ID);

    }
}
