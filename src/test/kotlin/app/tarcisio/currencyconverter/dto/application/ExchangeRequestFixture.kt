package app.tarcisio.currencyconverter.dto.application

import java.math.BigDecimal

object ExchangeRequestFixture {

    fun example(): ExchangeRequest {
        return ExchangeRequest(1L, "USD", "BRL", BigDecimal("100.00"))
    }

}
