package app.tarcisio.currencyconverter.dto.web

import java.math.BigDecimal

data class ExchangeRateInfo(
    val rate: BigDecimal? = null,
    val timestamp: Long? = null
)
