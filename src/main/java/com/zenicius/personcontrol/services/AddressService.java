package com.zenicius.personcontrol.services;

import com.zenicius.personcontrol.exceptions.NotFoundException;
import com.zenicius.personcontrol.models.AddressModel;
import com.zenicius.personcontrol.models.PersonModel;
import com.zenicius.personcontrol.repositories.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    private PersonService personService;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Transactional
    public AddressModel save(AddressModel addressModel) {
        return addressRepository.save(addressModel);
    }

    public AddressModel findById(UUID ownerId, UUID addressId) {
        Optional<AddressModel> optionalAddressModel = addressRepository.findById(addressId);
        PersonModel owner = personService.findById(ownerId);

        if (optionalAddressModel.isEmpty() || optionalAddressModel.get().getOwner() != owner)
            throw new NotFoundException("Address not found");

        return optionalAddressModel.get();
    }

    public Page<AddressModel> findAllByOwner(PersonModel owner, Pageable pageable) {
        return addressRepository.findAllByOwner(owner, pageable);
    }

    public long getCount() {
        return addressRepository.count();
    }

    @Transactional
    public boolean delete(AddressModel addressModel) {
        long addressCount = getCount();
        addressRepository.delete(addressModel);

        if (addressCount - 1 == getCount())
            return true;

        return false;
    }

}
