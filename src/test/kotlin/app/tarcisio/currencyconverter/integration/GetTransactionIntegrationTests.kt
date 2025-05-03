package app.tarcisio.currencyconverter.integration

import app.tarcisio.currencyconverter.entity.TransactionHistoryEntityFixture
import app.tarcisio.currencyconverter.repository.TransactionHistoryRespository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
internal class GetTransactionIntegrationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    internal fun `should return NOT FOUND an empty list when no transactions exist`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/currency-converter/transactions/1"))
            .andExpect(status().isNotFound)
    }

    @Test
    @Sql(scripts = ["/db/insert-test-data.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = ["/db/cleanup-test-data.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    internal fun `should return populated list when calling get transactions`() {

        mockMvc.perform(
            MockMvcRequestBuilders.get("/currency-converter/transactions/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk)
        .andExpect {
            jsonPath("$").isArray
            jsonPath("$.length()").value(2)
        }

    }

}