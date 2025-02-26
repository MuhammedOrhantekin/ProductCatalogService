package com.muhammed.orhantekin.product_catalog_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Kullanıcıya değer dönerken DTO")
public class DtoProduct {

    private Long id;

    private Double price;

    private String name;

    private String description;

    private Set<DtoCategory> categories = new HashSet<>();
}
