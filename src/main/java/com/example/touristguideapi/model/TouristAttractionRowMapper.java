package com.example.touristguideapi.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TouristAttractionRowMapper implements RowMapper<TouristAttraction> {
    @Override
    public TouristAttraction mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TouristAttraction(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getInt ("city_id"),
                (String[]) rs.getArray("tags").getArray()
        );
    }
}
