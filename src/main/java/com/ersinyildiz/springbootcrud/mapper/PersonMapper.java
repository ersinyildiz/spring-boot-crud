package com.ersinyildiz.springbootcrud.mapper;

import com.ersinyildiz.springbootcrud.dto.PersonDTO;
import com.ersinyildiz.springbootcrud.model.Person;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonDTO toPersonDTO(Person person);
    Person toPerson(PersonDTO personDTO);
    List<PersonDTO> toPersonDTOs(List<Person> persons);
}
