package com.example.restaurantreview.services;

import com.example.restaurantreview.models.Restaurant;
import com.example.restaurantreview.repositories.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit-тесты для класса RestaurantService.
 */
@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {
    @InjectMocks
    private RestaurantService restaurantService;
    @Mock
    private RestaurantRepository restaurantRepository;

    /**
     * Тест метода save.
     */
    @Test
    void testSave() {
        Restaurant mockRestaurant = createMockRestaurant();

        when(restaurantRepository.save(mockRestaurant)).thenReturn(mockRestaurant);
        assertEquals(mockRestaurant, restaurantService.save(mockRestaurant));
    }

    /**
     * Тест метода findById в случае, когда ресторан не найден (возвращает Optional.empty()).
     */
    @Test
    void testFindById_notFound_returnsOptionalEmpty() {
        when(restaurantRepository.findById(1)).thenReturn(Optional.empty());
        assertFalse(restaurantService.findById(1).isPresent());
    }

    /**
     * Тест метода findById в случае, когда ресторан найден (возвращает Optional).
     */
    @Test
    void testFindById_found_returnsOptional() {
        Restaurant mockRestaurant = createMockRestaurant();

        when(restaurantRepository.findById(1)).thenReturn(Optional.of(mockRestaurant));

        Optional<Restaurant> result = restaurantService.findById(1);

        assertTrue(result.isPresent());
        assertEquals(mockRestaurant, result.get());
    }

    /**
     * Тест метода findAll.
     */
    @Test
    void testFindAll() {
        Restaurant mockRestaurant = createMockRestaurant();

        List<Restaurant> list = Collections.singletonList(mockRestaurant);

        Sort sort = Sort.by(Sort.Direction.ASC, "avgRating");

        when(restaurantRepository.findAll(sort)).thenReturn(list);

        assertEquals(list, restaurantService.findAll(sort));
    }

    /**
     * Тест метода testUpdate в случае, когда ресторан не найден.
     */
    @Test
    void testUpdate_notFound_returnsOptionalEmpty() {
        Restaurant mockRestaurant = createMockRestaurant();

        when(restaurantRepository.findById(1)).thenReturn(Optional.empty());

        assertEquals(Optional.empty(), restaurantService.update(1, mockRestaurant));
    }

    /**
     * Тест метода testUpdate в случае, когда ресторан найден.
     */
    @Test
    void testUpdate_found_returnsOptional() {
        Restaurant mockRestaurant = createMockRestaurant();

        Optional<Restaurant> optionalMockRestaurant = Optional.of(mockRestaurant);

        when(restaurantRepository.findById(1)).thenReturn(optionalMockRestaurant);
        when(restaurantRepository.save(mockRestaurant)).thenReturn(mockRestaurant);

        Optional<Restaurant> result = restaurantService.update(1, mockRestaurant);

        assertTrue(result.isPresent());
        assertEquals(mockRestaurant, result.get());

        verify(restaurantRepository, times(1)).findById(1);
        verify(restaurantRepository, times(1)).save(mockRestaurant);
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