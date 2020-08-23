package com.akash;

import com.akash.controller.PersonReadTest;
import com.akash.controller.PersonWriteTest;
// import org.junit.platform.runner.JUnitPlatform;
// import org.junit.platform.suite.api.SelectClasses;
// import org.junit.runner.RunWith;

/**
 * Only test suites are allowed to run using maven-surefire-plugin and not all test cases. This will
 * help to load spring context only once for all test classes having @SpringBootTest.
 */

// @RunWith(JUnitPlatform.class)
// @SelectClasses({PersonReadTest.class, PersonWriteTest.class})
public class AllTests {}
