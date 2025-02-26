package com.muhammed.orhantekin.product_catalog_service.exception;

import lombok.Getter;

@Getter
public enum MessageType {

    NO_RECORD_EXIST("1001","kayıt bulunamadı"),
    GENERAL_EXCEPTION("9999" , "genel bir hata oluştu"),
    INVALID_INPUT("1002","Geçersiz Giriş" );

    private String code;
    private String message;



    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
