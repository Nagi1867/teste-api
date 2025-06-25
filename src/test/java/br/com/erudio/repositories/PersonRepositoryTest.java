package br.com.erudio.repositories;

import br.com.erudio.model.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

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
}