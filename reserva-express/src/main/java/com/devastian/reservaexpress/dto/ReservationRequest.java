package com.devastian.reservaexpress.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationRequest {
	
	@FutureOrPresent(message = "La fecha debe estar en futuro o en presente")
	private LocalDate reservationDate;
	
	@NotNull(message = "La hora no debe estar vacía")
	private LocalTime reservationTime;
	
	@Min(value = 1, message = "El número de invitados debe ser mayor a 0")
	private int numberOfGuests;
	
	@NotNull(message = "El ID del restaurante es obligatorio")
    private Long restaurantId;
}
