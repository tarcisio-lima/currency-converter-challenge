package app.tarcisio.currencyconverter.service

import app.tarcisio.currencyconverter.client.ExchangeRateClient
import app.tarcisio.currencyconverter.common.TransactionalContext
import app.tarcisio.currencyconverter.dto.application.ExchangeRequest
import app.tarcisio.currencyconverter.dto.application.ExchangeResponse
import app.tarcisio.currencyconverter.dto.web.ExchangeRateApiResponse
import app.tarcisio.currencyconverter.mapper.ExchangeMapper
import app.tarcisio.currencyconverter.repository.TransactionHistoryRespository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CurrencyConverterService(
    private val client: ExchangeRateClient,
    private val transactionHistoryRespository: TransactionHistoryRespository
){

    fun peformExchange(request: ExchangeRequest): ResponseEntity<ExchangeResponse> {

        val result: ExchangeRateApiResponse? = client.getExchange(
            TransactionalContext(request)
                .addHeader("apiKey", "FI2hQ6TsSiZmUPmsQofjv5G2mGJbGs2R")
        )

        saveTransactionHistory(result, request.userId)

        val response = ExchangeMapper.mapToExchangeResponse(result)
        return ResponseEntity.ok(response)
    }

    fun getTransactions(userId: Long): ResponseEntity<List<ExchangeResponse>> {
        val result = transactionHistoryRespository.findAllByUserId(userId)
        return if (result.isNotEmpty())
            ResponseEntity.ok(result.map { ExchangeMapper.mapToExchangeResponse(it) })
        else
            ResponseEntity.notFound().build()
    }

    @Transactional
    fun saveTransactionHistory(data : ExchangeRateApiResponse?, userId: Long) {
        val mappedResult = ExchangeMapper.mapToTransactionHistoryEntity(data)
        transactionHistoryRespository.save(mappedResult.copy(userId = userId))
    }

}