package com.ragajulan.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ragajulan.entity.Address;
import com.ragajulan.entity.Person;
import com.ragajulan.repository.PersonRepository;

@SpringBootTest
class AddressTest {
	@Autowired
	private PersonRepository repos;

	@Test
	void test() {
		fail("Not yet implemented");
	}

	@Test
	public void savePersonAndAddress() {

		Address address = Address.builder().city("Tangerang Selatan").countryCode("+62").postalCode("52114")
				.state("Banten").street("Pondok Aren").build();

		Person person = Person.builder().name("Raga").emailAddress("raga@gmail.com").address(address).build();

		repos.save(person);

	}

	@Test
	public void getAllPerson() {
		savePersonAndAddress();

		List<Person> persons = repos.findAll();
		System.out.println(persons);
	}

	
	
	@Test
	public void getPersonById()throws EntityNotFoundException {

		savePersonAndAddress();
		Person person = repos.findById(Long.valueOf(1)).orElseThrow(() -> new EntityNotFoundException("not found"));
		System.out.println(person);
		

	}
	
	@Test
	public void deletePersonById() throws EntityNotFoundException {

	
		Person person = repos.findById(Long.valueOf(1)).orElseThrow(() -> new EntityNotFoundException("not found"));
		System.out.println("Deleted person id is "+person.getId());
		repos.delete(person);

	}
	
}
