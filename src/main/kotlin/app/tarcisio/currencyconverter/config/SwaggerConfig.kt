package app.tarcisio.currencyconverter.config

import app.tarcisio.currencyconverter.utils.MessagesProvider
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI().info(
            Info()
                .title(
                    MessagesProvider.getMessage("label.swagger-api-title")
                )
                .description(
                    MessagesProvider.getMessage("label.swagger-api-description")
                )
        )
    }

}