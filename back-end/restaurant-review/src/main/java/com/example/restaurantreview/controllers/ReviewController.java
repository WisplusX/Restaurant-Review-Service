package com.example.restaurantreview.controllers;

import com.example.restaurantreview.models.Restaurant;
import com.example.restaurantreview.models.Review;
import com.example.restaurantreview.services.RestaurantService;
import com.example.restaurantreview.services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Контроллер для обработки запросов, связанных с отзывами о ресторанах.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final RestaurantService restaurantService;

    /**
     * Обработчик GET запроса для получения списка всех отзывов.
     *
     * @return ResponseEntity с HTTP статусом OK и списком всех отзывов в теле ответа.
     */
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.findAll());
    }

    /**
     * Обработчик GET запроса для получения отзыва по его идентификатору.
     *
     * @param id идентификатор отзыва.
     * @return ResponseEntity с HTTP статусом OK и отзывом в теле ответа, если отзыв найден,
     * либо HTTP статусом NOT_FOUND, если отзыв не найден.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable int id) {
        Optional<Review> review = reviewService.findById(id);
        if (review.isPresent()) {
            return ResponseEntity.ok(review.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Обработчик GET запроса для получения всех отзывов по идентификатору ресторана.
     *
     * @param id Идентификатор ресторана, для которого нужно получить отзывы.
     * @return ResponseEntity с HTTP статусом OK и списком всех отзывов для указанного ресторана в теле ответа.
     */
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<Review>> getAllReviewsByRestaurantId(@PathVariable int id) {
        return ResponseEntity.ok(reviewService.findAllByRestaurantId(id));
    }

    /**
     * Обработчик POST запроса для создания нового отзыва.
     *
     * @param restaurantId идентификатор ресторана, для которого создается отзыв (передается как параметр запроса).
     * @param review новый отзыв, переданный в теле запроса.
     * @return ResponseEntity с HTTP статусом CREATED и созданным отзывом в теле ответа.
     */
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestParam int restaurantId, @Valid @RequestBody Review review) {
        Optional<Restaurant> restaurantOptional = restaurantService.findById(restaurantId);

        if (restaurantOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Restaurant restaurant = restaurantOptional.get();

            if (restaurant.getAvgRating() == 0) {
                restaurant.setAvgRating(review.getRating());
            } else {
                double avgRating = restaurant.getAvgRating();
                int numberOfReviews = reviewService.findAllByRestaurantId(restaurant.getId()).size();
                double newAvgRating = ((avgRating * numberOfReviews) + review.getRating()) / (numberOfReviews + 1);

                restaurant.setAvgRating(newAvgRating);
            }

            review.setRestaurant(restaurantOptional.get());
            review.setCreationDate(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(reviewService.save(review));
        }
    }

    /**
     * Обработчик PUT запроса для обновления существующего отзыва.
     *
     * @param id     идентификатор отзыва, который нужно обновить.
     * @param review новая информация об отзыве, переданная в теле запроса.
     * @return ResponseEntity с HTTP статусом OK и обновленным отзывом в теле ответа,
     * либо HTTP статусом NOT_FOUND, если отзыв с указанным идентификатором не найден.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable int id, @Valid @RequestBody Review review) {
        Optional<Review> updatedReview = reviewService.update(id, review);
        if (updatedReview.isPresent()) {
            return ResponseEntity.ok(updatedReview.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Обработчик DELETE запроса для удаления отзыва по его идентификатору.
     *
     * @param id идентификатор отзыва, который нужно удалить.
     * @return ResponseEntity с HTTP статусом NO_CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable int id) {
        reviewService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
