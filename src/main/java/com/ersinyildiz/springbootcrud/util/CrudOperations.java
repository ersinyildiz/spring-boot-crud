package com.ersinyildiz.springbootcrud.util;

import com.ersinyildiz.springbootcrud.dto.PersonDTO;
import com.ersinyildiz.springbootcrud.exception.PersonNotFoundException;
import com.ersinyildiz.springbootcrud.mapper.PersonMapper;
import com.ersinyildiz.springbootcrud.model.Person;
import com.ersinyildiz.springbootcrud.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CrudOperations {

    private PersonService personService;
    private PersonMapper personMapper;
    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }
    @Autowired
    public void setPersonMapper(PersonMapper personMapper) {
        this.personMapper = personMapper;
    }

    private static final String RECORD_NOT_FOUND = "Kayıt bulunamadı id:";

    public CrudOperations() {
        //Default Constructor
    }
    public PersonDTO get(Long id){
        Person p = personService.findById(id).orElseThrow(() -> new PersonNotFoundException(RECORD_NOT_FOUND+id));
        return personMapper.toPersonDTO(p);
    }
    public List<PersonDTO> getAll(){
        return personMapper.toPersonDTOs(personService.findAll());
    }
    public PersonDTO save(PersonDTO personDTO){
        return personMapper.toPersonDTO(personService.save(personMapper.toPerson(personDTO)));
    }
    public void update(PersonDTO personDTO, Long id){
        if (!personService.findById(id).isPresent())
            throw new PersonNotFoundException(RECORD_NOT_FOUND+id);
        Person person = personMapper.toPerson(personDTO);
        person.setId(id);
        personService.save(person);
    }
    public void delete(Long id){
        Person p = personService.findById(id).orElseThrow(() -> new PersonNotFoundException(RECORD_NOT_FOUND+id));
        personService.deleteById(p.getId());
    }
}
