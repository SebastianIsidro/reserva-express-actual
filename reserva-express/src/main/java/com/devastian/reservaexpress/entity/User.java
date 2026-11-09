package com.devastian.reservaexpress.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.devastian.reservaexpress.enums.Gender;
import com.devastian.reservaexpress.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private LocalDate birthDate;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Column(unique = true, nullable = false)
	private String email;
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Reservation> reservations = new ArrayList<>();
	
	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Restaurant> restaurants = new ArrayList<>();
	
	// NECESARIOS - Pueden ser agregados a futuro
	/*
	private String documentNumber;

	@Enumerated(EnumType.STRING)
	private DocumentType documentType;

	@Enumerated(EnumType.STRING)
	private UserStatus status;

	private LocalDateTime lastLogin;
	*/

}
