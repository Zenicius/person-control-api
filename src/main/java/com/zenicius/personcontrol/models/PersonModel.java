package com.zenicius.personcontrol.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_PERSON")
public class PersonModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private UUID id;

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "BIRTHDATE", nullable = false)
    private Date birthdate;

    @OneToOne
    private AddressModel mainAddress;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<AddressModel> addresses;

    public PersonModel() {
    }

    public PersonModel(String name, Date birthdate) {
        this.name = name;
        this.birthdate = birthdate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public List<AddressModel> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressModel> addresses) {
        this.addresses = addresses;
    }

    public AddressModel getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(AddressModel mainAddress) {
        this.mainAddress = mainAddress;
    }
}
