package com.zenicius.personcontrol.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddressDTO {

    @NotBlank
    private String street;

    @NotBlank
    private String zipCode;

    @NotBlank
    private String number;

    @NotBlank
    private String city;

    @NotNull
    private boolean isMain;

    public AddressDTO(String street, String zipCode, String number, String city, boolean isMain) {
        this.street = street;
        this.zipCode = zipCode;
        this.number = number;
        this.city = city;
        this.isMain = isMain;
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

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }
}
