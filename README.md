#Spring boot application test cases with junit 5


Spring boot version --> 2.3.3.RELEASE

junit jupiter version --> 5.3.2

maven-surefire-plugin --> 2.22.2



https://rieckpil.de/improve-build-times-with-context-caching-from-spring-test/

https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html#testcontext-ctx-management-caching


** factors that determine spring context cache
locations (from @ContextConfiguration)

classes (part of @ContextConfiguration)

contextInitializerClasses (from @ContextConfiguration)

contextCustomizers (from ContextCustomizerFactory) – e.g. @DynamicPropertySource, @MockBean and @SpyBean.

contextLoader (part of @ContextConfiguration)

parent (from @ContextHierarchy)

activeProfiles (coming from @ActiveProfiles)

propertySourceLocations (from @TestPropertySource)

propertySourceProperties (from @TestPropertySource)

resourceBasePath (part of @WebAppConfiguration)




** Avoid @MockBean with @SpringBootTest


** Avoid @DirtiesContext


** Use Abstract test classes with @SpringBootTest


** Use below loggers to debug issue

logging.level.org.springframework.test.context.cache=DEBUG

