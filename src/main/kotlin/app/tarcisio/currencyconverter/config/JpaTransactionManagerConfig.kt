package app.tarcisio.currencyconverter.config

import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 * Habilita o controle transacional na aplicação
 */
@Configuration
@EnableTransactionManagement
class JpaTransactionManagerConfig