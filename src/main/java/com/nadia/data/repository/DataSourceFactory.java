package com.nadia.data.repository;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Properties;

@Component
public class DataSourceFactory {


     private DataSource getDataSource(String dbUrl) {

        Properties props = new Properties();
        props.setProperty("useSSL", "false");
        props.setProperty("serverTimezone", "GMT");

        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl("jdbc:mysql://127.0.0.1:3306/" + dbUrl);
        ds.setUsername("root");
        ds.setPassword("pinkus");
        ds.setConnectionProperties(props);

        return ds;
    }


     public JdbcOperations getJdbcTemplate(String dbUrl){
        return new JdbcTemplate(getDataSource(dbUrl));
     }

}
