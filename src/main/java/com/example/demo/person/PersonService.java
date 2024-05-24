package com.example.demo.person;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {


    private final PersonRepository personRepository;

    public List<PersonEntity> getAllPersons() {
        return personRepository.findAll();
    }

    public Optional<PersonEntity> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    public PersonEntity savePerson(PersonEntity person) {
        return personRepository.save(person);
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
}
