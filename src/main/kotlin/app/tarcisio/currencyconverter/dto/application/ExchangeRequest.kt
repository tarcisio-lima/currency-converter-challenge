package app.tarcisio.currencyconverter.dto.application

import java.math.BigDecimal

data class ExchangeRequest(
    val userId: Long,
    val currencyFrom: String?,
    val currencyTo: String?,
    val amount: BigDecimal?
)
