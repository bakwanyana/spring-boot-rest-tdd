package com.example.controller;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

    @Test
    public void thatOkIsReturnedWhenPersonRequestedExists() throws Exception {
        long id = 1L;
        String name = "John";
        String surname = "Snow";

        Person person = new Person(id, name, surname);
        doReturn(person).when(personService).getPerson(id);

        mockMvc.perform(get("/person/{id}",  1L))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.surname").value(surname));
    }

    @Test
    public void thatNotFoundIsReturnedWhenPersonNotFound() {

    }

    @Test
    public void thatInternalServerErrorIsReturnedWhenExceptionOccurs() {

    }
}
