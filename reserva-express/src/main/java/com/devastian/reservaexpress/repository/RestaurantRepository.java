package com.devastian.reservaexpress.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devastian.reservaexpress.entity.Restaurant;
import com.devastian.reservaexpress.entity.User;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

	List<Restaurant> findByOwner(User owner, Sort sort);
	
}
