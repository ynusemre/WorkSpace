package com.example.XmlParser.repository;

import com.example.XmlParser.model.Field;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository
public class  FieldRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FieldRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Field> getAllFields() {
        String sql = "CALL GetAllFields()";

        return jdbcTemplate.query(sql, new RowMapper<Field>() {
            @Override
            public Field mapRow(ResultSet rs, int rowNum) throws SQLException {
                Field field = new Field();
                field.setId(rs.getInt("id"));
                field.setPart(rs.getString("part"));
                return field;
            }
        });
    }

}
