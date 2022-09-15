package com.kidsnara.library.config.database;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
public class RoutingDatabaseConfig {

    private final Environment env;

    @Bean
    @Qualifier("primary")
    @ConfigurationProperties(prefix="spring.datasource.primary")
    public DataSource primaryDataSource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @Qualifier("secondary")
    @ConfigurationProperties(prefix="spring.datasource.secondary")
    public DataSource secondaryDataSource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @DependsOn({"primaryDataSource", "secondaryDataSource"})
    @Bean("routeDataSource")
    public DataSource routeDataSource(@Qualifier("primary") DataSource primary, @Qualifier("secondary") DataSource secondary){

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DatabaseRole.PRIMARY, primary);
        dataSourceMap.put(DatabaseRole.SECONDARY, secondary);

        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(primary);

        return routingDataSource;
    }

    @DependsOn({"routeDataSource"})
    @Bean
    public DataSource dataSource(DataSource routeDataSource){
        return new LazyConnectionDataSourceProxy(routeDataSource);
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.kidsnara.library.domain");

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.physical_naming_strategy",
                SpringPhysicalNamingStrategy.class.getName());
        properties.put("hibernate.implicit_naming_strategy",
                SpringImplicitNamingStrategy.class.getName());
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.put("hibernate.format_sql", env.getProperty("spring.jpa.properties.hibernate.format_sql"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Primary
    @Bean
    public PlatformTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }
}
