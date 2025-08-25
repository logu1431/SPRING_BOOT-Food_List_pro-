package com.example.demo;

import com.example.demo.controller.FoodController;
import com.example.demo.modal.Food_fav;
import com.example.demo.repository.FoodFavRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(FoodController.class)
class DemoApplicationTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FoodFavRepository foodFavRepository;

    private final ObjectMapper mapper = new ObjectMapper();


    private Food_fav makeFood(int id, String name, String veg, int vegPrice, String nonVeg, int nonVegPrice, String reason) {
        Food_fav f = new Food_fav(name, veg, vegPrice, nonVeg, nonVegPrice, reason);
        try {
            Food_fav.class.getMethod("setId", int.class).invoke(f, id);
        } catch (Exception ignore) { /* if no setter, ignore id asserts */ }
        return f;
    }

    // ---------- GET /api/foods
    @Test
    @DisplayName("GET /api/foods -> 200 with list")
    void getAllFoods_ok() throws Exception {
    List<Food_fav> data = Arrays.asList(
        makeFood(1, "A", "VegA", 100, "NonVegA", 200, "ReasonA"),
        makeFood(2, "B", "VegB", 150, "NonVegB", 250, "ReasonB")
    );
    given(foodFavRepository.findAll()).willReturn(data);

    mvc.perform(get("/api/foods"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].name").value("A"))
        .andExpect(jsonPath("$[1].vegPrice").value(150));
    }

    // ---------- GET /api/foods/{id}
    @Test
    @DisplayName("GET /api/foods/{id} -> 200 when found")
    void getFoodById_found() throws Exception {
        Food_fav f = makeFood(10, "Title", "Veg", 120, "NonVeg", 220, "Reason");
        given(foodFavRepository.findById(10)).willReturn(Optional.of(f));

        mvc.perform(get("/api/foods/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Title"))
                .andExpect(jsonPath("$.vegPrice").value(120));
    }

    // ---------- POST /api/foods
    @Test
    @DisplayName("POST /api/foods -> 201 Created")
    void createFood_created() throws Exception {
        Food_fav request = new Food_fav("New", "VegNew", 130, "NonVegNew", 230, "ReasonNew");
        Food_fav saved = makeFood(100, "New", "VegNew", 130, "NonVegNew", 230, "ReasonNew");

        given(foodFavRepository.save(ArgumentMatchers.any(Food_fav.class))).willReturn(saved);

        mvc.perform(post("/api/foods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New"));
    }

    // ---------- PUT /api/foods/{id}
    @Test
    @DisplayName("PUT /api/foods/{id} -> 200 when found and updated")
    void updateFood_ok() throws Exception {
        Food_fav existing = makeFood(5, "Old", "VegOld", 110, "NonVegOld", 210, "ReasonOld");
        Food_fav updateReq = new Food_fav("NewTitle", "VegNew", 140, "NonVegNew", 240, "ReasonNew");
        Food_fav updated = makeFood(5, "NewTitle", "VegNew", 140, "NonVegNew", 240, "ReasonNew");

        given(foodFavRepository.findById(5)).willReturn(Optional.of(existing));
        given(foodFavRepository.save(any(Food_fav.class))).willReturn(updated);

        mvc.perform(put("/api/foods/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("NewTitle"))
                .andExpect(jsonPath("$.vegPrice").value(140));
    }

    @Test
    @DisplayName("PUT /api/foods/{id} -> 404 when not found")
    void updateFood_notFound() throws Exception {
        given(foodFavRepository.findById(123)).willReturn(Optional.empty());

        Food_fav req = new Food_fav("T", "VegT", 100, "NonVegT", 200, "ReasonT");
        mvc.perform(put("/api/foods/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }

    // ---------- DELETE /api/foods/{id}
    @Test
    @DisplayName("DELETE /api/foods/{id} -> 204")
    void deleteFood_ok() throws Exception {
        doNothing().when(foodFavRepository).deleteById(7);

        mvc.perform(delete("/api/foods/7"))
                .andExpect(status().isNoContent());

        verify(foodFavRepository, times(1)).deleteById(7);
    }

    // ---------- DELETE /api/foods
    @Test
    @DisplayName("DELETE /api/foods -> 204")
    void deleteAllFoods_ok() throws Exception {
        doNothing().when(foodFavRepository).deleteAll();

        mvc.perform(delete("/api/foods"))
                .andExpect(status().isNoContent());

        verify(foodFavRepository, times(1)).deleteAll();
    }

}