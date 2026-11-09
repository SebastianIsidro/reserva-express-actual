package com.devastian.reservaexpress.service;

import java.util.List;

import com.devastian.reservaexpress.dto.ReservationRequest;
import com.devastian.reservaexpress.dto.Response;
import com.devastian.reservaexpress.entity.Reservation;

public interface ReservationService {
	
	Response<List<Reservation>> getAllMyReservations();
	Response<List<Reservation>> getRestaurantReservations(Long id);
	Response<Reservation> createReservation(ReservationRequest reservationRequest);
	Response<Reservation> cancelReservation(Long id);
	Response<Reservation> acceptReservation(Long id);
	Response<Reservation> declineReservation(Long id);
	
	
}
