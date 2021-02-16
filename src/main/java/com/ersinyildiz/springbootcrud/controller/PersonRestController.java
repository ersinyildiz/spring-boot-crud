package com.ersinyildiz.springbootcrud.controller;

import com.ersinyildiz.springbootcrud.dto.PersonDTO;
import com.ersinyildiz.springbootcrud.exception.PersonNotValidException;
import com.ersinyildiz.springbootcrud.util.CrudOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class PersonRestController extends CrudOperations {

    @GetMapping("/persons")
    public List<PersonDTO> retrieveAllPersons() {
        return getAll();
    }

    @GetMapping("/persons/{id}")
    public PersonDTO retrievePerson(@PathVariable("id") long id) {
        return get(id);
    }

    @PostMapping("/persons")
    public PersonDTO createPerson(@Valid @RequestBody PersonDTO personDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            throw new PersonNotValidException("Lütfen alanları eksiksiz doldurunuz..");
        }
        return save(personDTO);
    }

    @PutMapping("/persons/{id}")
    public ResponseEntity<Object> updatePerson(@Valid @RequestBody PersonDTO personDTO, BindingResult bindingResult,@PathVariable("id")Long id) {
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().build();
        }
        update(personDTO,id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity<Object> deletePerson(@PathVariable("id") long id) {
        delete(id);
        return ResponseEntity.ok().build();
    }

}
