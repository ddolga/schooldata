package com.nadia.data.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class ConnectMySQL {


    public DataSource dataSource() {


        Properties props = new Properties();
        props.setProperty("useSSL","false");
        props.setProperty("serverTimezone","GMT");

        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl("jdbc:mysql://127.0.0.1:3306/school_reform");
        ds.setUsername("root");
        ds.setPassword("pinkus");
        ds.setConnectionProperties(props);

        return ds;
    }

    @Bean
    public JdbcOperations jdbcTemplate() {

        return new JdbcTemplate(dataSource());
    }


}
