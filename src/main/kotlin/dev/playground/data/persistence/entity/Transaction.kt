package dev.playground.data.persistence.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.Instant

@Entity
class Transaction @JvmOverloads constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    val account: Account,
    val amount: BigDecimal,
    @Enumerated(EnumType.STRING)
    val currency: Currency,
    val description: String,
    @CreationTimestamp
    val timestamp: Instant = Instant.now(),
)