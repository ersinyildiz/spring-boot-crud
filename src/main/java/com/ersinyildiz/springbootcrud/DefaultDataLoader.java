package com.ersinyildiz.springbootcrud;

import com.ersinyildiz.springbootcrud.model.Person;
import com.ersinyildiz.springbootcrud.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DefaultDataLoader implements ApplicationRunner {

    private PersonService personService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void run(ApplicationArguments args) {
        Person p1 = Person.builder()
                .name("Ersin YILDIZ")
                .email("ersinyildiz@gmail.com")
                .phone("01234567890")
                .build();
        Person p2 = Person.builder()
                .name("Mehmet KARACA")
                .email("mehmetkaraca@gmail.com")
                .phone("01234567890")
                .build();
        Person p3 = Person.builder()
                .name("Murat CAN")
                .email("muratcan@gmail.com")
                .phone("01234567890")
                .build();
        personService.save(p1);
        personService.save(p2);
        personService.save(p3);
    }
}
