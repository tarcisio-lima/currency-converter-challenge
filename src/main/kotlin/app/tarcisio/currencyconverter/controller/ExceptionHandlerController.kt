package app.tarcisio.currencyconverter.controller

import app.tarcisio.currencyconverter.exception.ErrorResponse
import app.tarcisio.currencyconverter.exception.IllegalParameterCurrencyCalcException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.RestClientResponseException
import java.time.LocalDateTime

@ControllerAdvice
class ExceptionHandlerController {

    @ExceptionHandler(RestClientResponseException::class)
    fun handleRestClientResponseException(
        error: RestClientResponseException
    ): ResponseEntity<String> {
        return ResponseEntity.status(error.statusCode)
            .contentType(MediaType.APPLICATION_JSON)
            .body(error.responseBodyAsString)
    }

    @ExceptionHandler(IllegalParameterCurrencyCalcException::class)
    fun handleIllegalParameterCurrencyCalcException(error: IllegalParameterCurrencyCalcException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest()
            .body(
                ErrorResponse(
                    status = HttpStatus.BAD_REQUEST.value(),
                    error = HttpStatus.BAD_REQUEST.reasonPhrase,
                    message = error.localizedMessage,
                    timeStamp = LocalDateTime.now(),
                    trace = error.stackTraceToString()
                )
            )
    }

}