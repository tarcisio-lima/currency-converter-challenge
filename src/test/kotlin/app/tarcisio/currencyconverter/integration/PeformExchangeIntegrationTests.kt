package app.tarcisio.currencyconverter.integration

import app.tarcisio.currencyconverter.dto.application.ExchangeRequestFixture.example
import app.tarcisio.currencyconverter.testutils.JSONMapperUtils
import com.github.tomakehurst.wiremock.client.WireMock
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
internal class PeformExchangeIntegrationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    internal fun `should return success when calling perform exchange`() {

        WireMock.stubFor(
            WireMock.get(WireMock.urlPathMatching("/exchangerates_data/convert"))
                .withQueryParam("from", WireMock.equalTo("USD"))
                .withQueryParam("to", WireMock.equalTo("BRL"))
                .withQueryParam("amount", WireMock.equalTo("100.00"))
                .withHeader("apiKey", WireMock.equalTo("test-api-key"))
                .willReturn(
                    WireMock.aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON.toString())
                        .withBodyFile("exchangerateclient_response_ok.json")
                        .withStatus(200)
                )
        )

        val request = JSONMapperUtils.objectToJson(example())

        mockMvc.perform(
            MockMvcRequestBuilders.post("/currency-converter/exchange")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
        .andExpect(status().isOk())
        .andExpect {
            jsonPath("$.id", equalTo(1))
            jsonPath("$.userId", equalTo(1))
            jsonPath("$.sourceCurrency", equalTo("USD"))
            jsonPath("$.sourceAmount", equalTo("100.00"))
            jsonPath("$.targetCurrency", equalTo("BRL"))
            jsonPath("$.targetAmount", equalTo("565.66"))
            jsonPath("$.exchangeRate", equalTo(5.656604))
        }
    }

    @ParameterizedTest
    @EnumSource(value = HttpStatus::class, names = [
        "BAD_REQUEST",
        "NOT_FOUND",
        "INTERNAL_SERVER_ERROR"
    ])
    internal fun `should return errors when calling perform exchange`(status: HttpStatus) {

        val filename = when (status) {
            HttpStatus.BAD_REQUEST -> "4xx-bad-request.json"
            HttpStatus.NOT_FOUND -> "4xx-not-found.json"
            HttpStatus.INTERNAL_SERVER_ERROR -> "5xx-internal-server-error.json"
            else -> throw AssertionError("Parametized value is invalid: $status")
        }

        WireMock.stubFor(
            WireMock.get(WireMock.urlPathMatching("/exchangerates_data/convert"))
                .withQueryParam("from", WireMock.equalTo("USD"))
                .withQueryParam("to", WireMock.equalTo("BRL"))
                .withQueryParam("amount", WireMock.equalTo("100.00"))
                .withHeader("apiKey", WireMock.equalTo("test-api-key"))
                .willReturn(
                    WireMock.aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON.toString())
                        .withBodyFile(filename)
                        .withStatus(status.value())
                )
        )

        val request = JSONMapperUtils.objectToJson(example())

        mockMvc.perform(
            MockMvcRequestBuilders.post("/currency-converter/exchange")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
            .andExpect(status().`is`(status.value()))
            .andExpect {
                jsonPath("$.message").isNotEmpty
            }
    }


}