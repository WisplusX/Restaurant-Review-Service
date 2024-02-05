package com.example.restaurantreview.services;

import com.example.restaurantreview.models.Restaurant;
import com.example.restaurantreview.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с ресторанами.
 */
@RequiredArgsConstructor
@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

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
     * Метод для получения списка всех ресторанов.
     *
     * @return список всех ресторанов.
     */
    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
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
