package com.example.touristguideapi.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagObjectRowMapper implements RowMapper<TagObject> {
    @Override
    public TagObject mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TagObject(
                rs.getInt("tag_id"),
                Tag.valueOf(rs.getString("tag"))
        );
    }
}
