package dev.playground.data.persistence.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal

@Entity
class Account @JvmOverloads constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,  // explicit mark so JPA assigns it after persistence
    val firstName: String,
    val lastName: String,
    @Email
    @NotBlank
    val email: String,
    val balance: BigDecimal,
    @Enumerated(EnumType.STRING)
    val currency: Currency,
    @OneToMany(mappedBy = "account", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val transactions: List<Transaction> = mutableListOf(),
)