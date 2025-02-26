package com.muhammed.orhantekin.product_catalog_service.handler;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Exceptionn<E> {

    private String path;

    private Date createTime;

    private E message;
}
