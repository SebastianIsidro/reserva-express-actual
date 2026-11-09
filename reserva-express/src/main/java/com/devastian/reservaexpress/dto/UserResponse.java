package com.devastian.reservaexpress.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.devastian.reservaexpress.enums.Gender;
import com.devastian.reservaexpress.enums.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthDate;
    
    private Gender gender;
    
    private String email;
    
    private Role role;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
