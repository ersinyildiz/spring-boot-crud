package com.ersinyildiz.springbootcrud.controller;

import com.ersinyildiz.springbootcrud.exception.PersonNotFoundException;
import com.ersinyildiz.springbootcrud.exception.PersonNotValidException;
import com.ersinyildiz.springbootcrud.model.Person;
import com.ersinyildiz.springbootcrud.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class PersonController {

    private PersonService personService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("personList",personService.findAll());
        return "index";
    }

    @GetMapping("/persons/{id}")
    public String retreivePerson(@PathVariable("id") Long id, Model model){
        Person person = personService.findById(id).orElseThrow(() -> new PersonNotFoundException("Kayit bulunamadi.. id:"+id));
        model.addAttribute("personList",personService.findAll());
        model.addAttribute("personDetails",person);
        return "index";
    }

    @ModelAttribute
    public Person initModel(){
        return new Person();
    }

    @PostMapping("/addPerson")
    public String createPerson(@Valid @ModelAttribute Person person, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("personList",personService.findAll());
            return "index";
        }
        personService.save(person);
        model.addAttribute("personList",personService.findAll());
        return "redirect:/";
    }

    @PostMapping("/persons/{id}")
    public String updatePerson(@Valid @ModelAttribute Person person,BindingResult bindingResult, @PathVariable("id") long id, Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("personDetails",personService.findById(id).orElseThrow(() -> new PersonNotFoundException("Kayit bulunamadi id:"+id)));
            model.addAttribute("personList",personService.findAll());
            return "index";
        }
        Person person1 = personService.findById(id).orElseThrow(() -> new PersonNotFoundException("Kayit bulunamadi id:"+id));
        person1.setName(person.getName());
        person1.setEmail(person.getEmail());
        person1.setPhone(person.getPhone());
        personService.save(person1);
        model.addAttribute("personList",personService.findAll());
        return "redirect:/";
    }

    @GetMapping("/deletePerson/{id}")
    public String deletePerson(@PathVariable("id") long id, Model model) {
        personService.findById(id).orElseThrow(() -> new PersonNotFoundException("Kayit bulunamadi id:"+id));
        personService.deleteById(id);
        model.addAttribute("personList",personService.findAll());
        return "redirect:/";
    }
}
