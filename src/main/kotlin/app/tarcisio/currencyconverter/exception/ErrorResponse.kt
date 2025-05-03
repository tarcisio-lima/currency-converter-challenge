package app.tarcisio.currencyconverter.exception

import java.time.LocalDateTime

data class ErrorResponse(
    val status: Int,
    val error: String,
    val message: String,
    val timeStamp: LocalDateTime,
    val trace: String? = null
)