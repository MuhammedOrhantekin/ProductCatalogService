package com.muhammed.orhantekin.product_catalog_service.exception;

import lombok.Getter;

// Uygulamada kullanılan hata mesajlarını yönetmek için kullanılan bir enum sınıfıdır.
@Getter
public enum MessageType {

    NO_RECORD_EXIST("1001","kayıt bulunamadı"),
    INVALID_INPUT("1002","Geçersiz Giriş" );

    private String code;
    private String message;

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
