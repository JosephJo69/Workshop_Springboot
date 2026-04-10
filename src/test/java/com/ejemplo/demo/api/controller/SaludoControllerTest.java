package com.ejemplo.demo.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import com.ejemplo.demo.api.dto.SaludoResponse;
import com.ejemplo.demo.domain.service.SaludoService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SaludoController.class)
class SaludoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private SaludoService saludoService;

    @Test
    @DisplayName("Debe responder health del workshop")
    void debeResponderHealthDelWorkshop() throws Exception {
        mockMvc.perform(get("/api/v1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("ok"));
        
        
    }

    @Test
    @DisplayName("GET /saludos debe responder 200 y mensaje correcto")
    void debeSaludarCorrectamente() throws Exception {
        // Configuramos el mock para que devuelva un saludo ficticio
        org.mockito.Mockito.when(saludoService.crearSaludo("Ana"))
            .thenReturn(new SaludoResponse("Hola, Estudiante Ana...", java.time.Instant.now()));

        mockMvc.perform(get("/api/v1/saludos").param("nombre", "Ana"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").exists());
    }

    @Test
    @DisplayName("POST /saludos con nombre vacío debe dar error")
    void debeDarErrorValidacion() throws Exception {
        mockMvc.perform(post("/api/v1/saludos")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"\"}"))
                .andExpect(status().isBadRequest()); 
    }
}
