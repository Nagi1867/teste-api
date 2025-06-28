package br.com.erudio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.erudio.model.Person;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByEmail(String email);

    @Query("select p from Person p where p.firstName =?1 and p.lastName =?2")
    Person findByJPQL(String firstName, String lastName);
}