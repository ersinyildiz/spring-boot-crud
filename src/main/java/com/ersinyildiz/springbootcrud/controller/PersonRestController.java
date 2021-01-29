package com.ersinyildiz.springbootcrud.controller;

import com.ersinyildiz.springbootcrud.dto.PersonDTO;
import com.ersinyildiz.springbootcrud.exception.PersonNotFoundException;
import com.ersinyildiz.springbootcrud.exception.PersonNotValidException;
import com.ersinyildiz.springbootcrud.mapper.PersonMapper;
import com.ersinyildiz.springbootcrud.model.Person;
import com.ersinyildiz.springbootcrud.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class PersonRestController {

    private static final String RECORD_NOT_FOUND = "Kayıt bulunamadı id:";

    private PersonService personService;
    private PersonMapper personMapper;


    @Autowired
    public void setPersonService(PersonService personService,PersonMapper personMapper) {
        this.personService = personService;
        this.personMapper = personMapper;
    }

    @GetMapping("/persons")
    public List<PersonDTO> retrieveAllPersons() {
        return personMapper.toPersonDTOs(personService.findAll());
    }

    @GetMapping("/persons/{id}")
    public Person retrievePerson(@PathVariable("id") long id) {
        return personService.findById(id).orElseThrow(() -> new PersonNotFoundException(RECORD_NOT_FOUND+id));
    }

    @PostMapping("/persons")
    public Person createPerson(@Valid @RequestBody PersonDTO personDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            throw new PersonNotValidException("Lütfen alanları eksiksiz doldurunuz..");
        }
        return personService.save(personMapper.toPerson(personDTO));
    }

    @PutMapping("/persons/{id}")
    public ResponseEntity<Object> updatePerson(@Valid @RequestBody PersonDTO personDTO, BindingResult bindingResult,@PathVariable("id")Long id) {
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().build();
        }
        Person person = personMapper.toPerson(personDTO);
        person.setId(id);
        personService.save(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity<Object> deletePerson(@PathVariable("id") long id) {
        Optional<Person> person = personService.findById(id);
        if (!person.isPresent())
            throw new PersonNotFoundException(RECORD_NOT_FOUND+id);
        personService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
