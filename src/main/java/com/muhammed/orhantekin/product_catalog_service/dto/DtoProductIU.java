package com.muhammed.orhantekin.product_catalog_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Insert - Update İşlemlerinde kullanılan DTO")
public class DtoProductIU {


    @NotNull(message = "Null OLamaz")
    @NotBlank(message = "Product name is no empty!")
    @Size(min = 2,max = 100,message = "Product name must be between 2 and 100 characters")
    private String name;


    @Positive(message = "Price must be a positive number")
    @DecimalMin(value = "0.1" ,message = "Price must be at least 0.1")
    private Double price;


    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    private Set<@Valid DtoCategory> categories = new HashSet<>();
}
