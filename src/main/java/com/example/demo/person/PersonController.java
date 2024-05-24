package com.example.demo.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<PersonEntity> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonEntity> getPersonById(@PathVariable Long id) {
        Optional<PersonEntity> person = personService.getPersonById(id);
        return person.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public PersonEntity createPerson(@RequestBody PersonEntity person) {
        return personService.savePerson(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonEntity> updatePerson(@PathVariable Long id, @RequestBody PersonEntity personDetails) {
        Optional<PersonEntity> person = personService.getPersonById(id);

        if (person.isPresent()) {

            PersonEntity updatedPerson = PersonEntity.builder()
                    .id(person.get().getId())
                    .name(personDetails.getName())
                    .email(personDetails.getEmail())
                    .build();

            return ResponseEntity.ok(personService.savePerson(updatedPerson));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PersonEntity> partialUpdatePerson(@PathVariable Long id, @RequestBody PersonEntity personDetails) {
        Optional<PersonEntity> person = personService.getPersonById(id);

        if (person.isPresent()) {
            PersonEntity currentPerson = person.get();

            PersonEntity updatedPerson = PersonEntity.builder()
                    .id(currentPerson.getId())
                    .name(personDetails.getName() != null ? personDetails.getName() : currentPerson.getName())
                    .email(personDetails.getEmail() != null ? personDetails.getEmail() : currentPerson.getEmail())
                    .build();

            return ResponseEntity.ok(personService.savePerson(updatedPerson));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
