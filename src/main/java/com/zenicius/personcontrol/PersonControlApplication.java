package com.zenicius.personcontrol;

import com.zenicius.personcontrol.services.AddressService;
import com.zenicius.personcontrol.services.PersonService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Hidden
public class PersonControlApplication {

	@Autowired
	private PersonService personService;

	@Autowired
	private AddressService addressService;

	public static void main(String[] args) {
		SpringApplication.run(PersonControlApplication.class, args);
	}

	@GetMapping("/")
	public String index() {
		return personService.getCount() + " Persons and " +
				addressService.getCount() + " Addresses in database!";
	}

}
