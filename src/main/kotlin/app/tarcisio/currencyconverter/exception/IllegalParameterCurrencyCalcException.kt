package app.tarcisio.currencyconverter.exception

import app.tarcisio.currencyconverter.utils.MessagesProvider

class IllegalParameterCurrencyCalcException: RuntimeException(
    MessagesProvider.getMessage("error.message.currency-calc-exception")
)