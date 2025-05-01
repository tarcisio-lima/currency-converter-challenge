package app.tarcisio.currencyconverter.dto.web

import java.math.BigDecimal
import java.time.LocalDate

data class ExchangeRateApiResponse(
    val date: LocalDate? = null,
    val info: ExchangeRateInfo? = null,
    val query: ExchangeRateQuery? = null,
    val result: BigDecimal? = null,
    val success: Boolean? = null
)
