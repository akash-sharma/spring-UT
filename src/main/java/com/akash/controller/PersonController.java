package com.akash.controller;

import com.akash.dao.master.PersonDao;
import com.akash.dao.slave.PersonSlaveDao;
import com.akash.entity.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PersonController {

  private static final Logger LOGGER = LogManager.getLogger(PersonController.class);

  @Autowired private PersonDao personDao;

  @Autowired private PersonSlaveDao personSlaveDao;

  @PostMapping(path = "/createPerson")
  @ResponseBody
  public String createPerson(@RequestParam(value = "name") String name) {

    Person person = new Person();
    person.setPersonName(name);
    Person savedPerson = personDao.save(person);
    String personJson = "empty person saved";
    try {
      personJson = new ObjectMapper().writeValueAsString(savedPerson);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return personJson;
  }

  @GetMapping(path = "/findPerson")
  @ResponseBody
  public String findPerson(@RequestParam(value = "id") Long id) {

    if (id == null) {
      throw new RuntimeException("id cannot be null");
    }
    Optional<Person> optionalPerson = personSlaveDao.findById(id);
    String personJson = "empty person saved";
    if (optionalPerson.isPresent()) {
      try {
        personJson = new ObjectMapper().writeValueAsString(optionalPerson.get());
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
    return personJson;
  }
}
