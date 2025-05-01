package app.tarcisio.currencyconverter.common

/**
 * Oferece encapsulamento dos dados de requisição manipulação dentro da aplicação
 */
class TransactionalContext<T : Any>(private val request: T) {

    private val headers: MutableMap<String, String> = mutableMapOf()

    fun get(): T  = request

    fun <U : Any> map(mapper: (T) -> U): TransactionalContext<U> {
        return TransactionalContext(mapper.invoke(request))
    }

    fun addHeader(headerKey: String, headerValue: String): TransactionalContext<T> {
        headers[headerKey] = headerValue
        return this
    }

    fun getHeader(headerKey: String): String? {
        return headers[headerKey]
    }

}