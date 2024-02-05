package com.example.restaurantreview.controllers;

import com.example.restaurantreview.models.Restaurant;
import com.example.restaurantreview.models.Review;
import com.example.restaurantreview.services.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit-тесты для класса ReviewController.
 */
@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {
    @InjectMocks
    private ReviewController reviewController;
    @Mock
    private ReviewService reviewService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Тест метода getReviewById.
     *
     * @throws Exception если произошла ошибка во время выполнения теста.
     */
    @Test
    public void testGetReviewById() throws Exception {
        // Создание макетного объекта отзыва
        Review mockReview = createMockReview();

        // Установка поведения макета для сервиса отзывов
        when(reviewService.findById(1)).thenReturn(Optional.of(mockReview));

        // Выполнение HTTP GET запроса и проверка результата
        mockMvc.perform(get("/api/reviews/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("Text"))
                .andExpect(jsonPath("$.restaurant").value(new Restaurant()))
                .andExpect(jsonPath("$.rating").value(3))
                .andExpect(jsonPath("$.authorName").value("John Doe"))
                .andExpect(jsonPath("$.authorEmail").value("john@example.com"));

        // Проверка вызова метода сервиса отзывов
        verify(reviewService, times(1)).findById(1);
    }

    @Test
    public void testGetAllReviews() throws Exception {
        // Создание макетного объекта отзыва
        Review mockReview1 = createMockReview();

        List<Review> list = Collections.singletonList(mockReview1);

        // Установка поведения макета для сервиса отзывов
        when(reviewService.findAll()).thenReturn(list);

        // Выполнение HTTP GET запроса и проверка результата
        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].text").value("Text"))
                .andExpect(jsonPath("$[0].restaurant").value(new Restaurant()))
                .andExpect(jsonPath("$[0].rating").value(3))
                .andExpect(jsonPath("$[0].authorName").value("John Doe"))
                .andExpect(jsonPath("$[0].authorEmail").value("john@example.com"));

        // Проверка вызова метода сервиса отзывов
        verify(reviewService, times(1)).findAll();
    }

    @Test
    public void testCreateReview() throws Exception {
        // Создание макетного объекта отзыва
        Review mockReview1 = createMockReview();

        String jsonReview = objectMapper.writeValueAsString(mockReview1);

        // Выполнение HTTP POST запроса и проверка результата
        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReview))
                .andExpect(status().isCreated());

        // Проверка вызова метода сервиса отзывов
        verify(reviewService, times(1)).save(mockReview1);
    }

    @Test
    public void testUpdateReview() throws Exception {
        // Создание макетного объекта отзыва
        Review mockReview1 = createMockReview();

        String jsonReview = objectMapper.writeValueAsString(mockReview1);

        // Установка поведения макета для сервиса отзывов
        when(reviewService.update(1, mockReview1)).thenReturn(Optional.of(mockReview1));

        // Выполнение HTTP PUT запроса и проверка результата
        mockMvc.perform(put("/api/reviews/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReview))
                .andExpect(status().isOk());

        // Проверка вызова метода сервиса отзывов
        verify(reviewService, times(1)).update(1, mockReview1);
    }

    @Test
    public void testDeleteReview() throws Exception {
        // Установка поведения макета для сервиса отзывов
        doNothing().when(reviewService).deleteById(1);

        // Выполнение HTTP DELETE запроса и проверка результата
        mockMvc.perform(delete("/api/reviews/{id}", 1))
                .andExpect(status().isNoContent());

        // Проверка вызова метода сервиса отзывов
        verify(reviewService, times(1)).deleteById(1);
    }

    /**
     * Создание макетного объекта ресторана для использования в тестах.
     *
     * @return макетный объект ресторана
     */
    private Review createMockReview() {
        Review mockReview = new Review();
        mockReview.setId(1);
        mockReview.setText("Text");
        mockReview.setRestaurant(new Restaurant());
        mockReview.setRating(3);
        mockReview.setAuthorName("John Doe");
        mockReview.setAuthorEmail("john@example.com");
        mockReview.setCreationDate(LocalDateTime.now());
        return mockReview;
    }
}