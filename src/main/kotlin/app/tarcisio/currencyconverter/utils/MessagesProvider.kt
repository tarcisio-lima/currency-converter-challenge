package app.tarcisio.currencyconverter.utils

import java.util.*

object MessagesProvider {

    private val defaultBundle = ResourceBundle.getBundle("bundles.messages")

    fun getMessage(key: String): String {
        return defaultBundle.getString(key)
    }

}