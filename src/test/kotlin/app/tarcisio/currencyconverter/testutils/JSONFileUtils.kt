package app.tarcisio.currencyconverter.testutils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

internal object JSONFileUtils {

    @Throws(IOException::class)
    fun <T> getJsonFileAs(filename: String, typeValue: Class<T>): T {
        val file = Paths.get("src", "test", "resources", "__files", filename).toFile()
        val mapper = ObjectMapper().registerModule(JavaTimeModule())
        return mapper.readValue(file, typeValue)
    }

    @Throws(IOException::class)
    fun getFileAsJsonString(filename: String): String {
        val file = Paths.get("src", "test", "resources", "__files", filename).toFile()
        return Files.readString(file.toPath())
    }
}