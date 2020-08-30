package com.akash.config.db;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.SessionFactory;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.SessionFactoryFactoryBean;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

/**
 * datastax oss vs dse
 * https://support.datastax.com/hc/en-us/articles/115005513246-DataStax-Drivers-for-Apache-Cassandra-VS-DataStax-DSE-Drivers
 *
 * https://stackoverflow.com/questions/48571009/cassandra-java-driver-set-global-consistency-level
 *
 */

@Configuration
@EnableCassandraRepositories(basePackages = {"com.akash.cassandra.entity"})
public class CassandraConfiguration {

  @Value("${cassandra.contactpoints}")
  private String contactPoints;

  @Value("${cassandra.port}")
  private int port;

  @Value("${cassandra.keyspace}")
  private String keySpace;

  @Value("${cassandra.local-datacenter}")
  private String datacenter;

  @Bean
  public CqlSessionFactoryBean cqlSessionFactoryBean() {

    CqlSessionFactoryBean session = new CqlSessionFactoryBean();
    session.setContactPoints(contactPoints);
    session.setKeyspaceName(keySpace);
    session.setLocalDatacenter(datacenter);
    session.setPort(port);
    return session;
  }

  @Bean
  public SessionFactoryFactoryBean sessionFactory(
      CqlSession session, CassandraConverter converter) {

    SessionFactoryFactoryBean sessionFactory = new SessionFactoryFactoryBean();
    sessionFactory.setSession(session);
    sessionFactory.setConverter(converter);
    sessionFactory.setSchemaAction(SchemaAction.NONE);
    return sessionFactory;
  }

  @Bean
  public CassandraMappingContext mappingContext(CqlSession cqlSession) {

    CassandraMappingContext mappingContext = new CassandraMappingContext();
    mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cqlSession));
    return mappingContext;
  }

  @Bean
  public CassandraConverter converter(
      CassandraMappingContext mappingContext, CqlSession cqlSession) {
    MappingCassandraConverter mappingCassandraConverter =
        new MappingCassandraConverter(mappingContext);
    mappingCassandraConverter.setUserTypeResolver(new SimpleUserTypeResolver(cqlSession));
    return mappingCassandraConverter;
  }

  @Bean
  public CassandraOperations cassandraTemplate(
      SessionFactory sessionFactory, CassandraConverter converter) {
    return new CassandraTemplate(sessionFactory, converter);
  }
}
