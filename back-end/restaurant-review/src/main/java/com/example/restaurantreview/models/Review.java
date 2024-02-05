package com.example.restaurantreview.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Модель представляющая отзыв о ресторане.
 */
@Data
@Entity
@Table(name = "review")
public class Review {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "text")
    private String text;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    @Column(name = "rating")
    private int rating;

    @NotNull(message = "Creation date is required")
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @NotNull(message = "Restaurant ID is required")
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @NotBlank(message = "Author name is required")
    @Column(name = "author_name")
    private String authorName;

    @Email(message = "Invalid email format")
    @Column(name = "author_email")
    private String authorEmail;

    /**
     * Конструктор по умолчанию.
     */
    public Review() {
    }

    /**
     * Конструктор с параметрами.
     *
     * @param text текст отзыва.
     * @param rating оценка отзыва.
     * @param creationDate дата создания отзыва.
     * @param restaurant ресторан, к которому относится отзыв.
     * @param authorName имя автора отзыва.
     * @param authorEmail электронная почта автора отзыва.
     */
    public Review(String text, int rating, LocalDateTime creationDate, Restaurant restaurant, String authorName,
                  String authorEmail) {
        this.text = text;
        this.rating = rating;
        this.creationDate = creationDate;
        this.restaurant = restaurant;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
    }
}
