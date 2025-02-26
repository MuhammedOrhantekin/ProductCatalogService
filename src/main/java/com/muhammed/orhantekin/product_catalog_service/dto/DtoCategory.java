package com.muhammed.orhantekin.product_catalog_service.dto;

import com.muhammed.orhantekin.product_catalog_service.model.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Category DTO")
public class DtoCategory {

    @Size(min = 2, max = 50, message = "Category name must be between 2 and 50 characters")
    private String name;
}
