package app.tarcisio.currencyconverter.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.zalando.logbook.HeaderFilter
import org.zalando.logbook.HttpRequest
import org.zalando.logbook.Logbook
import org.zalando.logbook.core.*
import java.util.function.Predicate

/**
 * Utiliza a biblioteca Logbook como apoio para logging dentro da aplicação.
 */
@Configuration
class LogbookConfig {

    @Bean
    fun logbook(): Logbook {
        return Logbook.builder()
            .strategy(DefaultStrategy())
            .sink(
                DefaultSink(
                    LogbookFormatter(),
                    DefaultHttpLogWriter()
                )
            )
            .headerFilter(maskApiKey())
            .headerFilter(excludeUnnecessaryHeaders())
            .condition(conditions())
            .build()
    }

    /**
     * Obfusca dados sensíveis que possam transitar nos logs
     */
    private fun maskApiKey(): HeaderFilter = HeaderFilters.replaceHeaders("apiKey", "*****")

    private fun excludeUnnecessaryHeaders(): HeaderFilter {
        return HeaderFilters.removeHeaders { headerKey: String? ->
            val headers: List<String> = mutableListOf(
                "accept",
                "content-length",
                "content-type"
            )
            headers.stream().noneMatch { listKey: String ->
                listKey.equals(
                    headerKey,
                    ignoreCase = true
                )
            }
        }
    }

    /**
     * Elimina listeners de paths padrões do framework nos logs
     */
    fun conditions(): Predicate<HttpRequest> {
        return Conditions.exclude(
            Conditions.requestTo("/health/**"),
            Conditions.requestTo("/admin/**"),
            Conditions.requestTo("/info"),
            Conditions.requestTo("/swagger-ui/**"),
            Conditions.requestTo("/webjars/**"),
            Conditions.requestTo("/swagger-resources/**"),
            Conditions.requestTo("/v3/api-docs/**"),
            Conditions.requestTo("/actuator/**")
        )
    }
}