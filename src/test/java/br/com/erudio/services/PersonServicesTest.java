package br.com.erudio.services;

import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
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

    @Test
    void testGivenExistingEmail_WhenSavePerson_thenThrowsException() {
        given(repository.findByEmail(anyString())).willReturn(Optional.of(person));

        assertThrows(ResourceNotFoundException.class, () -> {
            services.create(person);
        });

        verify(repository, never()).save(any(Person.class));
    }

    @Test
    void testGivenPersonsList_WhenFindAllPersons_thenReturnPersonsList() {
        Person person1 = new Person("Leonardo",
                "Costa",
                "leonardo@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil", "Male");

        given(repository.findAll()).willReturn(List.of(person, person1));

        List<Person> personList = services.findAll();

        assertNotNull(personList);
        assertEquals(2, personList.size());
    }

    @Test
    void testGivenPersonsList_WhenFindAllPersons_thenReturnEmptyPersonsList() {
        given(repository.findAll()).willReturn(Collections.emptyList());

        List<Person> personList = services.findAll();

        assertTrue(personList.isEmpty());
        assertEquals(0, personList.size());
    }

    @Test
    void testGivenPersonId_WhenFindById_thenReturnPersonObject() {
        given(repository.findById(anyLong())).willReturn(Optional.of(person));

        Person savedPerson = services.findById(1L);

        assertNotNull(savedPerson);
        assertEquals("Gabriel", savedPerson.getFirstName());
    }

    @Test
    void testGivenPersonObject_WhenUpdatePerson_thenReturnUpdatePersonObject() {
        person.setId(1L);
        given(repository.findById(anyLong())).willReturn(Optional.of(person));

        person.setFirstName("Leonardo");
        person.setEmail("leonardo@erudio.com.br");

        given(repository.save(person)).willReturn(person);

        Person savedPerson = services.update(person);

        assertNotNull(savedPerson);
        assertEquals("Leonardo", savedPerson.getFirstName());
        assertEquals("leonardo@erudio.com.br", savedPerson.getEmail());
    }
}