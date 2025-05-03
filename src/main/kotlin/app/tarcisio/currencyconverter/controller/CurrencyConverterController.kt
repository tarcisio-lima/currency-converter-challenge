package app.tarcisio.currencyconverter.controller

import app.tarcisio.currencyconverter.common.TransactionalContext
import app.tarcisio.currencyconverter.dto.application.ExchangeRequest
import app.tarcisio.currencyconverter.dto.application.ExchangeResponse
import app.tarcisio.currencyconverter.exception.ErrorResponse
import app.tarcisio.currencyconverter.service.CurrencyConverterService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
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
        description = "Retorna o valor de conversão entre moedas.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Conversão realizada com sucesso",
                content = [Content(schema = Schema(implementation = ExchangeResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Requisição inválida",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Erro interno da aplicação",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
        ]
    )
    fun peformExchange(
        @RequestBody exchangeRequest: ExchangeRequest
    ): ResponseEntity<ExchangeResponse> {
        return currencyConverterService.peformExchange(TransactionalContext(exchangeRequest))
    }

    @GetMapping("/transactions/{userId}")
    @Operation(
        summary = "Consulta transações",
        description = "Consulta todas as transações de conversão realizadas por um usuário específico.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Consulta realizada com sucesso",
                content = [Content(schema = Schema(implementation = ExchangeResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Requisição inválida",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Usuário e suas respectivas transações não foram encontrados"
            ),
            ApiResponse(
                responseCode = "500",
                description = "Erro interno da aplicação",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    fun getTransactions(
        @PathVariable userId: Long
    ): ResponseEntity<List<ExchangeResponse>> {
        return currencyConverterService.getTransactions(userId)
    }

}