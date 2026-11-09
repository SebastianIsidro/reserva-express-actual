package com.devastian.reservaexpress.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.devastian.reservaexpress.dto.ReservationRequest;
import com.devastian.reservaexpress.dto.Response;
import com.devastian.reservaexpress.entity.Reservation;
import com.devastian.reservaexpress.entity.Restaurant;
import com.devastian.reservaexpress.entity.User;
import com.devastian.reservaexpress.enums.ReservationStatus;
import com.devastian.reservaexpress.exceptions.NotFoundException;
import com.devastian.reservaexpress.exceptions.BadRequestException;
import com.devastian.reservaexpress.repository.ReservationRepository;
import com.devastian.reservaexpress.repository.RestaurantRepository;
import com.devastian.reservaexpress.service.AuthService;
import com.devastian.reservaexpress.service.ReservationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {
	
	private final AuthService authService;
	private final ReservationRepository reservationRepository;
	private final RestaurantRepository restaurantRepository;
	
	@Override
	public Response<List<Reservation>> getAllMyReservations() {
		
		log.info("INSIDE getAllMyReservations()");
		
		User currentUser = authService.getCurrentLoggedInUser();
		
		List<Reservation> reservations = reservationRepository.findByUser(currentUser, Sort.by(Sort.Direction.DESC, "id"));
		
		return Response.<List<Reservation>>builder()
				.statusCode(HttpStatus.OK.value())
				.message("Reservas recuperadas exitosamente")
				.data(reservations)
				.build();
	}
	
	@Override
	public Response<List<Reservation>> getRestaurantReservations(Long id) {
		
		log.info("INSIDE getRestaurantReservations()");
		
		User currentUser = authService.getCurrentLoggedInUser();
		
		List<Reservation> reservations = reservationRepository.findByRestaurantIdAndRestaurantOwner(id, currentUser, Sort.by(Sort.Direction.DESC, "id"));
		
		return Response.<List<Reservation>>builder()
				.statusCode(HttpStatus.OK.value())
				.message("Reservas recuperadas exitosamente")
				.data(reservations)
				.build();
	}
	
	@Override
	public Response<Reservation> createReservation(ReservationRequest reservationRequest) {
		
		log.info("INSIDE createReservation()");
		
		User currentUser = authService.getCurrentLoggedInUser();
		
		Restaurant restaurant = restaurantRepository.findById(reservationRequest.getRestaurantId()).orElseThrow(() -> new NotFoundException("Restaurante no encontrado"));
		
		Reservation reservationToSave = Reservation.builder()
				.reservationDate(reservationRequest.getReservationDate())
				.reservationTime(reservationRequest.getReservationTime())
				.numberOfGuests(reservationRequest.getNumberOfGuests())
				.status(ReservationStatus.PENDING)
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.user(currentUser)
				.restaurant(restaurant)
				.build();
		
		Reservation savedReservation = reservationRepository.save(reservationToSave);
		
		return Response.<Reservation>builder()
				.statusCode(HttpStatus.OK.value())
				.message("Reserva creada exitosamente")
				.data(savedReservation)
				.build();
	}
	
	public Response<Reservation> cancelReservation(Long id) {
		
		log.info("INSIDE cancelReservation()");
		
	    User currentUser = authService.getCurrentLoggedInUser();
	    
	    Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new NotFoundException("Reserva no encontrada"));

	    if (!reservation.getUser().equals(currentUser)) {
	        throw new BadRequestException("No puedes cancelar una reserva que no es tuya");
	    }
	    if (reservation.getStatus() != ReservationStatus.PENDING) {
	        throw new BadRequestException("Solo se pueden cancelar reservas en estado PENDING");
	    }
	    
	    reservation.setStatus(ReservationStatus.CANCELLED);
	    reservation.setUpdatedAt(LocalDateTime.now());
	    reservationRepository.save(reservation);
	    
	    return Response.<Reservation>builder()
	            .statusCode(HttpStatus.OK.value())
	            .message("Reserva cancelada exitosamente")
	            .data(reservation)
	            .build();
	}
	
	public Response<Reservation> acceptReservation(Long id) {
	    
		log.info("INSIDE acceptReservation()");
		
	    Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new NotFoundException("Reserva no encontrada"));

	    if (reservation.getStatus() != ReservationStatus.PENDING) {
	        throw new BadRequestException("Solo se pueden aceptar reservas en estado PENDING");
	    }
	    
	    reservation.setStatus(ReservationStatus.ACCEPTED);
	    reservation.setUpdatedAt(LocalDateTime.now());
	    reservationRepository.save(reservation);
	    
	    return Response.<Reservation>builder()
	            .statusCode(HttpStatus.OK.value())
	            .message("Reserva aceptada exitosamente")
	            .data(reservation)
	            .build();
	}
	
	public Response<Reservation> declineReservation(Long id) {
	    
		log.info("INSIDE declineReservation()");
		
	    Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new NotFoundException("Reserva no encontrada"));

	    if (reservation.getStatus() != ReservationStatus.PENDING) {
	        throw new BadRequestException("Solo se pueden rechazar reservas en estado PENDING");
	    }
	    
	    reservation.setStatus(ReservationStatus.DECLINED);
	    reservation.setUpdatedAt(LocalDateTime.now());
	    reservationRepository.save(reservation);
	    
	    return Response.<Reservation>builder()
	            .statusCode(HttpStatus.OK.value())
	            .message("Reserva rechazada exitosamente")
	            .data(reservation)
	            .build();
	}
}
