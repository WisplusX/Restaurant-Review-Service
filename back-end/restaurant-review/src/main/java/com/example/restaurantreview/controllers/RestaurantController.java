package com.example.restaurantreview.controllers;

import com.example.restaurantreview.models.Restaurant;
import com.example.restaurantreview.services.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер для обработки запросов, связанных с ресторанами.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    /**
     * Обработчик GET запроса для получения списка всех ресторанов.
     *
     * @param sortBy    Параметр для сортировки списка ресторанов (по умолчанию "avgRating").
     * @param filterBy  Параметр для фильтрации списка ресторанов по типу кухни (по умолчанию "").
     * @param sortOrder Параметр для указания порядка сортировки (по умолчанию "asc").
     * @return ResponseEntity с HTTP статусом OK и списком всех ресторанов в теле ответа.
     */
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants(
            @RequestParam(defaultValue = "avgRating") String sortBy,
            @RequestParam(defaultValue = "") String filterBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = sortOrder.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        List<Restaurant> restaurants;

        if (!filterBy.isEmpty()) {
            restaurants = restaurantService.findAllByCuisine(filterBy, sort);
        } else {
            restaurants = restaurantService.findAll(sort);
        }

        return ResponseEntity.ok(restaurants);
    }


    /**
     * Обработчик GET запроса для получения списка ресторанов отсортированных по кол-ву оценок.
     *
     * @return ResponseEntity с HTTP статусом OK и списком популярных ресторанов в теле ответа.
     */
    @GetMapping("/popular")
    public ResponseEntity<List<Restaurant>> getRestaurantsByPopularity() {
        return ResponseEntity.ok(restaurantService.findAllByPopularity());
    }

    /**
     * Обработчик GET запроса для получения ресторана по его идентификатору.
     *
     * @param id идентификатор ресторана.
     * @return ResponseEntity с HTTP статусом OK и рестораном в теле ответа, если ресторан найден,
     * либо HTTP статусом NOT_FOUND, если ресторан не найден.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable int id) {
        Optional<Restaurant> restaurant = restaurantService.findById(id);
        if (restaurant.isPresent()) {
            return ResponseEntity.ok(restaurant.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Обработчик POST запроса для создания нового ресторана.
     *
     * @param restaurant новый ресторан, переданный в теле запроса.
     * @return ResponseEntity с HTTP статусом CREATED и созданным рестораном в теле ответа.
     */
    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody Restaurant restaurant) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(restaurantService.save(restaurant));
    }

    /**
     * Обработчик PUT запроса для обновления существующего ресторана.
     *
     * @param id         идентификатор ресторана, который нужно обновить.
     * @param restaurant новая информация о ресторане, переданная в теле запроса.
     * @return ResponseEntity с HTTP статусом OK и обновленным рестораном в теле ответа,
     * либо HTTP статусом NOT_FOUND, если ресторан с указанным идентификатором не найден.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable int id,
                                                       @Valid @RequestBody Restaurant restaurant) {
        Optional<Restaurant> updatedRestaurant = restaurantService.update(id, restaurant);
        if (updatedRestaurant.isPresent()) {
            return ResponseEntity.ok(updatedRestaurant.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Обработчик DELETE запроса для удаления ресторана по его идентификатору.
     *
     * @param id идентификатор ресторана, который нужно удалить.
     * @return ResponseEntity с HTTP статусом NO_CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable int id) {
        restaurantService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
