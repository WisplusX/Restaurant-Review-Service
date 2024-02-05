package com.example.restaurantreview.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Модель представляющая ресторан.
 */
@Data
@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Name is required")
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @PositiveOrZero(message = "Rating must be positive or zero")
    @NotNull(message = "Average rating is required")
    @Column(name = "avg_rating")
    private double avgRating;
    @NotBlank(message = "Location is required")
    @Column(name = "location")
    private String location;
    @Column(name = "cuisine")
    private String cuisine;
    @NotBlank(message = "Price range is required")
    @Size(min = 1, max = 5, message = "Price range must be between 1 and 5")
    @Column(name = "price_range")
    private String priceRange;
    @Column(name = "photo")
    private String photo;

    /**
     * Конструктор по умолчанию.
     */
    public Restaurant() {
    }

    /**
     * Конструктор с параметрами.
     *
     * @param name название ресторана.
     * @param description описание ресторана.
     * @param avgRating средний рейтинг ресторана.
     * @param location местоположение ресторана.
     * @param cuisine тип кухни ресторана.
     * @param priceRange ценовой диапазон ресторана.
     * @param photo фотография ресторана.
     */
    public Restaurant(String name, String description, double avgRating, String location, String cuisine, String priceRange, String photo) {
        this.name = name;
        this.description = description;
        this.avgRating = avgRating;
        this.location = location;
        this.cuisine = cuisine;
        this.priceRange = priceRange;
        this.photo = photo;
    }
}
