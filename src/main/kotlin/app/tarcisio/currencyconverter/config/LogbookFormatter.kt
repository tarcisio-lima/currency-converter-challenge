package app.tarcisio.currencyconverter.config

import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.lang.NonNull
import org.zalando.logbook.*
import java.io.IOException
import java.text.MessageFormat

/**
 * Formata as mensagens que serão transitadas nos logs
 */
class LogbookFormatter : HttpLogFormatter {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(LogbookFormatter::class.java)
        private const val EMPTY_JSON_STRING = "{ }"
    }

    override fun format(
        @NonNull precorrelation: Precorrelation,
        @NonNull request: HttpRequest
    ): String {
        return MessageFormat.format(
            "↗️ [{0}] {1}\nheaders = {2}\nbody = {3}\n",
            formatCorrelationId(precorrelation),
            formatUrlRequest(request),
            formatHeaders(request),
            formatBody(request)
        )
    }

    override fun format(
        @NonNull correlation: Correlation,
        @NonNull response: HttpResponse
    ): String {
        return MessageFormat.format(
            "↙️ [{0}] {1}\nduration = {2} seg\nheaders = {3}\nbody = {4}\n",
            formatCorrelationId(correlation),
            formatUrlResponse(response),
            formatDurationToSeconds(correlation),
            formatHeaders(response),
            formatBody(response)
        )
    }

    private fun formatBody(message: HttpMessage): String {
        var bodyAsString = EMPTY_JSON_STRING

        try {
            if (bodyIsNotEmpty(message)) {
                bodyAsString = message.bodyAsString
            }
        } catch (error: IOException) {
            logger.warn("⚠️ Ocorreu um erro ao tentar extrair o corpo da requisição", error)
        }

        return bodyAsString
    }

    fun formatHeaders(message: HttpMessage): String {
        return if (headersIsNotEmpty(message)) {
            message.headers.toString()
        } else {
            EMPTY_JSON_STRING
        }
    }

    private fun formatUrlRequest(request: HttpRequest): String {
        return if (request.origin == Origin.REMOTE) {
            MessageFormat.format(
                "Solicitação recebida de: {0} \"{1}{2}\"",
                request.method,
                request.path,
                getQueryParamsIfExists(request)
            )
        } else {
            MessageFormat.format(
                "Solicitação enviada para: {0} \"{1}\"",
                request.method,
                request.requestUri
            )
        }
    }

    private fun formatCorrelationId(precorrelation: Precorrelation): String {
        return if (precorrelation.id != null) {
            precorrelation.id
        } else {
            "ID Ausente"
        }
    }

    private fun formatDurationToSeconds(correlation: Correlation): Int {
        return correlation.duration.seconds.toInt()
    }

    private fun formatUrlResponse(response: HttpResponse): String {
        return if (response.origin == Origin.REMOTE) {
            MessageFormat.format(
                "{0} {1} - Resposta recebida pela API",
                response.status,
                response.reasonPhrase
            )
        } else {
            MessageFormat.format(
                "{0} {1} - Resposta enviada pela API",
                response.status,
                response.reasonPhrase
            )
        }
    }

    @Throws(IOException::class)
    private fun bodyIsNotEmpty(message: HttpMessage): Boolean {
        return message.body != null && message.body.isNotEmpty()
    }

    private fun headersIsNotEmpty(message: HttpMessage): Boolean {
        return message.headers.isNotEmpty()
    }

    private fun getQueryParamsIfExists(request: HttpRequest): String {
        return if (request.query.isNotBlank()) {
            MessageFormat.format("?{0}", request.query)
        } else {
            StringUtils.EMPTY
        }
    }
}