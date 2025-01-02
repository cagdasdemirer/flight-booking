package dev.teamso.flightbooking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Flight Booking API")
                        .version("1.0")
                        .description("API for managing flight bookings")
                        .termsOfService("http://swagger.io/terms/")
                        .contact(new Contact()
                                .name("Çağdaş Demirer")
                                .url("http://cagdasdemirer.com")
                                .email("mcdemirer@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}