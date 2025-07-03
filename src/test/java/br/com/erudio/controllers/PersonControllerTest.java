package br.com.erudio.controllers;

import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.services.PersonServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.*;

@WebMvcTest
class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonServices services;

    private Person person;

    @BeforeEach
    public void setup() {
        // Given / Arrange
        person = new Person(
                "Leandro",
                "Costa",
                "leandro@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil",
                "Male");
    }

    @Test
    void testGivenPersonObject_WhenCreatePerson_thenReturnSavedPersonObject() throws JsonProcessingException, Exception {
        given(services.create(any(Person.class))).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person)));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.firstName", is(person.getFirstName())));
    }

    @Test
    void testGivenListOfPersons_WhenFindAllPersons_thenReturnPersonsList() throws JsonProcessingException, Exception {
        List<Person> persons = new ArrayList<>();
        persons.add(person);
        persons.add(person);
        given(services.findAll()).willReturn(persons);

        ResultActions response = mockMvc.perform(get("/person")
                .content(objectMapper.writeValueAsString(person)));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(persons.size())));
    }

    @Test
    void testGivenPersonId_WhenFindById_thenReturnPersonObject() throws JsonProcessingException, Exception {
        long personId = 1L;
        given(services.findById(personId)).willReturn(person);

        ResultActions response = mockMvc.perform(get("/person/{id}", personId));

        response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.firstName", is(person.getFirstName())));
    }

    @Test
    void testGivenInvalidPersonId_WhenFindById_thenReturnNotFound() throws JsonProcessingException, Exception {
        long personId = 1L;
        given(services.findById(personId)).willThrow(ResourceNotFoundException.class);


        ResultActions response = mockMvc.perform(get("/person/{id}", personId));

        response.andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    void testGivenUpdatePersonId_WhenUpdate_thenReturnUpdatedPersonObject() throws JsonProcessingException, Exception {
        long personId = 1L;
        given(services.findById(personId)).willReturn(person);
        given(services.update(any(Person.class))).willAnswer((invocation) -> invocation.getArgument(0));

        Person updatedPerson = new Person(
                "Zezinho",
                "Costa",
                "leandro@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil",
                "Male"
        );

        ResultActions response = mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPerson)));

        response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.firstName", is(updatedPerson.getFirstName())));
    }

    @Test
    void testGivenUnexistentPerson_WhenUpdate_thenReturnNotFound() throws JsonProcessingException, Exception {
        long personId = 1L;
        given(services.findById(personId)).willThrow(ResourceNotFoundException.class);
        given(services.update(any(Person.class))).willAnswer((invocation) -> invocation.getArgument(1));

        Person updatedPerson = new Person(
                "Zezinho",
                "Costa",
                "leandro@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil",
                "Male"
        );

        ResultActions response = mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPerson)));

        response.andExpect(status().isNotFound()).andDo(print());
    }
}