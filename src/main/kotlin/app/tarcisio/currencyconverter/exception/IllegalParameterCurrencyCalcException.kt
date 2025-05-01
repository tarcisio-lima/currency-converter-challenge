package app.tarcisio.currencyconverter.exception

import app.tarcisio.currencyconverter.utils.MessagesProvider
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

class IllegalParameterCurrencyCalcException: RuntimeException(
    MessagesProvider.getMessage("error.message.currency-calc-exception")
)