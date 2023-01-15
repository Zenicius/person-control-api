package com.zenicius.personcontrol.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zenicius.personcontrol.dtos.AddressDTO;
import com.zenicius.personcontrol.dtos.PersonDTO;
import com.zenicius.personcontrol.models.AddressModel;
import com.zenicius.personcontrol.models.PersonModel;
import com.zenicius.personcontrol.services.AddressService;
import com.zenicius.personcontrol.services.PersonService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private PersonController personController;

    @Autowired
    private PersonService personService;

    @Autowired
    private AddressService addressService;

    private PersonModel testPerson;
    private AddressModel testAddress;

    @BeforeEach
    public void setup() {
        PersonModel person = new PersonModel("Joao Test", new Date());
        PersonModel person2 = new PersonModel("Maria Test", new Date());
        PersonModel person3 = new PersonModel("Fabio Test", new Date());

        AddressModel address = new AddressModel(person, "Rua Test", "000000", "10", "Cidade");
        AddressModel address2 = new AddressModel(person, "Rua Test 2", "000000", "15", "Cidade 2");

        testPerson = person;
        testAddress = address;

        personService.save(person);
        personService.save(person2);
        personService.save(person3);
        addressService.save(address);
        addressService.save(address2);
    }

    private String toJson(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }

    @Test
    @DisplayName("CREATE person and checks if returned JSON is correct")
    public void createPerson() throws Exception {
        PersonDTO person = new PersonDTO("Carla Test", new Date());

        String body = toJson(person);

        mockMvc.perform(post("/persons").content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(person.getName())));
    }

    @Test
    @DisplayName("CREATE address and checks if returned JSON is correct")
    public void createAddress() throws Exception {
        UUID personID = testPerson.getId();

        AddressDTO address = new AddressDTO("Rua Test 3", "0000000", "35", "Cidade 3", true);

        String body = toJson(address);

        mockMvc.perform(post("/persons/{id}/addresses", personID).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.street", is(address.getStreet())))
                .andExpect(jsonPath("$.number", is(address.getNumber())))
                .andExpect(jsonPath("$.zipCode", is(address.getZipCode())))
                .andExpect(jsonPath("$.city", is(address.getCity())));

    }

    @Test
    @DisplayName("GET all persons and checks if returns JSON with correct size")
    public void getAllPersons() throws Exception {
        Page <PersonModel> persons = personService.findAll(Pageable.unpaged());

        mockMvc.perform(get("/persons"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.size()", is((int)persons.getTotalElements())));
    }

    @Test
    @DisplayName("GET test person by ID and checks if returned JSON is correct")
    public void getPersonByID() throws Exception {
        UUID id = testPerson.getId();

        mockMvc.perform(get("/persons/{id}", id))
                .andDo((print()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testPerson.getId().toString())))
                .andExpect(jsonPath("$.name", is(testPerson.getName())));
    }

    @Test
    @DisplayName("GET all addresses from test person and checks if returns JSON with correct size")
    public void getAllAddresses() throws Exception {
        UUID id = testPerson.getId();
        Page<AddressModel> addresses = addressService.findAllByOwner(testPerson, Pageable.unpaged());

        mockMvc.perform(get("/persons/{id}/addresses", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.size()", is((int)addresses.getTotalElements())));
    }

    @Test
    @DisplayName("UPDATE test person data and checks if returned JSON is correct")
    public void updatePerson() throws Exception {
        UUID id = testPerson.getId();

        PersonDTO updatedPerson = new PersonDTO("Joao Test II", new Date());
        String body = toJson(updatedPerson);

        mockMvc.perform(put("/persons/{id}", id).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testPerson.getId().toString())))
                .andExpect(jsonPath("$.name", is(testPerson.getName())));
    }

    @Test
    @DisplayName("UPDATE test address data and checks if returned JSON is correct")
    public void updateAddress() throws Exception {
        UUID personID = testPerson.getId();
        UUID addressID = testAddress.getId();

        AddressDTO updatedAddress = new AddressDTO("Rua Test 3", "0000000", "35", "Cidade 3", true);
        String body = toJson(updatedAddress);

        mockMvc.perform(put("/persons/{id}/addresses/{addressId}", personID, addressID)
                .content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.street", is(updatedAddress.getStreet())))
                .andExpect(jsonPath("$.number", is(updatedAddress.getNumber())))
                .andExpect(jsonPath("$.zipCode", is(updatedAddress.getZipCode())))
                .andExpect(jsonPath("$.city", is(updatedAddress.getCity())));
    }

    @Test
    @DisplayName("UPDATE main address for test person and checks if returned JSON is correct")
    public void updateMainAddress() throws Exception {
        UUID personID = testPerson.getId();
        UUID addressID = testAddress.getId();

        mockMvc.perform(put("/persons/{id}/addresses/{addressId}/main", personID, addressID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mainAddress.id", is(addressID.toString())));
    }

    @Test
    @DisplayName("DELETE test person and checks if response is OK")
    public void deletePerson() throws Exception {
        UUID id = testPerson.getId();

        mockMvc.perform(delete("/persons/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("DELETE test address and check if response is OK")
    public void deleteAddress() throws Exception {
        UUID personID = testPerson.getId();
        UUID addressID = testAddress.getId();

        mockMvc.perform(delete("/persons/{id}/addresses/{addressId}", personID, addressID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}