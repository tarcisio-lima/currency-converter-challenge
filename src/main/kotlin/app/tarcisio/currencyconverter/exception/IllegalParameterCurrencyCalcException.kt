package app.tarcisio.currencyconverter.exception

import app.tarcisio.currencyconverter.utils.MessagesProvider
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Bad Request")
class IllegalParameterCurrencyCalcException: RuntimeException(
    MessagesProvider.getMessage("error.message.currency-calc-exception")
)