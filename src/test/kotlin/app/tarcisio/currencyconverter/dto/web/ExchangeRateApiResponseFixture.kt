package app.tarcisio.currencyconverter.dto.web

import java.math.BigDecimal
import java.time.LocalDate

object ExchangeRateApiResponseFixture {

    fun example(): ExchangeRateApiResponse {
        return ExchangeRateApiResponse(
            date = LocalDate.now(),
            info = ExchangeRateInfo(
                rate = BigDecimal(1.0),
                timestamp = 1L
            ),
            ExchangeRateQuery(
                amount = BigDecimal(500.0),
                from = "USD",
                to = "BRL",
            ),
            result = BigDecimal(1.0),
            success = true
        )
    }

    fun mapperExample(): ExchangeRateApiResponse {
        return ExchangeRateApiResponse(
            query = ExchangeRateQuery(from = "USD", to = "BRL", amount = BigDecimal("10.00")),
            info = ExchangeRateInfo(rate = BigDecimal("5.50")),
            date = LocalDate.of(2020, 1, 1),
            result = BigDecimal("55.00")
        )
    }

}