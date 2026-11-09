package com.devastian.reservaexpress.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.devastian.reservaexpress.dto.Response;
import com.devastian.reservaexpress.dto.RestaurantRequest;
import com.devastian.reservaexpress.entity.Restaurant;
import com.devastian.reservaexpress.entity.User;
import com.devastian.reservaexpress.exceptions.BadRequestException;
import com.devastian.reservaexpress.exceptions.NotFoundException;
import com.devastian.reservaexpress.repository.RestaurantRepository;
import com.devastian.reservaexpress.service.AuthService;
import com.devastian.reservaexpress.service.RestaurantService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {
	
	private final AuthService authService;
	private final RestaurantRepository restaurantRepository;
	
	@Override
	public Response<List<Restaurant>> getAllRestaurants() {
		
		log.info("INSIDE getAllRestaurants()");
		
		List<Restaurant> restaurants = restaurantRepository.findAll();
		return Response.<List<Restaurant>>builder()
				.statusCode(HttpStatus.OK.value())
				.message("Restaurantes recuperados exitosamente")
				.data(restaurants)
				.build();
	}
	
	@Override
	public Response<Restaurant> getRestaurantById(Long id) {
		
		log.info("INSIDE getRestaurantById()");
		
		Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new NotFoundException("Restaurante no encontrado"));
		
		return Response.<Restaurant>builder()
				.statusCode(HttpStatus.OK.value())
				.message("Restaurante recuperado exitosamente")
				.data(restaurant)
				.build();
	}
	
	@Override
	public Response<List<Restaurant>> getAllMyRestaurants() {
		
		log.info("INSIDE getAllMyRestaurants()");
		
		User currentUser = authService.getCurrentLoggedInUser();
		
		List<Restaurant> restaurants = restaurantRepository.findByOwner(currentUser, Sort.by(Sort.Direction.DESC, "id"));
		return Response.<List<Restaurant>>builder()
				.statusCode(HttpStatus.OK.value())
				.message("Restaurantes recuperadas exitosamente")
				.data(restaurants)
				.build();
	}
	
	@Override
	public Response<Restaurant> createRestaurant(RestaurantRequest restaurantRequest) {
		
		log.info("INSIDE createRestaurant()");
		
		User currentUser = authService.getCurrentLoggedInUser();
		
		Restaurant restaurantToSave = Restaurant.builder()
				.name(restaurantRequest.getName())
				.description(restaurantRequest.getDescription())
				.address(restaurantRequest.getAddress())
				.openingTime(restaurantRequest.getOpeningTime())
				.closingTime(restaurantRequest.getClosingTime())
				.capacity(restaurantRequest.getCapacity())
				.logoUrl(restaurantRequest.getLogoUrl())
				.category(restaurantRequest.getCategory())
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.owner(currentUser)
				.build();
		
		Restaurant savedRestaurant = restaurantRepository.save(restaurantToSave);
		
		return Response.<Restaurant>builder()
				.statusCode(HttpStatus.OK.value())
				.message("Restaurante creado exitosamente")
				.data(savedRestaurant)
				.build();
	}
	
	@Override
	public Response<Restaurant> updateRestaurant(Long id, RestaurantRequest restaurantRequest) {
		
		log.info("INSIDE updateRestaurant()");
		
		User currentUser = authService.getCurrentLoggedInUser();
		
	    Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new NotFoundException("Restaurante no encontrado"));

	    if (!restaurant.getOwner().equals(currentUser)) {
	        throw new BadRequestException("No puedes modificar un restaurante que no es tuyo");
	    }
		
		if (restaurantRequest.getName() != null) restaurant.setName(restaurantRequest.getName());
		if (restaurantRequest.getDescription() != null) restaurant.setDescription(restaurantRequest.getDescription());
		if (restaurantRequest.getAddress() != null) restaurant.setAddress(restaurantRequest.getAddress());
		if (restaurantRequest.getOpeningTime() != null) restaurant.setOpeningTime(restaurantRequest.getOpeningTime());
		if (restaurantRequest.getClosingTime() != null) restaurant.setClosingTime(restaurantRequest.getClosingTime());
		if (restaurantRequest.getCapacity() != 0) restaurant.setCapacity(restaurantRequest.getCapacity());
		if (restaurantRequest.getLogoUrl() != null) restaurant.setLogoUrl(restaurantRequest.getLogoUrl());
		if (restaurantRequest.getCategory() != null) restaurant.setCategory(restaurantRequest.getCategory());
		restaurant.setUpdatedAt(LocalDateTime.now());
		
		Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
		
		return Response.<Restaurant>builder()
				.statusCode(HttpStatus.OK.value())
				.message("Restaurante actualizado exitosamente")
				.data(updatedRestaurant)
				.build();
	}
}
