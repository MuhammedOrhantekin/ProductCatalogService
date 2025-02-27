package com.muhammed.orhantekin.product_catalog_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "📦 Ürün Kataloğu Servisi API",
                version = "1.0",
                description = """
            Bu API, ürünlerin **eklenmesi, listelenmesi, güncellenmesi, silinmesi ve kategoriye göre filtrelenmesini** sağlar.
            
            ##  Özellikler:
            - **Ürün Yönetimi:** CRUD işlemleri (Ekle, Güncelle, Sil, Listele)
            - **Filtreleme:** Ürünleri kategoriye göre listeleme
            - **Doğrulama:** Giriş verilerini kontrol eden mekanizmalar
            - **Swagger UI Desteği:** API endpoint'lerini test edebilirsiniz
           
        """,
                contact = @Contact(
                        name = "Muhammed Orhantekin",
                        url = "https://github.com/MuhammedOrhantekin"
                )
        )

)
public class OpenApiConfig {
}
