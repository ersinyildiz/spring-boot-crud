package com.ersinyildiz.springbootcrud;

import com.ersinyildiz.springbootcrud.dto.PersonDTO;
import com.ersinyildiz.springbootcrud.mapper.PersonMapper;
import com.ersinyildiz.springbootcrud.model.Person;
import com.ersinyildiz.springbootcrud.util.CrudOperations;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CrudOperationsTest {
    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private CrudOperations crudOperations;

    @Test
    void testSavePerson() {
        Person p1 = Person.builder()
                .name("Ersin YILDIZ")
                .email("ersinyildiz@gmail.com")
                .phone("05554441122")
                .build();
        PersonDTO personDTO = personMapper.toPersonDTO(p1);
        personDTO = crudOperations.save(personDTO);
        MatcherAssert.assertThat(personDTO.getId(), Matchers.greaterThan(0L));
    }
    @Test
    void testGetAllPerson(){
        List<PersonDTO> personDTOList = crudOperations.getAll();
        MatcherAssert.assertThat(personDTOList.size(), Matchers.greaterThan(0));
    }
    @Test
    void testUpdatePerson() throws RuntimeException{
        PersonDTO personDTO = crudOperations.get(3L);
        personDTO.setPhone("05445551133");
        crudOperations.update(personDTO,3L);
        MatcherAssert.assertThat(crudOperations.get(3L).getPhone(), Matchers.equalTo("05445551133"));
    }
    @Test
    void testDeletePerson() throws RuntimeException{
        crudOperations.delete(3L);
        MatcherAssert.assertThat(crudOperations.getAll().size(), Matchers.equalTo(2));
    }
}
