package app.tarcisio.currencyconverter.repository.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "currency_history")
data class TransactionHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long?,
    val userId: Long?,
    val sourceCurrency: String?,
    val targetCurrency: String?,
    val exchangeRate: BigDecimal?,
    val registrationDate: LocalDateTime?
)
