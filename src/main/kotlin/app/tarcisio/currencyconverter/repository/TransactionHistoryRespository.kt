package app.tarcisio.currencyconverter.repository

import app.tarcisio.currencyconverter.entity.TransactionHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionHistoryRespository : JpaRepository<TransactionHistoryEntity, Long> {
    fun findAllByUserId(userId: Long): List<TransactionHistoryEntity>
}