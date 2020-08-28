package com.akash.controller;

import com.akash.AbstractApplicationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonWriteTest extends AbstractApplicationTest {

  @Autowired private PersonController personController;

  @Test
  public void createPersonTest() {

    String personName = "sachin";
    String personJson = personController.createPerson(personName);
    Assertions.assertTrue(personJson.contains(personName));
  }
}
