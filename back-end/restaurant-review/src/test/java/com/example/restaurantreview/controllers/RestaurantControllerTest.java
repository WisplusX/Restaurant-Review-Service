package com.example.restaurantreview.controllers;

import com.example.restaurantreview.models.Restaurant;
import com.example.restaurantreview.services.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit-тесты для класса RestaurantController.
 */
@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {
    @InjectMocks
    private RestaurantController restaurantController;
    @Mock
    private RestaurantService restaurantService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
        objectMapper = new ObjectMapper();
    }

    /**
     * Тест метода getRestaurantById.
     *
     * @throws Exception если произошла ошибка во время выполнения теста.
     */
    @Test
    public void testGetRestaurantById() throws Exception {
        // Создание макетного объекта ресторана
        Restaurant mockRestaurant = createMockRestaurant();

        // Установка поведения макета для сервиса ресторана
        when(restaurantService.findById(1)).thenReturn(Optional.of(mockRestaurant));

        // Выполнение HTTP GET запроса и проверка результата
        mockMvc.perform(get("/api/restaurants/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Restaurant"))
                .andExpect(jsonPath("$.description").value("Desc"))
                .andExpect(jsonPath("$.location").value("Location"))
                .andExpect(jsonPath("$.avgRating").value(3.5))
                .andExpect(jsonPath("$.photo").value("Photo URL"))
                .andExpect(jsonPath("$.cuisine").value("Greece"))
                .andExpect(jsonPath("$.priceRange").value("$$$$"));

        // Проверка вызова метода сервиса
        verify(restaurantService, times(1)).findById(1);
    }

    /**
     * Тест метода getAllRestaurants.
     *
     * @throws Exception если произошла ошибка во время выполнения теста.
     */
    @Test
    public void testGetAllRestaurants() throws Exception {
        // Создание макетного объекта ресторана
        Restaurant mockRestaurant = createMockRestaurant();

        // Создание списка с макетным рестораном
        List<Restaurant> list = Collections.singletonList(mockRestaurant);

        // Установка поведения макета для сервиса ресторана
        when(restaurantService.findAll()).thenReturn(list);

        // Выполнение HTTP GET запроса и проверка результата
        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Restaurant"))
                .andExpect(jsonPath("$[0].description").value("Desc"))
                .andExpect(jsonPath("$[0].location").value("Location"))
                .andExpect(jsonPath("$[0].avgRating").value(3.5))
                .andExpect(jsonPath("$[0].photo").value("Photo URL"))
                .andExpect(jsonPath("$[0].cuisine").value("Greece"))
                .andExpect(jsonPath("$[0].priceRange").value("$$$$"));

        // Проверка вызова метода сервиса
        verify(restaurantService, times(1)).findAll();
    }

    /**
     * Тест метода createRestaurant.
     *
     * @throws Exception если произошла ошибка во время выполнения теста.
     */
    @Test
    public void testCreateRestaurant() throws Exception {
        // Создание макетного объекта ресторана
        Restaurant mockRestaurant = createMockRestaurant();

        // Преобразование макетного объекта ресторана в JSON строку
        String jsonRestaurant = objectMapper.writeValueAsString(mockRestaurant);

        // Выполнение HTTP POST запроса и проверка результата
        mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRestaurant))
                .andExpect(status().isCreated());

        // Проверка вызова метода сервиса
        verify(restaurantService, times(1)).save(mockRestaurant);
    }

    /**
     * Тест метода updateRestaurant.
     *
     * @throws Exception если произошла ошибка во время выполнения теста.
     */
    @Test
    public void testUpdateRestaurant() throws Exception {
        // Создание макетного объекта ресторана
        Restaurant mockRestaurant = createMockRestaurant();

        // Преобразование макетного объекта ресторана в JSON строку
        String jsonRestaurant = objectMapper.writeValueAsString(mockRestaurant);

        // Установка поведения макета для метода update сервиса ресторана
        when(restaurantService.update(1, mockRestaurant)).thenReturn(Optional.of(mockRestaurant));

        // Выполнение HTTP PUT запроса и проверка результата
        mockMvc.perform(put("/api/restaurants/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRestaurant))
                .andExpect(status().isOk());

        // Проверка вызова метода update сервиса ресторана
        verify(restaurantService, times(1)).update(1, mockRestaurant);
    }

    /**
     * Тест метода deleteRestaurant.
     *
     * @throws Exception если произошла ошибка во время выполнения теста.
     */
    @Test
    public void testDeleteRestaurant() throws Exception {
        // Установка поведения макета для метода deleteById сервиса ресторана
        doNothing().when(restaurantService).deleteById(1);

        // Выполнение HTTP DELETE запроса и проверка результата
        mockMvc.perform(delete("/api/restaurants/{id}", 1))
                .andExpect(status().isNoContent());

        // Проверка вызова метода deleteById сервиса ресторана
        verify(restaurantService, times(1)).deleteById(1);
    }

    /**
     * Создание макетного объекта ресторана для использования в тестах.
     *
     * @return макетный объект ресторана
     */
    private Restaurant createMockRestaurant() {
        Restaurant mockRestaurant = new Restaurant();
        mockRestaurant.setId(1);
        mockRestaurant.setName("Restaurant");
        mockRestaurant.setDescription("Desc");
        mockRestaurant.setLocation("Location");
        mockRestaurant.setAvgRating(3.5);
        mockRestaurant.setPhoto("Photo URL");
        mockRestaurant.setCuisine("Greece");
        mockRestaurant.setPriceRange("$$$$");
        return mockRestaurant;
    }
}