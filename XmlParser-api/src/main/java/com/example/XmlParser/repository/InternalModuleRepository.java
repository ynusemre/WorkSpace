package com.example.XmlParser.repository;

import com.example.XmlParser.model.Field;
import com.example.XmlParser.model.InternalModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class InternalModuleRepository {
/*
    @Procedure(name = "GetAllInternalModules")
    List<InternalModule> getAllInternalModules();

 */
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public InternalModuleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<InternalModule> getAllInternalModules() {
        String sql = "CALL GetAllInternalodules()";

        return jdbcTemplate.query(sql, new RowMapper<InternalModule>() {
            @Override
            public InternalModule mapRow(ResultSet rs, int rowNum) throws SQLException {
                InternalModule internalModule = new InternalModule();
                internalModule.setId(rs.getInt("id"));
                internalModule.setPart(rs.getString("part"));
                return internalModule;
            }
        });
    }

}
