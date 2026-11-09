package com.devastian.reservaexpress.dto;

import java.time.LocalTime;

import com.devastian.reservaexpress.enums.RestaurantCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class RestaurantRequest {
	
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    
    @NotBlank(message = "La descripción es obligatoria")
    private String description;
    
    @NotBlank(message = "La dirección es obligatoria")
    private String address;
    
    private LocalTime openingTime;
    private LocalTime closingTime;
    
    @Min(value = 1, message = "La capacidad debe ser mayor a 0")
    private int capacity;
    
    private String logoUrl;
    private RestaurantCategory category;
}
