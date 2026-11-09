package com.devastian.reservaexpress.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
	
	@NotBlank(message = "El nombre es obligatorio")
	private String firstName;
	
	@NotBlank(message = "El apellido es obligatorio")
	private String lastName;
	
	@NotBlank(message = "El correo electrónico es obligatorio")
	@Email
	private String email;
	
	@NotBlank(message = "La contraseña es obligatoria")
	@Size(min = 8, max = 64)
	private String password;
}
