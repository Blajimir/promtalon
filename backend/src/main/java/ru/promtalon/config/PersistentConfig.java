package ru.promtalon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath: persistent.properties")
public class PersistentConfig {
    @Autowired
    private Environment environment;
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName( environment.getProperty("jdbc.postgres.driver"));
        dataSource.setUrl(environment.getProperty("jdbc.postgres.url"));
        dataSource.setUsername(environment.getProperty("jdbc.postgres.username"));
        dataSource.setPassword(environment.getProperty("jdbc.postgres.password"));
        return dataSource;
    }
}
