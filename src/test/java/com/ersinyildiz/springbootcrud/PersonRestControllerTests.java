package com.ersinyildiz.springbootcrud;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringBootCrudApplication.class)
@SpringBootTest
public class PersonRestControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testRetrieveAllPersons() throws Exception {
        mockMvc.perform(get("/api/persons")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]").exists())
                .andExpect(jsonPath("$[*].id").isNotEmpty());
    }

    @Test
    public void testRetrievePerson() throws Exception {
        mockMvc.perform(get("/api/persons/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testNotFoundPerson() throws Exception {
        mockMvc.perform(get("/api/persons/{id}", 0)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("NOT_FOUND"));
    }

    @Test
    public void testCreatePersonSuccess() throws Exception {
        mockMvc.perform(post("/api/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Kemal GUVEN\", \"email\": \"kemalguven@music.com\", \"phone\": \"04445553322\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.phone").exists())
                .andExpect(jsonPath("$.name").value("Kemal GUVEN"))
                .andExpect(jsonPath("$.email").value("kemalguven@music.com"))
                .andExpect(jsonPath("$.phone").value("04445553322"));
    }

    @Test
    public void testCreatePersonFailed() throws Exception {
        mockMvc.perform(post("/api/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"\", \"email\": \"\", \"phone\": \"04445553322\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                .value("BAD_REQUEST"));
    }

    @Test
    public void testUpdatePersonSuccess() throws Exception {
        mockMvc.perform(put("/api/persons/{id}",2)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Mehmet Kaya\", \"email\": \"mehmetkaya@gmail.com\", \"phone\": \"01234567890\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdatePersonFailedById() throws Exception {
        mockMvc.perform(put("/api/persons/{id}",9)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Mehmet Kaya\", \"email\": \"mehmetkaya@gmail.com\", \"phone\": \"01234567890\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("NOT_FOUND"));
    }

    @Test
    public void testUpdatePersonFailedByArgument() throws Exception {
        mockMvc.perform(put("/api/persons/{id}",2)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Mehmet Kaya\", \"email\": \"error\", \"phone\": \"01234567890\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeletePersonSuccess() throws Exception{
        mockMvc.perform(delete("/api/persons/{id}",3)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletePersonFailed() throws Exception{
        mockMvc.perform(delete("/api/persons/{id}",9)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("NOT_FOUND"));
    }
}
