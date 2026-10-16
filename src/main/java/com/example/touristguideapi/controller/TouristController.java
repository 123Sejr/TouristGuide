package com.example.touristguideapi.controller;

import com.example.touristguideapi.model.*;
import com.example.touristguideapi.service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Controller
@RequestMapping("attractions")
public class TouristController {
    private final TouristService service;


    public TouristController(TouristService touristService) {
        this.service = touristService;

    }

    // GET-endpoint, der henter alle turistattraktioner fra service layer.
    // Returnerer html-siden attractionList.
    @GetMapping()
    public String getTouristAttractions(Model model) {
        ArrayList<TouristAttraction> attractions = service.getTouristAttractions();
        model.addAttribute("attractionList", attractions);
        return "attractionList";
    }

    // GET-endpoint, der viser en enkelt turistattraktion på sin egen HTML side.
    // Attraktionen lægges i Model, så Thymeleaf kan vise dens data.
    // Findes den ikke, sendes brugeren tilbage til oversigten.
    @GetMapping("/{name}")
    public String getName(@PathVariable String name, Model model) {
        TouristAttraction attraction = service.findAttractionByName(name);

        if (attraction == null) {
            return "redirect:/attractions";
        }

        model.addAttribute("attraction", attraction);
        return "attractionDetails";
    }


    @GetMapping("/add-attraction")
    public String submitAttraction() {
        return "add-attraction";
    }

    @PostMapping("/save")
    public String addAttraction(TouristAttraction attraction, Model model) {
        service.addAttraction(attraction);
        model.addAttribute("attraction", attraction);
        return "successful";
    }

    /*GET-endpoint, der henter information om en specifik attraktion fra service layer og
    * returnere html-siden editAttraction, der giver brugeren lov til at ændre på attraktionens oplysninger,
    * bortset fra dens navn.*/
    @GetMapping("{name}/edit")
    public String editAttraction(Model model, @PathVariable String name) {
        TouristAttraction attraction = service.findAttractionByName(name);
        model.addAttribute("attraction", attraction);

        List<String> cityList = service.getCities();
        model.addAttribute("cityList", cityList);

        String[] tagsList = attraction.getTags();
        model.addAttribute("tagsList", tagsList);
        return "editAttraction";
    }


    /*POST-endpoint der gemmer de ændret oplysninger på attraktionen valgt fra endpointet "{name}/edit" og
    * opdatere listen af attraktioner gennem service til repository'et.
    * updateAttraction.html bliver returneret som viser de gemte oplysninger på attraktionen*/
    @PostMapping("/update")
    public String updateAttraction(Model model, @ModelAttribute("attraction") TouristAttraction attraction) {
        model.addAttribute("attraction", attraction);
        service.updateAttraction(attraction.getName(), attraction);

        String[] tagsList = attraction.getTags();
        model.addAttribute("tagsList", tagsList);
        return "updateAttraction";
    }


    //POST-endpoint der sletter på en eksisterende attraktion.
    // Kalder på service layer, som derefter kalder på repository layer.
    // Returnerer til klienten med status 200 (OK) når attraktionen blev slettet.
    @PostMapping("/{name}/delete")
    public String removeAttraction(@PathVariable String name, Model model) {
        model.addAttribute("name", name);
        service.removeAttraction(name);
        return "deleted";
    }

    @GetMapping("/{name}/tags")
    public String getAttractionTags(@PathVariable String name, Model model){
        TouristAttraction attraction = service.findAttractionByName(name);
         model.addAttribute("attraction", attraction);
         model.addAttribute("taglist", attraction.getTags());
        return "tags";
    }
}
