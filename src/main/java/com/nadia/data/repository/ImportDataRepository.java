package com.nadia.data.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository
public class ImportDataRepository {

    JdbcOperations jdbcOperations;

    @Autowired
    public ImportDataRepository(DataSourceFactory dataSourceFactory) {
        this.jdbcOperations = dataSourceFactory.getJdbcTemplate("import_data");
    }


}
