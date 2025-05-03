package app.tarcisio.currencyconverter.testutils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import java.io.IOException

internal object JSONMapperUtils {

    @Throws(IOException::class)
    fun <T> objectToJson(type: T): String {
        val mapper = ObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .registerModule(JavaTimeModule())
        return mapper.writeValueAsString(type)
    }

}