package app.tarcisio.currencyconverter.mapper

import app.tarcisio.currencyconverter.dto.application.ExchangeResponse
import app.tarcisio.currencyconverter.dto.web.ExchangeRateApiResponse
import app.tarcisio.currencyconverter.entity.TransactionHistoryEntity
import app.tarcisio.currencyconverter.utils.CurrencyUtils.calculateCurrencyValue
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

object ExchangeMapper {

    fun mapToTransactionHistoryEntity(value: ExchangeRateApiResponse?): TransactionHistoryEntity {
        return TransactionHistoryEntity(
            id = null,
            userId = 0,
            sourceCurrency = value?.query?.from,
            amount = value?.query?.amount?.setScale(2, RoundingMode.HALF_EVEN),
            targetCurrency = value?.query?.to,
            exchangeRate = value?.info?.rate,
            registrationDate = value?.date.let { localDate -> LocalDateTime.of(localDate, LocalTime.now())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
            }
        )
    }

    fun mapToExchangeResponse(value: TransactionHistoryEntity): ExchangeResponse {
        return ExchangeResponse(
            id = value.id,
            userId = value.userId,
            sourceCurrency = value.sourceCurrency,
            sourceAmount = value.amount?.setScale(2, RoundingMode.HALF_EVEN),
            targetCurrency = value.targetCurrency,
            exchangeRate = value.exchangeRate,
            registrationDate = value.registrationDate,
            targetAmount = calculateCurrencyValue(value.amount, value.exchangeRate)
        )
    }

}