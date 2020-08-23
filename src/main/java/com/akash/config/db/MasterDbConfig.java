package com.akash.config.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.akash.dao.master",
    entityManagerFactoryRef = "masterEntityManagerFactory",
    transactionManagerRef = "masterEntityTransactionManager")
public class MasterDbConfig {

  @Resource private Environment env;

  @Primary
  @Bean(name = "masterEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory(
      final EntityManagerFactoryBuilder builder,
      final @Qualifier("master-db") DataSource dataSource) {

    Map<String, Object> properties = new HashMap<>();

    properties.put(
        "hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto").trim());
    properties.put(
        "hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
    properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));

    return builder
        .dataSource(dataSource)
        .packages("com.akash.entity")
        .persistenceUnit("masterDB")
        .properties(properties)
        .build();
  }

  @Primary
  @Bean(name = "master-db")
  public DataSource masterDatasource() {

    ComboPooledDataSource dataSource = new ComboPooledDataSource();

    try {
      dataSource.setDriverClass(env.getProperty("database.datasource.driver-class-name"));
    } catch (PropertyVetoException e) {
      throw new RuntimeException("exception occurred while creating slave datasource", e);
    }
    dataSource.setJdbcUrl(env.getProperty("database.master.datasource.url"));
    dataSource.setUser(env.getProperty("database.master.datasource.username"));
    dataSource.setPassword(env.getProperty("database.master.datasource.password"));
    dataSource.setDataSourceName("masterPool");
    dataSource.setPreferredTestQuery(env.getProperty("spring.datasource.validationQuery"));
    // wait_timeout at prod = 1200 sec
    dataSource.setMaxIdleTime(1100);
    return dataSource;
  }

  @Primary
  @Bean(name = "masterEntityTransactionManager")
  public PlatformTransactionManager MasterTransactionManager(
      @Qualifier("masterEntityManagerFactory")
          EntityManagerFactory masterEntityTransactionManager) {

    return new JpaTransactionManager(masterEntityTransactionManager);
  }
}
