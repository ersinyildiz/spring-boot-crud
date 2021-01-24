package com.ersinyildiz.springbootcrud.repository;

import com.ersinyildiz.springbootcrud.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
