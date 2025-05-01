package app.tarcisio.currencyconverter.dto.application

import java.math.BigDecimal
import java.time.LocalDateTime

data class ExchangeResponse(
    val id: Long?,
    val userId: Long?,
    val sourceCurrency: String?,
    val sourceAmount: BigDecimal?,
    val targetCurrency: String?,
    val targetAmount: BigDecimal?,
    val exchangeRate: Double?,
    val registrationDate: LocalDateTime?
)

