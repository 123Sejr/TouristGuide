package com.example.touristguideapi.service;

import com.example.touristguideapi.model.City;
import com.example.touristguideapi.model.TouristAttraction;
import com.example.touristguideapi.repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TouristService {
    //Opretter et objekt af TouristRepository så vi kan bruge dens metoder.
    private TouristRepository repository;

    public TouristService(TouristRepository repository) {
        this.repository = repository;
    }

    public ArrayList<TouristAttraction> getTouristAttractions() {
        return repository.getAllAttractions();
    }

    // Kalder på en metode i repository, der returnerer en attraktion baseret på navn.
    // Kaldes selv fra controller.
    public TouristAttraction findAttractionByName(String name) {
        return repository.findAttractionByName(name);
    }

    // Tager imod en attraction fra controlleren og sender det videre til repository
    public void addAttraction(TouristAttraction attraction) {
        repository.addAttraction(attraction);
    }

    // Tager imod et navn og en attraction fra controlleren og sender det videre til metoden i repository.
    public void updateAttraction(String name, TouristAttraction attraction) {
        repository.updateAttraction(name, attraction);
    }

    // Tager imod et navn fra controlleren og sender det videre til en metode i repository
    public void removeAttraction(String name) {
        repository.removeAttraction(name);
    }

    public List<City> getCities() {
        return repository.getCities();
    }
}
