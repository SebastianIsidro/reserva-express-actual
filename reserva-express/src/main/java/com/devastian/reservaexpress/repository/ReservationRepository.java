package com.devastian.reservaexpress.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devastian.reservaexpress.entity.Reservation;
import com.devastian.reservaexpress.entity.User;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	List<Reservation> findByUser(User user, Sort sort);
	List<Reservation> findByRestaurantOwner(User owner, Sort sort);
	List<Reservation> findByRestaurantIdAndRestaurantOwner(Long id, User owner, Sort sort);
	
}
