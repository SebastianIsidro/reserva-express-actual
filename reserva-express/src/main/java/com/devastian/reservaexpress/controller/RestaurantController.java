package com.devastian.reservaexpress.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devastian.reservaexpress.dto.Response;
import com.devastian.reservaexpress.dto.RestaurantRequest;
import com.devastian.reservaexpress.entity.Restaurant;
import com.devastian.reservaexpress.service.RestaurantService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {
	
	private final RestaurantService restaurantService;
	
	@GetMapping
	public ResponseEntity<Response<List<Restaurant>>> getAllRestaurants() {
		return ResponseEntity.ok(restaurantService.getAllRestaurants());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response<Restaurant>> getRestaurantById(@PathVariable Long id) {
		return ResponseEntity.ok(restaurantService.getRestaurantById(id));
	}
	
	@PreAuthorize("hasRole('PARTNER')")
	@GetMapping("/my")
	public ResponseEntity<Response<List<Restaurant>>> getAllMyRestaurants() {
		return ResponseEntity.ok(restaurantService.getAllMyRestaurants());
	}

	@PreAuthorize("hasRole('PARTNER')")
	@PostMapping
	public ResponseEntity<Response<?>> createRestaurant(@Valid @RequestBody RestaurantRequest restaurantRequest) {
	    return ResponseEntity.ok(restaurantService.createRestaurant(restaurantRequest));
	}

	@PreAuthorize("hasRole('PARTNER')")
	@PutMapping("/{id}")
	public ResponseEntity<Response<?>> updateRestaurant(@PathVariable Long id, @Valid @RequestBody RestaurantRequest restaurantRequest) {
	    return ResponseEntity.ok(restaurantService.updateRestaurant(id, restaurantRequest));
	}

	
}
