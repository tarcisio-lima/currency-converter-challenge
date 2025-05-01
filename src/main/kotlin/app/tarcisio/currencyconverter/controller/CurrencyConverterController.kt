package app.tarcisio.currencyconverter.controller

import app.tarcisio.currencyconverter.dto.application.ExchangeRequest
import app.tarcisio.currencyconverter.dto.application.ExchangeResponse
import app.tarcisio.currencyconverter.service.CurrencyConverterService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/currency-converter")
@Tag(name = "Currency Conversion", description = "API para conversão de moedas")
class CurrencyConverterController(
    private val currencyConverterService: CurrencyConverterService
) {

    @PostMapping("/exchange")
    @Operation(
        summary = "Realiza a conversão de moeda",
        description = "Retorna o valor de conversão entre moedas."
    )
    fun peformExchange(
        @RequestBody exchangeRequest: ExchangeRequest
    ): ResponseEntity<ExchangeResponse> {
        return currencyConverterService.peformExchange(exchangeRequest)
    }

    @GetMapping("/transactions/{userId}")
    @Operation(
        summary = "Consulta transações",
        description = "Consulta todas as transações de conversão realizadas por um usuário específico."
    )
    fun getTransactions(
        @PathVariable userId: Long
    ): ResponseEntity<List<ExchangeResponse>> {
        return currencyConverterService.getTransactions(userId)
    }

}