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

@Repository
public class TouristRepository {

    private final JdbcTemplate jdbcTemplate;

    public TouristRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public TouristAttraction findAttractionById(int id) {
        String sql = """
                SELECT tourist_attraction.attraction_id, name, description, city_id,
                GROUP_CONCAT(tag_name SEPARATOR ',') as "tag_names"
                FROM attraction_tags
                JOIN tourist_attraction
                ON attraction_tags.attraction_id = tourist_attraction.attraction_id
                JOIN tag
                on tag.tag_id = attraction_tags.tag_id
                WHERE tourist_attraction.attraction_id = ?
                GROUP BY tourist_attraction.attraction_id;
                """;

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

        // tourist_attraction med id, navn, beskrivelse og by joines med attraction_tags for at finde
        // hvilke tags en attraktion har og joines så med tag for at få fat i navnene på de tags

        // for at processere tags, bruges GROUP_CONCAT med en separator, for at få samlet hver attraktions tags
        // i en enkelt, samlet string, som rowmapperen derefter kan omdanne til en liste ved hjælp af separatoren
        String sqlgetter = """
                SELECT tourist_attraction.attraction_id, name, description, city_id,
                GROUP_CONCAT(tag_name SEPARATOR ',') as "tag_names"
                FROM attraction_tags
                JOIN tourist_attraction
                ON attraction_tags.attraction_id = tourist_attraction.attraction_id
                JOIN tag
                on tag.tag_id = attraction_tags.tag_id
                GROUP BY attraction_id
                """;
        // GROUP BY på id, samler alt data op givent id

        // Mulighed: Man kunne tilføje city tabellen til join'en, så vi får city_name fremfor city_id

        return jdbcTemplate.query(
                sqlgetter,
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
            for (Object tag : attraction.getTags()) {

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
            ps.setString(2, updatedAttraction.getDescription());
            ps.setInt(3, updatedAttraction.getCityID());
            ps.setInt(4, id);
            return ps;
        });
    }
}