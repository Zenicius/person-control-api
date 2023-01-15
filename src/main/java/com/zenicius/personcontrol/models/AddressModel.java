package com.zenicius.personcontrol.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "TB_ADDRESS")
public class AddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ADDRESS_ID", nullable = false)
    private UUID id;

    @ManyToOne
    @JsonIgnore
    private PersonModel owner;

    @Column(name = "ADDRESS_STREET", length = 50, nullable = false)
    private String street;

    @Column(name = "ADDRESS_ZIP_CODE", length = 8, nullable = false)
    private String zipCode;

    @Column(name = "ADDRESS_NUMBER", length = 10, nullable = false)
    private String number;

    @Column(name = "ADDRESS_CITY", length = 50, nullable = false)
    private String city;

    public AddressModel() {

    }

    public AddressModel(PersonModel owner, String street, String zipCode, String number, String city) {
        this.owner = owner;
        this.street = street;
        this.zipCode = zipCode;
        this.number = number;
        this.city = city;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PersonModel getOwner() {
        return owner;
    }

    public void setOwner(PersonModel owner) {
        this.owner = owner;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void prepareForDelete() {
        if(owner.getMainAddress() != null)
            owner.getAddresses().remove(this);

        if (owner.getMainAddress() != null && owner.getMainAddress() == this)
            owner.setMainAddress(null);

        owner = null;
    }
}
