# Spring boot application test cases with junit 5 #


Spring boot version --> 2.3.3.RELEASE

junit jupiter version --> 5.3.2

maven-surefire-plugin --> 2.22.2


#### Reuse Spring context for multiple test classes ####
https://rieckpil.de/improve-build-times-with-context-caching-from-spring-test/

https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html#testcontext-ctx-management-caching


##### factors that determine spring context cache #####
1. locations (from @ContextConfiguration)
2. classes (part of @ContextConfiguration)
3. contextInitializerClasses (from @ContextConfiguration)
4. contextCustomizers (from ContextCustomizerFactory) – e.g. @DynamicPropertySource, @MockBean and @SpyBean.
5. contextLoader (part of @ContextConfiguration)
5. parent (from @ContextHierarchy)
6. activeProfiles (coming from @ActiveProfiles)
7. propertySourceLocations (from @TestPropertySource)
8. propertySourceProperties (from @TestPropertySource)
9. resourceBasePath (part of @WebAppConfiguration)


##### Rules to Remember #####
1. Try to Avoid @MockBean with @SpringBootTest or Use Abstract test classes with @SpringBootTest
2. Avoid @DirtiesContext
3. Use below loggers to debug issue : logging.level.org.springframework.test.context.cache=DEBUG


### Cassandra embedded server for sping UT ###
(Already implemented in this project)

https://www.linkedin.com/pulse/unit-testing-using-embedded-cassandra-raghunandan-gupta

https://www.codesandnotes.be/2015/02/26/cassandraunit-not-reloaded-making-cassandra-testing-faster/


### Elastic Search embedded server for spring UT ###

https://www.thetopsites.net/article/52581662.shtml

https://stackoverflow.com/questions/30675654/elasticsearch-spring-boot-integration-test


### Kafka Embedded server for spring UT ###

https://blog.mimacom.com/testing-apache-kafka-with-spring-boot/


### Redis Spring UT ###

https://www.baeldung.com/spring-embedded-redis

https://stackoverflow.com/questions/32524194/embedded-redis-for-spring-boot


###  Aerospike Mocking for Spring UT ###
(HashMap mocking implementation for aerospike)

https://github.com/srini156/mock-aerospike


### Notes ###

##### Mockito argThat example #####

```Java
FileFilter fileFilter = Mockito.mock(FileFilter.class);
ArgumentMatcher<File> hasLuck = file -> file.getName().endsWith("luck");

Mockito.when(fileFilter.accept(argThat(hasLuck))).thenReturn(true);

assertFalse(fileFilter.accept(new File("/deserve")));
assertTrue(fileFilter.accept(new File("/deserve/luck")));
```


##### Points to Remember #####

1. @RunWith(SpringRunner.class)
 If a JUnit class or its parent class is annotated with @RunWith, JUnit framework invokes the
 specified class as a test runner instead of running the default runner. A Runner class is
 responsible to run JUnit test, typically by reflection.

 SpringRunner provides support for loading a Spring ApplicationContext and having
 beans @Autowired into your test instance and many other things.

2. @SpringBootTest(classes = SpringUtApplication.class)

 It looks for a class annotated with @SpringBootConfiguration from which it then reads the
 configuration to create an application context. This class is usually our main application class
 since the @SpringBootApplication annotation includes the @SpringBootConfiguration annotation.

3. Use @MockBean to mock a bean via SpringRunner
