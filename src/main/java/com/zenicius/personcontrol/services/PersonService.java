package com.zenicius.personcontrol.services;

import com.zenicius.personcontrol.exceptions.NotFoundException;
import com.zenicius.personcontrol.models.PersonModel;
import com.zenicius.personcontrol.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    @Transactional
    public PersonModel save(PersonModel personModel) {
        return personRepository.save(personModel);
    }

    public PersonModel findById(UUID id) {
        Optional<PersonModel> optionalPersonModel = personRepository.findById(id);

        if (optionalPersonModel.isEmpty())
            throw new NotFoundException("Person not found");

        return optionalPersonModel.get();
    }

    public Page<PersonModel> findAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    public long getCount() {
        return personRepository.count();
    }

    @Transactional
    public void delete(PersonModel personModel) {
        personRepository.delete(personModel);
    }

}
