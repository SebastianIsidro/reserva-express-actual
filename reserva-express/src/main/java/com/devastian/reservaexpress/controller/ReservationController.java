package com.devastian.reservaexpress.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devastian.reservaexpress.dto.ReservationRequest;
import com.devastian.reservaexpress.dto.Response;
import com.devastian.reservaexpress.entity.Reservation;
import com.devastian.reservaexpress.service.ReservationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {
	
	private final ReservationService reservationService;
	
	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("/my")
	public ResponseEntity<Response<List<Reservation>>> getAllMyReservations() {
		return ResponseEntity.ok(reservationService.getAllMyReservations());
	}
	
	@PreAuthorize("hasRole('PARTNER')")
	@GetMapping("/restaurant/{id}")
	public ResponseEntity<Response<List<Reservation>>> getRestaurantReservations(@PathVariable Long id) {
		return ResponseEntity.ok(reservationService.getRestaurantReservations(id));
	}
	
	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping
	public ResponseEntity<Response<?>> createReservation(@Valid @RequestBody ReservationRequest reservationRequest) {
		return ResponseEntity.ok(reservationService.createReservation(reservationRequest));
	}

	@PreAuthorize("hasRole('CUSTOMER')")
	@PatchMapping("/{id}/cancel")
	public ResponseEntity<Response<?>> cancelReservation(@PathVariable Long id) {
	    return ResponseEntity.ok(reservationService.cancelReservation(id));
	}

	@PreAuthorize("hasRole('PARTNER')")
	@PatchMapping("/{id}/accept")
	public ResponseEntity<Response<?>> acceptReservation(@PathVariable Long id) {
	    return ResponseEntity.ok(reservationService.acceptReservation(id));
	}

	@PreAuthorize("hasRole('PARTNER')")
	@PatchMapping("/{id}/decline")
	public ResponseEntity<Response<?>> declineReservation(@PathVariable Long id) {
	    return ResponseEntity.ok(reservationService.declineReservation(id));
	}

	
}

/*
	@GetMapping
	public ResponseEntity<Map<String, Object>> listBookings() {
		return service.listBookings();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getBookingById(@PathVariable int id) {
		return service.getBookingById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> updateBooking(@RequestBody Booking booking, @PathVariable int id) {
		return service.updateBooking(booking, id);
	}
	
	@PutMapping("/cancel/{id}")
	public ResponseEntity<Map<String, Object>> cancelBooking(@PathVariable int id) {
		return service.cancelBooking(id);
	}
	
	@PutMapping("/decline/{id}")
	public ResponseEntity<Map<String, Object>> declineBooking(@PathVariable int id) {
		return service.declineBooking(id);
	}
	@PutMapping("/accept/{id}")
	public ResponseEntity<Map<String, Object>> acceptBooking(@PathVariable int id) {
		return service.acceptBooking(id);
	}
	
	@GetMapping("/owner/{id}")
	public ResponseEntity<Map<String, Object>> listBookingsByOwner(@PathVariable int id) {
	    return service.listBookingsByOwner(id);
	}
*/
