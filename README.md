# Spring boot application test cases with junit 5 #


Spring boot version --> 2.3.3.RELEASE

junit jupiter version --> 5.3.2

maven-surefire-plugin --> 2.22.2


#### links ####
https://rieckpil.de/improve-build-times-with-context-caching-from-spring-test/

https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html#testcontext-ctx-management-caching


#### factors that determine spring context cache ####
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



#### Try to Avoid @MockBean with @SpringBootTest or Use Abstract test classes with @SpringBootTest ####


#### Avoid @DirtiesContext ####


#### Use below loggers to debug issue ####

logging.level.org.springframework.test.context.cache=DEBUG



#### elastic search embedded server for spring integration testing ####

https://www.thetopsites.net/article/52581662.shtml

https://stackoverflow.com/questions/30675654/elasticsearch-spring-boot-integration-test


#### kafka embedded server for spring integration testing ####

https://blog.mimacom.com/testing-apache-kafka-with-spring-boot/


#### Redis Spring integration testing ####

https://www.baeldung.com/spring-embedded-redis

https://stackoverflow.com/questions/32524194/embedded-redis-for-spring-boot


#### Mockito argThat example ####

FileFilter fileFilter = mock(FileFilter.class);
ArgumentMatcher<File> hasLuck = file -> file.getName().endsWith("luck");
when(fileFilter.accept(argThat(hasLuck))).thenReturn(true);
assertFalse(fileFilter.accept(new File("/deserve")));
assertTrue(fileFilter.accept(new File("/deserve/luck")));

