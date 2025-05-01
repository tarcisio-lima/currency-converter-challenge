package app.tarcisio.currencyconverter.utils

import app.tarcisio.currencyconverter.exception.IllegalParameterCurrencyCalcException
import java.math.BigDecimal

object CurrencyUtils {

    /**
     * Calcula o valor final do câmbio da moeda
     * @param amount Valor de origem
     * @param exchangeRate Taxa de conversão
     */
    fun calculateCurrencyValue(amount: BigDecimal?, exchangeRate: Double?): BigDecimal {
        return if (amount != null && exchangeRate != null)
            amount.times(BigDecimal(exchangeRate))
        else
            throw IllegalParameterCurrencyCalcException()
    }
    
}