package br.com.erudio.services;

import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PersonServicesTest {
    @Mock
    private PersonRepository repository;
    @InjectMocks
    private PersonServices services;
    private Person person;

    @BeforeEach
    public void setup() {
        person = new Person(
                "Gabriel",
                "Costa",
                "gabriel@gmail.com.br",
                "Uberlândia - Minas Gerais - Brasil",
                "Male");
    }

    @Test
    void testGivenPersonObject_WhenSavePerson_thenReturnPersonObject() {
        given(repository.findByEmail(anyString())).willReturn(Optional.empty());
        given(repository.save(person)).willReturn(person);

        Person savedPerson = services.create(person);

        assertNotNull(savedPerson);
        assertEquals("Gabriel", savedPerson.getFirstName());
    }
}