package com.ersinyildiz.springbootcrud.controller;

import com.ersinyildiz.springbootcrud.dto.PersonDTO;
import com.ersinyildiz.springbootcrud.util.CrudOperations;
import lombok.Generated;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Generated //Ignore for test
@Controller
public class PersonController extends CrudOperations {

    private static final String PERSON_LIST = "personList";
    private static final String PERSON_DETAILS = "personDetails";
    private static final String INDEX = "index";
    private static final String REDIRECT = "redirect:/";


    @GetMapping("/")
    public String index(Model model){
        model.addAttribute(PERSON_LIST,getAll());
        return INDEX;
    }

    @GetMapping("/persons/{id}")
    public String retrievePerson(@PathVariable("id") Long id, Model model)  {
        model.addAttribute(PERSON_DETAILS,get(id));
        model.addAttribute(PERSON_LIST,getAll());
        return INDEX;
    }

    @ModelAttribute
    public PersonDTO initModel(){
        return new PersonDTO();
    }

    @PostMapping("/addPerson")
    public String createPerson(@Valid @ModelAttribute PersonDTO personDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute(PERSON_LIST,getAll());
            return INDEX;
        }
        save(personDTO);
        return REDIRECT;
    }

    @PostMapping("/persons/{id}")
    public String updatePerson(@Valid @ModelAttribute PersonDTO personDTO, BindingResult bindingResult, @PathVariable("id") Long id, Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute(PERSON_DETAILS,get(id));
            model.addAttribute(PERSON_LIST,getAll());
            return INDEX;
        }
        update(personDTO,id);
        return REDIRECT;
    }

    @GetMapping("/deletePerson/{id}")
    public String deletePerson(@PathVariable("id") long id) {
        delete(id);
        return REDIRECT;
    }
}
