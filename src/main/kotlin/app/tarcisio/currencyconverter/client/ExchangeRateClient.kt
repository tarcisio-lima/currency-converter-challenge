package app.tarcisio.currencyconverter.client

import app.tarcisio.currencyconverter.common.TransactionalContext
import app.tarcisio.currencyconverter.dto.application.ExchangeRequest
import app.tarcisio.currencyconverter.dto.web.ExchangeRateApiResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestClient.Builder
import org.springframework.web.client.RestClientResponseException
import org.zalando.logbook.spring.LogbookClientHttpRequestInterceptor

@Component
class ExchangeRateClient(
    interceptor: LogbookClientHttpRequestInterceptor,
    builder: Builder,
    @Value("\${exchange-rate-api.base-url}") baseUrl: String
){
    private val restClient: RestClient = builder.baseUrl(baseUrl).requestInterceptor(interceptor).build()

    fun getExchange(request: TransactionalContext<ExchangeRequest>): ExchangeRateApiResponse? {

        return restClient.get().uri { builder ->
                builder.path("/exchangerates_data/convert")
                    .queryParam("from", request.get().currencyFrom)
                    .queryParam("to", request.get().currencyTo)
                    .queryParam("amount", request.get().amount)
                    .build()
            }
            .header("apiKey", request.getHeader("apiKey"))
            .accept(MediaType.APPLICATION_JSON)
            .acceptCharset(Charsets.UTF_8)
            .retrieve()
            .onStatus({ it.isError}, {_, response ->
                throw RestClientResponseException(
                    "An error occurred while attempting to retrieve data",
                    response.statusCode.value(),
                    response.statusText,
                    response.headers,
                    response.body.readAllBytes(),
                    null
                )}
            )
            .body(ExchangeRateApiResponse::class.java)
    }
}