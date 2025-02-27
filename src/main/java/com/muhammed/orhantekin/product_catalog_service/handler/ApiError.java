package com.muhammed.orhantekin.product_catalog_service.handler;

import lombok.Getter;
import lombok.Setter;


//  Hata durum kodunu ve hata detaylarını içeren Exceptionn nesnesini barındırır.
@Getter
@Setter
public class ApiError<E> {

    private Integer status;

    private Exceptionn<E> exceptionn;
}
