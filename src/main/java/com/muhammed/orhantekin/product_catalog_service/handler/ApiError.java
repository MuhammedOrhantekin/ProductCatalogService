package com.muhammed.orhantekin.product_catalog_service.handler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError<E> {

    private Integer status;

    private Exceptionn<E> exceptionn;
}
