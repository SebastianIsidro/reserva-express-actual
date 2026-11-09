package com.devastian.reservaexpress.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.devastian.reservaexpress.exceptions.NotFoundException;
import com.devastian.reservaexpress.dto.Response;
import com.devastian.reservaexpress.dto.UserRequest;
import com.devastian.reservaexpress.dto.UserResponse;
import com.devastian.reservaexpress.entity.User;
import com.devastian.reservaexpress.repository.UserRepository;
import com.devastian.reservaexpress.service.AuthService;
import com.devastian.reservaexpress.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
	
	private final AuthService authService;
	private final UserRepository userRepository;
	
	private UserResponse mapToResponse(User user) {
	    return UserResponse.builder()
	        .id(user.getId())
	        .firstName(user.getFirstName())
	        .lastName(user.getLastName())
	        .phoneNumber(user.getPhoneNumber())
	        .birthDate(user.getBirthDate())
	        .gender(user.getGender())
	        .email(user.getEmail())
	        .role(user.getRole())
	        .createdAt(user.getCreatedAt())
	        .updatedAt(user.getUpdatedAt())
	        .build();
	}
	
	@Override
	public Response<UserResponse> getCurrentUser() {
		
		log.info("INSIDE getCurrentUser()");
		
		User currentUser = authService.getCurrentLoggedInUser();
		
		return Response.<UserResponse>builder()
				.statusCode(HttpStatus.OK.value())
				.message("Perfil recuperado exitosamente")
				.data(mapToResponse(currentUser))
				.build();
	}
	
	@Override
	public Response<UserResponse> updateCurrentUser(UserRequest userRequest) {
		
		log.info("INSIDE updateCurrentUser()");
		
		User currentUser = authService.getCurrentLoggedInUser();
		
		if (userRequest.getFirstName() != null) currentUser.setFirstName(userRequest.getFirstName());
		if (userRequest.getLastName() != null) currentUser.setLastName(userRequest.getLastName());
		if (userRequest.getPhoneNumber() != null) currentUser.setPhoneNumber(userRequest.getPhoneNumber());
		if (userRequest.getBirthDate() != null) currentUser.setBirthDate(userRequest.getBirthDate());
		if (userRequest.getGender() != null) currentUser.setGender(userRequest.getGender());
		currentUser.setUpdatedAt(LocalDateTime.now());
		
		User updatedUser = userRepository.save(currentUser);
		
		return Response.<UserResponse>builder()
				.statusCode(HttpStatus.OK.value())
				.message("Usuario actualizado exitosamente")
				.data(mapToResponse(updatedUser))
				.build();
	}
	
	@Override
	public Response<List<UserResponse>> getAllUsers() {
		
		log.info("INSIDE getAllUsers()");
		
		List<UserResponse> users = userRepository.findAll()
	            .stream()
	            .map(this::mapToResponse)
	            .toList();
		
		return Response.<List<UserResponse>>builder()
				.statusCode(HttpStatus.OK.value())
				.message("Usuarios recuperados exitosamente")
				.data(users)
				.build();
	}
	
	@Override
	public Response<UserResponse> getUserById(Long id) {
		
		log.info("INSIDE getUserById()");
		
		User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
		
		return Response.<UserResponse>builder()
				.statusCode(HttpStatus.OK.value())
				.message("Usuario recuperado exitosamente")
				.data(mapToResponse(user))
				.build();
	}
}
