package com.example.touristguideapi.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TouristAttractionRowMapper implements RowMapper<TouristAttraction> {
    @Override
    public TouristAttraction mapRow(ResultSet rs, int rowNum) throws SQLException {

        // Tags for hver attraktion omdannes til en enkelt String med komm mellem hver tag
        // (se getAllAttractions() i TouristRepository)
        String tagNames = rs.getString("tag_names");

        // String'en med all en attraktions tags omdannes til en ArrayList<String> ved brug af split på komma
        ArrayList<String> tags = new ArrayList<>(List.of(tagNames.split("'")));

        return new TouristAttraction(
                rs.getInt("attraction_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getInt ("city_id"),
                tags // tags er allerede hentet og gemt i en ArrayList, denne returneres så
        );
    }
}
