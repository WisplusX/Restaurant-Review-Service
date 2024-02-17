package com.example.restaurantreview.services;

import com.example.restaurantreview.models.Restaurant;
import com.example.restaurantreview.repositories.RestaurantRepository;
import com.example.restaurantreview.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис для работы с ресторанами.
 */
@RequiredArgsConstructor
@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;

    /**
     * Метод для сохранения ресторана.
     *
     * @param restaurant ресторан для сохранения.
     * @return сохраненный ресторан.
     */
    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    /**
     * Метод для поиска ресторана по его идентификатору.
     *
     * @param id идентификатор ресторана.
     * @return найденный ресторан (если существует).
     */
    public Optional<Restaurant> findById(int id) {
        return restaurantRepository.findById(id);
    }

    /**
     * Получает список всех ресторанов, с возможностью сортировки.
     *
     * @param sort объект Sort для сортировки списка ресторанов
     * @return список всех ресторанов, отсортированный согласно переданному объекту Sort
     */
    public List<Restaurant> findAll(Sort sort) {
        return restaurantRepository.findAll(sort);
    }

    /**
     * Находит список ресторанов в порядке популярности, основываясь на количестве отзывов.
     *
     * @return список ресторанов, отсортированных по убыванию количества отзывов
     */
    public List<Restaurant> findAllByPopularity() {
        // Получаем список всех ресторанов и их количество отзывов
        List<Object[]> restaurantReviewsCount = reviewRepository.findReviewsCountByRestaurantId();

        // Преобразуем результат запроса в Map для быстрого доступа по идентификатору ресторана
        Map<Integer, Integer> reviewsCountMap = restaurantReviewsCount.stream()
                .collect(Collectors.toMap(
                        obj -> ((Number) obj[0]).intValue(), // Идентификатор ресторана
                        obj -> ((Number) obj[1]).intValue()  // Количество отзывов
                ));


        // Сортируем рестораны по количеству отзывов (по убыванию)
        List<Restaurant> restaurants = restaurantRepository.findAll();
        restaurants.sort(Comparator.comparingInt(restaurant -> -reviewsCountMap.getOrDefault(restaurant.getId(), 0)));

        return restaurants;
    }

    /**
     * Получает список ресторанов по указанному типу кухни с возможностью сортировки.
     *
     * @param cuisine тип кухни для поиска
     * @param sort объект Sort для сортировки списка ресторанов
     * @return список ресторанов с указанным типом кухни, отсортированный согласно переданному объекту Sort
     */
    public List<Restaurant> findAllByCuisine(String cuisine, Sort sort) {
        return restaurantRepository.findByCuisineIgnoreCase(cuisine, sort);
    }

    /**
     * Метод для обновления информации о ресторане.
     *
     * @param id         идентификатор ресторана, который нужно обновить.
     * @param restaurant новая информация о ресторане.
     * @return обновленный ресторан (если существует).
     */
    public Optional<Restaurant> update(int id, Restaurant restaurant) {
        Optional<Restaurant> restaurantToUpdate = findById(id);
        if (restaurantToUpdate.isPresent()) {
            Restaurant newRestaurant = restaurantToUpdate.get();
            newRestaurant.setId(restaurant.getId());
            newRestaurant.setName(restaurant.getName());
            newRestaurant.setLocation(restaurant.getLocation());
            newRestaurant.setDescription(restaurant.getDescription());
            newRestaurant.setAvgRating(restaurant.getAvgRating());
            newRestaurant.setCuisine(restaurant.getCuisine());
            newRestaurant.setPriceRange(restaurant.getPriceRange());
            newRestaurant.setPhoto(restaurant.getPhoto());
            return Optional.of(restaurantRepository.save(newRestaurant));
        }
        return Optional.empty();
    }

    /**
     * Метод для удаления ресторана по его идентификатору.
     *
     * @param id идентификатор ресторана, который нужно удалить.
     */
    public void deleteById(int id) {
        restaurantRepository.deleteById(id);
    }
}
