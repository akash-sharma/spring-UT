package com.akash.controller;

import com.akash.SpringUtApplication;
import org.cassandraunit.spring.CassandraUnit;
import org.cassandraunit.spring.CassandraUnitTestExecutionListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringUtApplication.class)
@ActiveProfiles("test")
@TestExecutionListeners(
    listeners = CassandraUnitTestExecutionListener.class,
    mergeMode = MERGE_WITH_DEFAULTS)
@CassandraUnit
public class PersonWriteTest {

  @Autowired private PersonController personController;

  @Test
  public void createPersonTest() {

    String personName = "sachin";
    String personJson = personController.createPerson(personName);
    Assertions.assertTrue(personJson.contains(personName));
  }
}
