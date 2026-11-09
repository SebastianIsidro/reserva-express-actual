package com.devastian.reservaexpress.dto;

import java.time.LocalDate;

import com.devastian.reservaexpress.enums.Gender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;

@Data
public class UserRequest {
	
	@NotBlank(message = "El nombre es obligatorio")
	private String firstName;
	
	@NotBlank(message = "El apellido es obligatorio")
	private String lastName;
	
	private String phoneNumber;
	
	@Past(message = "La fecha de nacimiento debe estar en pasado")
	private LocalDate birthDate;
	
	private Gender gender;
	
}
