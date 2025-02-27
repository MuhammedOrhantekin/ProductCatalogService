package com.muhammed.orhantekin.product_catalog_service.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


// API hata mesajlarının detaylarını içeren bir sınıftır.
@Getter
@Setter
public class Exceptionn<E> {

    private String path;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Istanbul")
    private Date createTime;

    private E message;
}
