package com.example.restaurantreview.repositories;

import com.example.restaurantreview.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с отзывами.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    /**
     * Находит все отзывы для указанного ресторана, сортируя их по дате создания в порядке убывания.
     *
     * @param restaurantId идентификатор ресторана
     * @return список отзывов для указанного ресторана, отсортированный по дате создания в порядке убывания
     */
    List<Review> findAllByRestaurantIdOrderByCreationDateDesc(int restaurantId);

    /**
     * Выполняет запрос для подсчета количества отзывов для каждого ресторана.
     *
     * @return список массивов объектов, содержащих идентификатор ресторана и количество отзывов для него
     */
    @Query("SELECT r.restaurant.id, COUNT(r) FROM Review r GROUP BY r.restaurant.id")
    List<Object[]> findReviewsCountByRestaurantId();
}
