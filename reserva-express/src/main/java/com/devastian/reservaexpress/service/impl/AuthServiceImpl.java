package com.devastian.reservaexpress.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devastian.reservaexpress.dto.Response;
import com.devastian.reservaexpress.dto.LoginRequest;
import com.devastian.reservaexpress.dto.RegisterRequest;
import com.devastian.reservaexpress.entity.User;
import com.devastian.reservaexpress.enums.Role;
import com.devastian.reservaexpress.exceptions.NotFoundException;
import com.devastian.reservaexpress.exceptions.BadRequestException;
import com.devastian.reservaexpress.repository.UserRepository;
import com.devastian.reservaexpress.security.JwtUtils;
import com.devastian.reservaexpress.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;
	
	@Override
	public Response<?> register(RegisterRequest registerRequest) {
		
		log.info("INSIDE register()");
		
		Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());
		
		if (existingUser.isPresent()) {
			throw new BadRequestException("El correo electrónico ya está en uso");
		}

		User user = User.builder()
		        .createdAt(LocalDateTime.now())
		        .updatedAt(LocalDateTime.now())
		        .role(Role.ROLE_CUSTOMER)
		        .firstName(registerRequest.getFirstName())
		        .lastName(registerRequest.getLastName())
		        .email(registerRequest.getEmail())
		        .password(passwordEncoder.encode(registerRequest.getPassword()))
		        .build();
		
		userRepository.save(user);
		
		return Response.builder()
				.statusCode(HttpStatus.OK.value())
				.message("Usuario registrado correctamente")
				.build();
	}

	@Override
	public Response<?> login(LoginRequest loginRequest) {
		
		log.info("INSIDE login()");
		
		User user = userRepository.findByEmail(loginRequest.getEmail())
				.orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
		
		if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			throw new BadRequestException("Contraseña inválida");
		}
		
		String token = jwtUtils.generateToken(user.getEmail());
		
		return Response.builder()
				.statusCode(HttpStatus.OK.value())
				.message("Inicio de sesión exitoso")
				.data(token)
				.build();
	}

	@Override
	public User getCurrentLoggedInUser() {
		
		log.info("INSIDE getCurrentLoggedInUser()");
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
	}
	
}
