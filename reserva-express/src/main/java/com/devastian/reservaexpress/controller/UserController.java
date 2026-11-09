package com.devastian.reservaexpress.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devastian.reservaexpress.dto.Response;
import com.devastian.reservaexpress.dto.UserRequest;
import com.devastian.reservaexpress.dto.UserResponse;
import com.devastian.reservaexpress.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/me")
	public ResponseEntity<Response<UserResponse>> getCurrentUser() {
		return ResponseEntity.ok(userService.getCurrentUser());
	}
	
	@PutMapping("/me")
	public ResponseEntity<Response<UserResponse>> updateCurrentUser(@Valid @RequestBody UserRequest userRequest) {
		return ResponseEntity.ok(userService.updateCurrentUser(userRequest));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<Response<List<UserResponse>>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<Response<UserResponse>> getUserById(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}
}
