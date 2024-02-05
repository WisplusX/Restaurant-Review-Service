package com.example.restaurantreview.repositories;

import com.example.restaurantreview.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с ресторанами.
 */
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
}
