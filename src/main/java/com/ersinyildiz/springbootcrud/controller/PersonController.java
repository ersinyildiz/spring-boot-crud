package com.ersinyildiz.springbootcrud.controller;

import com.ersinyildiz.springbootcrud.dto.PersonDTO;
import com.ersinyildiz.springbootcrud.exception.PersonNotFoundException;
import com.ersinyildiz.springbootcrud.mapper.PersonMapper;
import com.ersinyildiz.springbootcrud.model.Person;
import com.ersinyildiz.springbootcrud.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class PersonController {

    private static final String PERSON_LIST = "personList";
    private static final String INDEX = "index";
    private static final String REDIRECT = "redirect:/";
    private static final String RECORD_NOT_FOUND = "Kayıt bulunamadı id:";

    private PersonService personService;
    private PersonMapper personMapper;

    @Autowired
    public void setPersonService(PersonService personService,PersonMapper personMapper) {
        this.personService = personService;
        this.personMapper = personMapper;
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute(PERSON_LIST,personMapper.toPersonDTOs(personService.findAll()));
        return INDEX;
    }

    @GetMapping("/persons/{id}")
    public String retrievePerson(@PathVariable("id") Long id, Model model)  {
        Optional<Person> person = personService.findById(id);
        if (!person.isPresent()){
            throw new PersonNotFoundException(RECORD_NOT_FOUND+id);
        }
        model.addAttribute(PERSON_LIST,personMapper.toPersonDTOs(personService.findAll()));
        model.addAttribute("personDetails",person.get());
        return INDEX;
    }

    @ModelAttribute
    public PersonDTO initModel(){
        return new PersonDTO();
    }

    @PostMapping("/addPerson")
    public String createPerson(@Valid @ModelAttribute PersonDTO personDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute(PERSON_LIST,personMapper.toPersonDTOs(personService.findAll()));
            return INDEX;
        }
        personService.save(personMapper.toPerson(personDTO));
        model.addAttribute(PERSON_LIST,personMapper.toPersonDTOs(personService.findAll()));
        return REDIRECT;
    }

    @PostMapping("/persons/{id}")
    public String updatePerson(@Valid @ModelAttribute PersonDTO personDTO, BindingResult bindingResult, @PathVariable("id") Long id, Model model) {
        Optional<Person> optionalPerson = personService.findById(id);
        if (!optionalPerson.isPresent()){
            throw new PersonNotFoundException(RECORD_NOT_FOUND+id);
        }
        if (bindingResult.hasErrors()){
            model.addAttribute("personDetails",optionalPerson.get());
            model.addAttribute(PERSON_LIST,personMapper.toPersonDTOs(personService.findAll()));
            return INDEX;
        }
        Person person = personMapper.toPerson(personDTO);
        person.setId(id);
        personService.save(person);
        model.addAttribute(PERSON_LIST,personMapper.toPersonDTOs(personService.findAll()));
        return REDIRECT;
    }

    @GetMapping("/deletePerson/{id}")
    public String deletePerson(@PathVariable("id") long id, Model model) {
        Optional<Person> person1 = personService.findById(id);
        if (!person1.isPresent()){
            throw new PersonNotFoundException(RECORD_NOT_FOUND+id);
        }
        personService.deleteById(id);
        model.addAttribute(PERSON_LIST,personMapper.toPersonDTOs(personService.findAll()));
        return REDIRECT;
    }
}
