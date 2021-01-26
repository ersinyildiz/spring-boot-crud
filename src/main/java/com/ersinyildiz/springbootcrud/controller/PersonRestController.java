package com.ersinyildiz.springbootcrud.controller;

import com.ersinyildiz.springbootcrud.exception.PersonNotFoundException;
import com.ersinyildiz.springbootcrud.exception.PersonNotValidException;
import com.ersinyildiz.springbootcrud.model.Person;
import com.ersinyildiz.springbootcrud.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class PersonRestController {

    private PersonService personService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/persons")
    public List<Person> retrieveAllPersons() {
        return personService.findAll();
    }

    @GetMapping("/persons/{id}")
    public Person retrievePerson(@PathVariable("id") long id) {
        return personService.findById(id).orElseThrow(() -> new PersonNotFoundException("Kayit bulunamadi id:"+id));
    }

    @PostMapping("/persons")
    public Person createPerson(@Valid @RequestBody Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            throw new PersonNotValidException("Lütfen alanları eksiksiz doldurunuz..");
        }
        return personService.save(person);
    }

    @PutMapping("/persons/{id}")
    public ResponseEntity<?> updatePerson(@Valid @RequestBody Person person, @PathVariable("id") long id) {
        Person person1 = personService.findById(id).orElseThrow(() -> new PersonNotFoundException("Kayit bulunamadi id:"+id));
        person1.setName(person.getName());
        person1.setEmail(person.getEmail());
        person1.setPhone(person.getPhone());
        personService.save(person1);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable("id") long id) {
        personService.findById(id).orElseThrow(() -> new PersonNotFoundException("Kayit bulunamadi id:"+id));
        personService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
