package com.zenicius.personcontrol.repositories;

import com.zenicius.personcontrol.models.AddressModel;
import com.zenicius.personcontrol.models.PersonModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<AddressModel, UUID> {

    Page<AddressModel> findAllByOwner(PersonModel owner, Pageable pageable);
}
