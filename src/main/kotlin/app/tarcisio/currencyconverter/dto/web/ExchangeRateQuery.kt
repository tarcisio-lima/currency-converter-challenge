package app.tarcisio.currencyconverter.dto.web

import java.math.BigDecimal

data class ExchangeRateQuery(
    val amount: BigDecimal? = null,
    val from: String? = null,
    val to: String? = null
)
