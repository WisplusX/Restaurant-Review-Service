package com.example.restaurantreview.repositories;

import com.example.restaurantreview.models.Restaurant;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с ресторанами.
 */
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    /**
     * Находит список ресторанов по указанной кухне с игнорированием регистра.
     *
     * @param cuisine тип кухни для поиска
     * @param sort объект Sort для сортировки результатов
     * @return список ресторанов с указанной кухней, отсортированный согласно переданному объекту Sort
     */
    List<Restaurant> findByCuisineIgnoreCase(String cuisine, Sort sort);
}
