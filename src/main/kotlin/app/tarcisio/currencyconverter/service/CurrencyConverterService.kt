package app.tarcisio.currencyconverter.service

import app.tarcisio.currencyconverter.client.ExchangeRateClient
import app.tarcisio.currencyconverter.common.TransactionalContext
import app.tarcisio.currencyconverter.dto.application.ExchangeRequest
import app.tarcisio.currencyconverter.dto.application.ExchangeResponse
import app.tarcisio.currencyconverter.dto.web.ExchangeRateApiResponse
import app.tarcisio.currencyconverter.entity.TransactionHistoryEntity
import app.tarcisio.currencyconverter.mapper.ExchangeMapper
import app.tarcisio.currencyconverter.repository.TransactionHistoryRespository
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CurrencyConverterService(
    private val client: ExchangeRateClient,
    private val transactionHistoryRespository: TransactionHistoryRespository,
    @Value("\${exchange-rate-api.api-key}") private val exchangeApiKey: String
){

    @Transactional
    fun peformExchange(request: TransactionalContext<ExchangeRequest>): ResponseEntity<ExchangeResponse> {

        val result: ExchangeRateApiResponse? = client.getExchange(
            request.addHeader("apiKey", exchangeApiKey)
        )
        val savedEntity = saveTransactionHistory(result, request.get().userId)

        return ResponseEntity.ok(ExchangeMapper.mapToExchangeResponse(savedEntity))
    }

    fun getTransactions(userId: Long): ResponseEntity<List<ExchangeResponse>> {
        val result = transactionHistoryRespository.findAllByUserId(userId)
        return if (result.isNotEmpty())
            ResponseEntity.ok(result.map { ExchangeMapper.mapToExchangeResponse(it) })
        else
            ResponseEntity.notFound().build()
    }

    fun saveTransactionHistory(data : ExchangeRateApiResponse?, userId: Long?) : TransactionHistoryEntity {
        val mappedResult = ExchangeMapper.mapToTransactionHistoryEntity(data)
        return transactionHistoryRespository.save(mappedResult.copy(userId = userId))
    }

}