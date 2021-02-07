package com.example.controller;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Person;
import com.example.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
@ActiveProfiles("dev")
public class PersonControllerMockMvcTest {

    /*This demonstrates a test that mocks out embedded servlet container */

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PersonController personController;
    @MockBean
    private PersonService personService; // this could always be an interface
    public static final long ID = 1L;
    public static final String NAME = "John";
    public static final String SURNAME = "Snow";

    @Test
    public void thatOkIsReturnedWhenPersonRequestedExists() throws Exception {

        Person person = new Person(ID, NAME, SURNAME);
        doReturn(person).when(personService).getPerson(ID);

        mockMvc.perform(get("/person/{id}",  ID))
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.surname").value(SURNAME));
    }

    @Test
    public void thatNotFoundIsReturnedWhenPersonNotFound() throws Exception {
        doThrow(new ResourceNotFoundException()).when(personService).getPerson(ID);

        mockMvc.perform(get("/person/{id}",  ID))
                .andExpect(status().isNotFound());
    }

    @Test
    public void thatInternalServerErrorIsReturnedWhenExceptionOccurs() throws Exception {
        doThrow(new RuntimeException()).when(personService).getPerson(ID);

        mockMvc.perform(get("/person/{id}",  ID))
                .andExpect(status().isBadRequest());
    }
}
