package com.example.restaurantreview.repositories;

import com.example.restaurantreview.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с отзывами.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
