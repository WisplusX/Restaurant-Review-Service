package com.example.restaurantreview.services;

import com.example.restaurantreview.models.Restaurant;
import com.example.restaurantreview.models.Review;
import com.example.restaurantreview.repositories.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit-тесты для класса ReviewService.
 */
@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @InjectMocks
    private ReviewService reviewService;
    @Mock
    private ReviewRepository reviewRepository;

    /**
     * Тест метода save.
     */
    @Test
    void testSave() {
        Review mockReview = createMockReview();

        when(reviewRepository.save(mockReview)).thenReturn(mockReview);
        assertEquals(mockReview, reviewService.save(mockReview));
    }

    /**
     * Тест метода findById в случае, когда отзыв не найден (возвращает Optional.empty()).
     */
    @Test
    void testFindById_notFound_returnsOptionalEmpty() {
        when(reviewRepository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertFalse(reviewService.findById(1).isPresent());
    }

    /**
     * Тест метода findById в случае, когда отзыв найден (возвращает Optional).
     */
    @Test
    void testFindById_found_returnsOptional() {
        Review mockReview = createMockReview();

        when(reviewRepository.findById(1)).thenReturn(Optional.of(mockReview));

        Optional<Review> result = reviewService.findById(1);

        assertTrue(result.isPresent());
        assertEquals(mockReview, result.get());
    }

    /**
     * Тест метода findAll.
     */
    @Test
    void testFindAll() {
        Review mockReview = createMockReview();

        List<Review> list = Collections.singletonList(mockReview);

        when(reviewRepository.findAll()).thenReturn(list);

        assertEquals(list, reviewService.findAll());
    }

    /**
     * Тест метода testUpdate в случае, когда ресторан не найден.
     */
    @Test
    void testUpdate_notFound_returnsOptionalEmpty() {
        Review mockReview = createMockReview();

        when(reviewRepository.findById(1)).thenReturn(Optional.empty());

        assertEquals(Optional.empty(), reviewService.update(1, mockReview));
    }

    /**
     * Тест метода testUpdate в случае, когда ресторан найден.
     */
    @Test
    void testUpdate_found_returnsOptional() {
        Review mockReview = createMockReview();

        Optional<Review> optionalMockReview = Optional.of(mockReview);

        when(reviewRepository.findById(1)).thenReturn(optionalMockReview);
        when(reviewRepository.save(mockReview)).thenReturn(mockReview);

        Optional<Review> result = reviewService.update(1, mockReview);

        assertTrue(result.isPresent());
        assertEquals(mockReview, result.get());

        verify(reviewRepository, times(1)).findById(1);
        verify(reviewRepository, times(1)).save(mockReview);
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