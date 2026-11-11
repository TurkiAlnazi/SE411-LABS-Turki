package edu.spu.se411.lab14.model;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record Course(
		@Id @PositiveOrZero Long id,                        
		@NotBlank @Size(max = 20) String code,                
		@NotNull @Size(min = 2, max = 100) String name,       
		@Size(max = 500) String description,
		@NotNull @Min(1) @Max(10) Integer credits             
) {}