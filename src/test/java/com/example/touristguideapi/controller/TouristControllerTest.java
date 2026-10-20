package com.example.touristguideapi.controller;

import com.example.touristguideapi.model.Tag;
import com.example.touristguideapi.model.TouristAttraction;
import com.example.touristguideapi.service.TouristService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Array;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TouristController.class)
class TouristControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //En stand-in for vores service-klasse
    @MockitoBean
    private TouristService touristService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    /*Her skal der testes at en GET request til /attractions endpointet returnerer en 200 OK status
    * og view navnet html siden "attractionList".*/
    @Test
    void shouldGetToursitAttrctions() throws Exception {
        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractionList"));
    }

    /*Her skal der testes at en GET request til /{name} endpointet returnerer en 200 OK status
    * og view navnet på html siden "attractionDetails".*/
    @Test
    void getName() throws Exception{
        TouristAttraction attraction = new TouristAttraction(1, "Tivoli", "Forlystelsespark i indre København",
                1, new ArrayList<>(List.of("ART", "MUSEUM")));
        when(touristService.findAttractionById(attraction.getId())).thenReturn(attraction);

        mockMvc.perform(get("/attractions/Tivoli"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractionDetails"));
    }

    /*Her skal der testes at en GET request til /add-attraction endpointet returnerer en
    * 200 OK status og view navnet på html siden "add-attraction".*/
    @Test
    void shouldSubmitAttraction() throws Exception {
        mockMvc.perform(get("/attractions/add-attraction"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-attraction"));
    }

    /*Her skal der testes en form submission via en POST request med parameter til /save endpointet returnerer en
    * 200 OK status og view navnet på html siden "add-attraction".*/
    @Test
    void shouldAddAttraction() throws Exception {
        //Simuler en POST request til /save endpointet med de nødvendige parametre:
        mockMvc.perform(post("/attractions/save")
                .param("name", "Den lille havfrue")
                .param("description", "Figur fra H.C.Andersens eventyr.")
                .param("city", "København")
                .param("tags", "ART"))
                .andExpect(status().isOk())
                .andExpect(view().name("successful"));

        //Dernæst vil man gerne verificere at servicemetoden bliver kaldt med de forventede argumenter.
        //Mockitos verify() funktion bruges til at verificere at service metoden addAttraction() bliver kaldt og
        // med det forventede touristAttraction argument.

        ArgumentCaptor<TouristAttraction> captor = ArgumentCaptor.forClass(TouristAttraction.class);
        verify(touristService).addAttraction(captor.capture());

        TouristAttraction captured = captor.getValue();
        assertEquals("Den lille havfrue", captured.getName());
        assertEquals("Figur fra H.C.Andersens eventyr.", captured.getDescription());
        assertEquals(1, captured.getCityID());
        // assertEquals("Art", captured.getTags());
    }

    /*Her skal der testes at en GET request til /{name}/edit endpointet returnerer en
     * 200 OK status og view navnet på html siden "editAttraction".*/
    @Test
    void shouldEditAttraction() throws Exception{
        TouristAttraction attraction = new TouristAttraction(1, "Tivoli", "Forlystelsespark i indre København",
                1, new ArrayList<>(List.of("ART", "MUSEUM")));
        when(touristService.findAttractionById(attraction.getId())).thenReturn(attraction);

        mockMvc.perform(get("/attractions/Tivoli/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("editAttraction"));
    }

    /*Her skal der testes at en POST request til /update endpointet returnerer en
     * 200 OK status og view navnet på html siden "updateAttraction".*/
    /*@Test
    void updateAttraction() throws Exception {
        //Simuler en POST request til /update endpointet med de nødvendige parametre:
        mockMvc.perform(post("/attractions/update")
                        .param("name", "Tivoli")
                        .param("description", "Forlystelsespark i indre København")
                        .param("city", "København")
                        .param("tags", "AMUSEMENT_PARK"))
                .andExpect(status().isOk())
                .andExpect(view().name("updateAttraction"));

        ArgumentCaptor<TouristAttraction> captor = ArgumentCaptor.forClass(TouristAttraction.class);
        verify(touristService).updateAttraction("Tivoli", captor.capture());
        //verify(touristService).updateAttraction("Tivoli", updatedAttraction);

        TouristAttraction captured = captor.getValue();
        assertEquals("Tivoli", captured.getName());
        assertEquals("Forlystelsespark i indre København", captured.getDescription());
        assertEquals("København", captured.getCity());
        assertEquals(EnumSet.of(Tag.AMUSEMENT_PARK), captured.getTags());
    }*/

    /*Her skal der testes at en POST request til /{name}/delete endpointet returnerer en
     * 200 OK status og view navnet på html siden "deleted".*/
    @Test
    void removeAttraction() throws Exception {
        TouristAttraction attraction = new TouristAttraction(1, "Tivoli", "Forlystelsespark i indre København",
                1, new ArrayList<>(List.of("ART", "MUSEUM")));
        when(touristService.findAttractionById(attraction.getId())).thenReturn(attraction);

        touristService.removeAttraction(attraction.getId());

        mockMvc.perform(post("/attractions/Tivoli/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("deleted"));

        //verify(touristService).removeAttraction(attraction.getName());

    }

    /*Her skal der testes at en GET request til /{name}/tags endpointet returnerer en
     * 200 OK status og view navnet på html siden "tags".*/
    @Test
    void shouldGetAttractionTags() throws Exception {
        TouristAttraction attraction = new TouristAttraction(1, "Tivoli", "Forlystelsespark i indre København",
                1, new ArrayList<>(List.of("ART", "MUSEUM")));
        when(touristService.findAttractionById(attraction.getId())).thenReturn(attraction);

        mockMvc.perform(get("/attractions/Tivoli/tags"))
                .andExpect(status().isOk())
                .andExpect(view().name("tags"))
                .andExpect(model().attributeExists("taglist"))
                .andExpect(model().attribute("attraction", attraction));
    }
}