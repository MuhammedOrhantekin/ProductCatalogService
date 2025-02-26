package com.muhammed.orhantekin.product_catalog_service.exception;

public class BaseException extends RuntimeException{

    public BaseException() {

    }

    public BaseException(ErrorMessage errorMessage) {
        super(errorMessage.prepareErrorMessage());
    }
}
