package com.example.touristguideapi.repository;

import com.example.touristguideapi.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

    //Henter listen af byer ved hjælp af query(), SQL og CityRowMapper
    public List<City> getCities() {
        return jdbcTemplate.query("SELECT * FROM city", new CityRowMapper());
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

    // Tilføjer et attraktion objekt til arraylisten. Kaldes fra service.
    public void addAttraction(TouristAttraction attraction) {
        touristAttractions.add(attraction);
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