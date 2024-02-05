package com.example.restaurantreview.services;

import com.example.restaurantreview.models.Review;
import com.example.restaurantreview.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с отзывами.
 */
@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    /**
     * Метод для сохранения отзыва.
     *
     * @param review отзыв для сохранения.
     * @return сохраненный отзыв.
     */
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    /**
     * Метод для поиска отзыва по его идентификатору.
     *
     * @param id идентификатор отзыва.
     * @return найденный отзыв (если существует).
     */
    public Optional<Review> findById(int id) {
        return reviewRepository.findById(id);
    }

    /**
     * Метод для получения списка всех отзывов.
     *
     * @return список всех отзывов.
     */
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    /**
     * Метод для обновления информации о отзыве.
     *
     * @param id идентификатор отзыва, который нужно обновить.
     * @param review новая информация о отзыве.
     * @return обновленный отзыв (если существует).
     */
    public Optional<Review> update(int id, Review review) {
        Optional<Review> reviewToUpdate = findById(id);
        if (reviewToUpdate.isPresent()) {
            Review newReview = reviewToUpdate.get();
            newReview.setId(review.getId());
            newReview.setText(review.getText());
            newReview.setRating(review.getRating());
            newReview.setRestaurant(review.getRestaurant());
            newReview.setCreationDate(review.getCreationDate());
            newReview.setAuthorName(review.getAuthorName());
            newReview.setAuthorEmail(review.getAuthorEmail());
            return Optional.of(reviewRepository.save(newReview));
        }
        return Optional.empty();
    }

    /**
     * Метод для удаления отзыва по его идентификатору.
     *
     * @param id идентификатор отзыва, который нужно удалить.
     */
    public void deleteById(int id) {
        reviewRepository.deleteById(id);
    }
}
