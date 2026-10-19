package com.example.touristguideapi.repository;

import com.example.touristguideapi.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;

/*
Klassen skal desuden indeholde CRUD metoder, der arbejder på ovenstående ArrayList.

Vent evt. med den endelige metodesignatur for CRUD metoderne til I
har set beskrivelsen af Controller klassens endpoints nedenfor.
*/

@Repository
public class TouristRepository {

    private final JdbcTemplate jdbcTemplate;

    public TouristRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public TouristAttraction findAttractionById(int id) {
        String sql = "SELECT * FROM tourist_attraction WHERE attraction_id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                new TouristAttractionRowMapper(),
                id
        );


    //Henter listen af byer ved hjælp af query(), SQL og CityRowMapper
    public List<City> getCities() {
        return jdbcTemplate.query("SELECT * FROM city", new CityRowMapper());
    }


    public List<TouristAttraction> getAllAttractions() {
        String sql = "SELECT * FROM tourist_attraction";

        return jdbcTemplate.query(
                sql,
                new TouristAttractionRowMapper()
        );
    // Gemmer en attraktion og dens tags i databasen. Kaldes fra service.
    @Transactional
    public void addAttraction(TouristAttraction attraction) {

        // Gem attraktionen.
        String sqlAttraction = """
            INSERT INTO tourist_attraction (name, description, city_id)
            VALUES (?, ?, ?)
            """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sqlAttraction, Statement.RETURN_GENERATED_KEYS
            );

            // sql parametrene identificeres med data returneret fra java metoderne
            ps.setString(1, attraction.getName());
            ps.setString(2, attraction.getDescription());
            ps.setInt(3, attraction.getCityID());
            return ps;
        }, keyHolder);

        // Gem attractions tags

        // Først skal vi have attraktionens tags (Skal måske bruge keyholder i stedet)
        String sqlID = "SELECT attraction_id FROM tourist_attraction WHERE name = ?"
                ;
        // vi gemmer id'et i en int
        int attractionID = jdbcTemplate.update(sqlID, attraction.getName());

        // vi vil også gerne have id for hvert tag, der skal gemmes
        // for hvert tag i tags arrayen tjekkes der hvilket id tagget har i databasen
        if (attraction.getTags() != null) {
            for (String tag : attraction.getTags()) {
                String sqlTagID = "SELECT tag_id FROM tag WHERE tag_name = ?";

                // id gammes i en int (igen, vi skal måske bruge keyholder i stedet)
                int tagID = jdbcTemplate.update(sqlTagID, tag);

                String sqlTags = """
                        INSERT INTO attraction_tags (attraction_id, tag_id)
                        VALUES (?, ?)
                        """;

                jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            sqlTags, Statement.RETURN_GENERATED_KEYS
                    );

                    // sql parametrene identificeres med de gemte id'er
                    ps.setInt(1, attractionID);
                    ps.setInt(2, tagID);
                    return ps;
                }, keyHolder);
            }
        }
    }

    public void removeAttraction(String name) {
        TouristAttraction attraction = findAttractionByName(name);

        if (attraction != null) {
            touristAttractions.remove(attraction);
        } else {
            System.out.println("No attraction with that name.");
        }
    }

    // Tager imod et navn og en attraktion (updatedAttraction) og
    // sætter en eksisterende attraktion (attraction) til at være i lig med
    // den attraktion (updatedAttraction), den har taget imod.
    // Navnet bruges til at finde den attraktion der skal ændres
    // og så bruges dets index i .set metoden
    public void updateAttraction(String name, TouristAttraction updatedAttraction) {
        TouristAttraction attraction = findAttractionByName(name);
        touristAttractions.set(touristAttractions.indexOf(attraction), updatedAttraction);
    }
}
/*
public void updateAttraction(int id, TouristAttraction updatedAttraction) {

}
*/