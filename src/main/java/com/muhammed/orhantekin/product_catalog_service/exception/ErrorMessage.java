package com.muhammed.orhantekin.product_catalog_service.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// Hata mesajları, bir hata türü (`MessageType`) ve opsiyonel bir açıklama (`ofStatic`) içerebilir.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

    private MessageType messageType;

    private String ofStatic;

    public String prepareErrorMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append(messageType.getMessage());
        if(ofStatic!=null) {
            builder.append(" : " + ofStatic);
        }
        return builder.toString();
    }
}
