package com.laterooms.configuration;

import org.apache.camel.component.jpa.JpaComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.OpenJpaVendorAdapter;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManagerFactory;

@Configuration
public class TyphoonConfiguration {
    @Bean
    public JpaComponent jpaComponent() {
        JpaComponent jpaComponent = new JpaComponent();
        jpaComponent.setEntityManagerFactory(entityManagerFactory());
        return jpaComponent;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalEntityManagerFactoryBean localEntityManagerFactoryBean = new LocalEntityManagerFactoryBean();
        localEntityManagerFactoryBean.setPersistenceUnitName("camel");
        localEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdaptor());
        return localEntityManagerFactoryBean.getNativeEntityManagerFactory();
    }

    @Bean
    public OpenJpaVendorAdapter jpaVendorAdaptor() {
        OpenJpaVendorAdapter openJpaVendorAdapter = new OpenJpaVendorAdapter();
        openJpaVendorAdapter.setDatabasePlatform("org.apache.openjpa.jdbc.sql.HSQLDictionary");
        openJpaVendorAdapter.setDatabase(Database.HSQL);

        return openJpaVendorAdapter;
    }
}