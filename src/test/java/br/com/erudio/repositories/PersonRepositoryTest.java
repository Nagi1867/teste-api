package br.com.erudio.repositories;

import br.com.erudio.model.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PersonRepositoryTest {
    @Autowired
    private PersonRepository repository;

    @DisplayName("Given Person Object when Save then Return Saved Person")
    @Test
    void testGivenPersonObject_whenSave_thenReturnSavedPerson() {
        Person person0 = new Person("Gabriel", "K", "gabriel@gmail.com", "vg", "m");

        Person savedPerson = repository.save(person0);

        assertNotNull(savedPerson);
        assertTrue(savedPerson.getId() > 0);
    }

    @DisplayName("Given Person Object when Save then Return Saved Person")
    @Test
    void testGivenPersonObject_whenFindAll_thenReturnPersonList() {
        Person person0 = new Person("Gabriel", "K", "gabriel@gmail.com", "vg", "m");
        Person person1 = new Person("Gabriel", "K", "gabriel@gmail.com", "vg", "m");

        repository.save(person0);
        repository.save(person1);

        List<Person> personList = repository.findAll();

        assertNotNull(personList);
        assertEquals(2, personList.size());
    }

    @DisplayName("Given Person Object when Save then Return Saved Person")
    @Test
    void testGivenPersonObject_whenFindByID_thenReturnPersonObject() {
        Person person1 = new Person("Gabriel", "K", "gabriel@gmail.com", "vg", "m");

        repository.save(person1);

        Person savedPerson = repository.findById(person1.getId()).get();

        assertNotNull(savedPerson);
        assertEquals(person1.getId(), savedPerson.getId());
    }

    @DisplayName("Given Person Object when Save then Return Saved Person")
    @Test
    void testGivenPersonObject_whenFindByEmail_thenReturnPersonObject() {
        Person person1 = new Person("Gabriel", "K", "gabriel@gmail.com", "vg", "m");

        repository.save(person1);

        Person savedPerson = repository.findByEmail(person1.getEmail()).get();

        assertNotNull(savedPerson);
        assertEquals(person1.getId(), savedPerson.getId());
    }

    @DisplayName("Given Person Object when Save then Return Saved Person")
    @Test
    void testGivenPersonObject_whenUpdatePerson_thenReturnUpdatedPersonObject() {
        Person person1 = new Person("Gabriel", "K", "gabriel@gmail.com", "vg", "m");

        repository.save(person1);

        Person savedPerson = repository.findById(person1.getId()).get();
        savedPerson.setFirstName("Gabriel");
        savedPerson.setEmail("gabriel@gmail.com");

        Person updatedPerson = repository.save(savedPerson);

        assertNotNull(updatedPerson);
        assertEquals("Gabriel", updatedPerson.getFirstName());
        assertEquals("gabriel@gmail.com", updatedPerson.getEmail());
    }

    @DisplayName("Given Person Object when Save then Return Saved Person")
    @Test
    void testGivenPersonObject_whenDelete_thenRemovePerson() {
        Person person1 = new Person("Gabriel", "K", "gabriel@gmail.com", "vg", "m");

        repository.save(person1);

        repository.deleteById(person1.getId());
        Optional<Person> personOptional = repository.findById(person1.getId());

        assertTrue(personOptional.isEmpty());
    }
}