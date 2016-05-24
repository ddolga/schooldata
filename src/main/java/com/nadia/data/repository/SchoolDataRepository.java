package com.nadia.data.repository;

import com.nadia.data.api.SchoolDataInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository
public class SchoolDataRepository implements SchoolDataInterface {

    JdbcOperations jdbcOperations;

    @Autowired
    public SchoolDataRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public void updateCityName(String original, String corrected){
        jdbcOperations.update("UPDATE address SET metro_area = ? WHERE metro_area = ?",corrected,original);
    }

}
