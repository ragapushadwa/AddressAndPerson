package com.ragajulan.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.ragajulan.entity.Address;
import com.ragajulan.entity.Person;
import com.ragajulan.repository.PersonRepository;
import com.ragajulan.response.ResponseHandler;


@RestController
public class PersonController {

	@Autowired
	private PersonRepository repos;

	@PostMapping("/person")
	public ResponseEntity<Object> Post(@RequestBody Person person) {
		try {
			Person result = repos.save(person);
			return ResponseHandler.generateResponse("Successfully added data!", HttpStatus.OK, result);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}

	}

	@GetMapping(value = "/persons")
	public ResponseEntity<Object> Get() {
		try {
			List<Person> result = repos.findAll();
			return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, result);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	@GetMapping("/person/{id}")
	public ResponseEntity<Object> findPersonById(@PathVariable(value = "id") Long id) {
		Person result = repos.findById(Long.valueOf(id)).orElseThrow(() -> new EntityNotFoundException("not found"));

		return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, result);

	}

	@DeleteMapping("/person/{id}")
	public ResponseEntity<Object> deletePersonById(@PathVariable(value = "id") Long id) {
		Person result = repos.findById(Long.valueOf(id)).orElseThrow(() -> new EntityNotFoundException("not found"));

		repos.delete(result);

		return ResponseHandler.generateResponse("Deleted", HttpStatus.OK, "Deleted person id " + result.getId());

	}

	@PatchMapping(value = "/person/{id}")
	public ResponseEntity<Object> partialUpdateGeneric(
	  @RequestBody Person person,
	  @PathVariable("id") String id) {
	    
		Person result = repos.findById(Long.valueOf(id)).orElseThrow(() -> new EntityNotFoundException("not found"));
		
		boolean needUpdate=false;
		if(person.getName()!=null) {
			result.setName(person.getName());
			needUpdate=true;
		}
		if(person.getEmailAddress()!=null) {
			result.setEmailAddress(person.getEmailAddress());
			needUpdate=true;
		}
		if(person.getAddress().getCity()!=null) {
			result.getAddress().setCity(person.getAddress().getCity());
			needUpdate=true;
		}
		if(person.getAddress().getCountryCode()!=null) {
			result.getAddress().setCountryCode(person.getAddress().getCountryCode());
			needUpdate=true;
		}
		if(person.getAddress().getPostalCode()!=null) {
			result.getAddress().setPostalCode(person.getAddress().getPostalCode());
			needUpdate=true;
		}
		if(person.getAddress().getState()!=null) {
			result.getAddress().setState(person.getAddress().getState());
			needUpdate=true;
		}
		if(person.getAddress().getStreet()!=null) {
			result.getAddress().setStreet(person.getAddress().getStreet());
			needUpdate=true;
		}
		
		if(needUpdate) {
			repos.save(result);
		}
		return ResponseHandler.generateResponse("Updated", HttpStatus.OK,  result);

	}
}
