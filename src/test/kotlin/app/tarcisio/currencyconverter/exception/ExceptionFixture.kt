package app.tarcisio.currencyconverter.exception


import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestClientResponseException
import java.time.LocalDateTime

object ExceptionFixture {

    fun restClientResponseException(status: HttpStatus): RestClientResponseException {
        return RestClientResponseException(
            "An error occurred while attempting to retrieve data",
            status,
            status.reasonPhrase,
            HttpHeaders.EMPTY,
            ToStringBuilder.reflectionToString(
                ErrorResponse(
                    status = status.value(),
                    error = status.reasonPhrase,
                    message = "An error occurred",
                    timeStamp = LocalDateTime.now(),
                    trace = null
                ),
                ToStringStyle.JSON_STYLE
            ).encodeToByteArray(),
            null
        )
    }

}