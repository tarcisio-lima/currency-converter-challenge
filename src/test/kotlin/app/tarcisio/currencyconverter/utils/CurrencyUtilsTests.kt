package app.tarcisio.currencyconverter.utils

import app.tarcisio.currencyconverter.exception.IllegalParameterCurrencyCalcException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class CurrencyUtilsTests {

    @Test
    internal fun `calculateCurrencyValue should return the correct calculated value`() {

        val amount = BigDecimal("100.00")
        val exchangeRate = 5.50
        val expectedValue = BigDecimal("550.00")

        val actualValue = CurrencyUtils.calculateCurrencyValue(amount, exchangeRate)
        assertEquals(expectedValue, actualValue)
    }

    @Test
    internal fun `calculateCurrencyValue should throw IllegalParameterCurrencyCalcException when amount is null`() {

        val exchangeRate = 5.50

        assertThrows(IllegalParameterCurrencyCalcException::class.java) {
            CurrencyUtils.calculateCurrencyValue(null, exchangeRate)
        }
    }

    @Test
    internal fun `calculateCurrencyValue should throw IllegalParameterCurrencyCalcException when exchangeRate is null`() {

        val amount = BigDecimal("100.00")

        assertThrows(IllegalParameterCurrencyCalcException::class.java) {
            CurrencyUtils.calculateCurrencyValue(amount, null)
        }
    }

    @Test
    internal fun `calculateCurrencyValue should throw IllegalParameterCurrencyCalcException when both amount and exchangeRate are null`() {
        assertThrows(IllegalParameterCurrencyCalcException::class.java) {
            CurrencyUtils.calculateCurrencyValue(null, null)
        }
    }

}