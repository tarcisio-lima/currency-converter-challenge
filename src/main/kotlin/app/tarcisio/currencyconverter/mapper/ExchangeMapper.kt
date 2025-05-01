package app.tarcisio.currencyconverter.mapper

import app.tarcisio.currencyconverter.dto.application.ExchangeResponse
import app.tarcisio.currencyconverter.dto.web.ExchangeRateApiResponse
import app.tarcisio.currencyconverter.repository.entity.TransactionHistoryEntity
import app.tarcisio.currencyconverter.utils.CurrencyUtils
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
            targetCurrency = value?.query?.to,
            exchangeRate = value?.info?.rate,
            registrationDate = value?.date.let { localDate ->
                LocalDateTime.of(localDate, LocalTime.now())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
            }
        )
    }

    fun mapToExchangeResponse(value: ExchangeRateApiResponse?): ExchangeResponse {
        return ExchangeResponse(
            id = null,
            userId = 0,
            sourceCurrency = value?.query?.from,
            sourceAmount = value?.query?.amount?.setScale(2, RoundingMode.HALF_EVEN),
            targetCurrency = value?.query?.to,
            targetAmount = calculateCurrencyValue(value?.query?.amount, value?.info?.rate?.toDouble())
                .setScale(2, RoundingMode.HALF_EVEN),
            exchangeRate = value?.info?.rate?.toDouble(),
            registrationDate = value?.date.let { localDate ->
                LocalDateTime.of(localDate, LocalTime.now())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
            }
        )
    }

    fun mapToExchangeResponse(value: TransactionHistoryEntity): ExchangeResponse {
        TODO("Not yet implemented")
    }

}