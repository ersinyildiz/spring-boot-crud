package com.ersinyildiz.springbootcrud.service;

import com.ersinyildiz.springbootcrud.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    List<Person> findAll();
    Optional<Person> findById(Long id);
    void deleteById(Long id);
    Person save (Person person);
}
