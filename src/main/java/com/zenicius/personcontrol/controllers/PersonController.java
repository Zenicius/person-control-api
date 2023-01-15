package com.zenicius.personcontrol.controllers;

import com.zenicius.personcontrol.dtos.AddressDTO;
import com.zenicius.personcontrol.dtos.PersonDTO;
import com.zenicius.personcontrol.models.AddressModel;
import com.zenicius.personcontrol.models.PersonModel;
import com.zenicius.personcontrol.services.AddressService;
import com.zenicius.personcontrol.services.PersonService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/persons")
@Tag(name = "Persons")
public class PersonController {

    private final PersonService personService;
    private final AddressService addressService;

    public PersonController(PersonService personService, AddressService addressService) {
        this.personService = personService;
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid PersonDTO personDTO) {
        var person = new PersonModel();
        BeanUtils.copyProperties(personDTO, person);

        return ResponseEntity.status(HttpStatus.CREATED).body(personService.save(person));
    }

    @PostMapping("/{id}/addresses")
    public ResponseEntity<Object> saveAddress(@PathVariable(value = "id") UUID id, @RequestBody @Valid AddressDTO addressDTO) {
        PersonModel person = personService.findById(id);

        var address = new AddressModel();
        BeanUtils.copyProperties(addressDTO, address);
        address.setOwner(person);

        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.save(address));
    }

    @GetMapping
    @PageableAsQueryParam
    public ResponseEntity<Page<PersonModel>> getAll(@Parameter(hidden = true) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.findById(id));
    }

    @GetMapping("/{id}/addresses")
    @PageableAsQueryParam
    public ResponseEntity<Page<AddressModel>> getAddresses(@PathVariable(value = "id") UUID id, @Parameter(hidden = true) Pageable pageable) {
        PersonModel person = personService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(addressService.findAllByOwner(person, pageable));
    }

    @GetMapping("/{id}/addresses/{addressId}")
    public ResponseEntity<Object> getAddress(@PathVariable(value = "id") UUID id, @PathVariable(value = "addressId") UUID addressId) {
        AddressModel address = addressService.findById(id, addressId);

        return ResponseEntity.status(HttpStatus.OK).body(address);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid PersonDTO personDTO) {
        PersonModel person = personService.findById(id);

        var updatedPerson = new PersonModel();
        BeanUtils.copyProperties(personDTO, updatedPerson);
        updatedPerson.setId(person.getId());

        return ResponseEntity.status(HttpStatus.OK).body(personService.save(updatedPerson));
    }

    @PutMapping("/{id}/addresses/{addressId}")
    public ResponseEntity<Object> updateAddress(@PathVariable(value = "id") UUID id, @PathVariable(value = "addressId") UUID addressId,
                                                @RequestBody @Valid AddressDTO addressDTO) {
        PersonModel person = personService.findById(id);
        AddressModel address = addressService.findById(id, addressId);

        var updatedAddress = new AddressModel();
        BeanUtils.copyProperties(addressDTO, updatedAddress);
        updatedAddress.setId(address.getId());
        updatedAddress.setOwner(person);

        return ResponseEntity.status(HttpStatus.OK).body(addressService.save(updatedAddress));
    }

    @PutMapping("{id}/addresses/{addressId}/main")
    public ResponseEntity<Object> updateMainAddress(@PathVariable(value = "id") UUID id, @PathVariable(value = "addressId") UUID addressId) {
        PersonModel person = personService.findById(id);
        AddressModel address = addressService.findById(id, addressId);

        person.setMainAddress(address);

        return ResponseEntity.status(HttpStatus.OK).body(personService.save(person));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
        PersonModel person = personService.findById(id);

        personService.delete(person);

        return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("message", "Deleted"));
    }

    @DeleteMapping("/{id}/addresses/{addressId}")
    public ResponseEntity<Object> deleteAddress(@PathVariable(value = "id") UUID id, @PathVariable(value = "addressId") UUID addressId) {
        AddressModel address = addressService.findById(id, addressId);

        address.prepareForDelete();
        addressService.delete(address);

        return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("message", "Deleted"));
    }
}
