package com.example.touristguideapi.repository;

import com.example.touristguideapi.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    }

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
    }

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

        // Først skal vi have attraktionens id (Skal måske bruge keyholder i stedet)
        int attractionID = jdbcTemplate.update(
                "SELECT attraction_id FROM tourist_attraction WHERE name = ?", attraction.getName()
        );

        // vi vil også gerne have id for hvert tag, der skal gemmes

        if (attraction.getTags() != null) {
            for (String tag : attraction.getTags()) {

                // for hvert tag i tags arrayen tjekkes der hvilket id tagget har i databasen
                // det gemms i en int (igen, måske skal keyholder bruges i stedet
                int tagID = jdbcTemplate.update("SELECT tag_id FROM tag WHERE tag_name = ?", tag);

                // de to id'er bruges til at indsætte data om tags
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
                });
            }
        }
    }

    //Skal SQL'en bruge navnet eller id på attraktionen?
    public void removeAttraction(int id) {
        TouristAttraction attraction = findAttractionById(id);

        if (attraction != null) {
            jdbcTemplate.update("delete from tourist_attraction where attraction_id = ?", id);
        } else {
            System.out.println("No attraction with that id.");
        }

        }

    public void updateAttraction(int id, TouristAttraction updatedAttraction) {

        String sqlUpdate = """
                              UPDATE tourist_attraction
                              SET name = ?,
                              description = ?,
                              city_id = ?
                              WHERE attraction_id = ?
                              """;
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlUpdate);

            // sql parametrene identificeres med de gemte id'er
            ps.setString(1, updatedAttraction.getName());
            ps.setString(2,updatedAttraction.getDescription());
            ps.setInt(3, updatedAttraction.getCityID());
            ps.setInt(4, id);
            return ps;
        });
    }

}