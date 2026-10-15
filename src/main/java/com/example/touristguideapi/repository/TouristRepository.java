package com.example.touristguideapi.repository;

import com.example.touristguideapi.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Arrays;
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

    // en arrayliste initialiseres med tre attraktioner for at vi har noget at teste ud fra.
    private ArrayList<TouristAttraction> touristAttractions = new ArrayList<>(
            List.of(
                    new TouristAttraction("Tivoli", "Forlystelsespark i indre København", "København", EnumSet.of(Tag.AMUSEMENT_PARK, Tag.KID_FRIENDLY)),
                    new TouristAttraction("Kronborg", "Slot i Helsingør, hvor Hamlet foregår.", "Helsingør", EnumSet.of(Tag.CASTLE, Tag.HISTORY, Tag.ART)),
                    new TouristAttraction("Rundetårn", "Astronomisk tårn fra Christian IVs tid.", "København", EnumSet.of(Tag.ARCHITECTURE, Tag.HISTORY))
            )
    );

    public ArrayList<TouristAttraction> getAllAttractions() {
        return touristAttractions;
    }

    public List<String> getCities() {
        return Arrays.asList("København", "Helsingør", "Roskilde");
    }

    // Tager et navn på en attraction og returnerer hele objektet, hvis det kan findes. Kaldes fra service.
    public TouristAttraction findAttractionByName(String name) {
        for (TouristAttraction attraction : touristAttractions) {
            if (Objects.equals(attraction.getName(), name)) {
                return attraction;
            }
        }
        return null;
    }

    // Gemmer en attraktion og dens tags i databasen. Kaldes fra service.
    @Transactional
    public void addAttraction(TouristAttraction attraction) {
        // Find byens id.
        String citySql = "SELECT city_id FROM city WHERE city_name = ?";

        Integer cityId = jdbcTemplate.queryForObject(
                citySql, Integer.class, attraction.getCity()
        );

        // Gem attraktionen.
        String sql = """
            INSERT INTO tourist_attraction (name, description, city_id)
            VALUES (?, ?, ?)
            """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, attraction.getName());
            ps.setString(2, attraction.getDescription());
            ps.setInt(3, cityId);
            return ps;
        }, keyHolder);

        // Hent attraktionens nye id.
        int attractionId = keyHolder.getKey() != null
                ? keyHolder.getKey().intValue()
                : -1;

        if (attractionId == -1) {
            throw new RuntimeException("Kunne ikke hente attraktionens id");
        }

        // Gem forbindelserne mellem attraktionen og dens tags.
        if (attraction.getTags() != null) {
            for (Tag tag : attraction.getTags()) {
                String tagSql = "SELECT tag_id FROM tag WHERE tag_name = ?";

                Integer tagId = jdbcTemplate.queryForObject(
                        tagSql, Integer.class, tag.name()
                );

                String connectionSql = """
                    INSERT INTO attraction_tags (attraction_id, tag_id)
                    VALUES (?, ?)
                    """;

                jdbcTemplate.update(connectionSql, attractionId, tagId);
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