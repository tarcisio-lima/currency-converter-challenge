package app.tarcisio.currencyconverter.utils

import app.tarcisio.currencyconverter.exception.IllegalParameterCurrencyCalcException
import java.math.BigDecimal
import java.math.RoundingMode

object CurrencyUtils {

    /**
     * Calcula o valor final do câmbio da moeda
     * @param amount Valor de origem
     * @param exchangeRate Taxa de conversão
     */
    fun calculateCurrencyValue(amount: BigDecimal?, exchangeRate: BigDecimal?): BigDecimal {
        return if (amount != null && exchangeRate != null)
            amount.times(exchangeRate).setScale(2, RoundingMode.HALF_EVEN)
        else
            throw IllegalParameterCurrencyCalcException()
    }
    
}