package app.tarcisio.currencyconverter.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "currency_history")
data class TransactionHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val userId: Long?,
    val sourceCurrency: String?,
    val amount: BigDecimal?,
    val targetCurrency: String?,
    val exchangeRate: BigDecimal?,
    val registrationDate: LocalDateTime?
)
