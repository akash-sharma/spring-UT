package com.akash.controller;

import com.akash.AbstractApplicationTest;
import com.akash.dao.master.PersonDao;
import com.akash.entity.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html#testcontext-ctx-management-caching
 *
 * <p>(1) @RunWith(SpringRunner.class)
 *
 * <p>If a JUnit class or its parent class is annotated with @RunWith, JUnit framework invokes the
 * specified class as a test runner instead of running the default runner. A Runner class is
 * responsible to run JUnit test, typically by reflection.
 *
 * <p>SpringRunner provides support for loading a Spring ApplicationContext and having
 * beans @Autowired into your test instance and many other things.
 *
 * <p>(2) @SpringBootTest(classes = SpringUtApplication.class)
 *
 * <p>It looks for a class annotated with @SpringBootConfiguration from which it then reads the
 * configuration to create an application context. This class is usually our main application class
 * since the @SpringBootApplication annotation includes the @SpringBootConfiguration annotation.
 *
 * <p>(3) Use @MockBean to mock a bean via SpringRunner
 *
 * <p>(4) Other useful annotations : @BeforeClass , @Before, @AfterClass, @After
 */

/**
 * Only test suites are allowed to run using maven-surefire-plugin and not all test cases. Test
 * Suites will help to load spring context only once for all test classes having @SpringBootTest.
 *
 * <p>// @RunWith(JUnitPlatform.class) // @SelectClasses({PersonReadTest.class,
 * PersonWriteTest.class})
 */
public class PersonReadTest extends AbstractApplicationTest {

  @Autowired private PersonController personController;

  @Autowired private PersonDao personDao;

  @Test
  public void findPersonTest() {

    String personName = "sachin";
    final Person person = new Person();
    person.setPersonName(personName);
    Person savedPerson = personDao.save(person);
    String personJson = personController.findPerson(savedPerson.getId());
    Assertions.assertTrue(personJson.contains(personName));
  }
}
