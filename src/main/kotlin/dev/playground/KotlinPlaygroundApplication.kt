package dev.playground

import dev.playground.data.persistence.entity.Account
import dev.playground.data.persistence.entity.Currency
import dev.playground.data.persistence.entity.Transaction
import dev.playground.data.persistence.repository.AccountRepository
import dev.playground.data.persistence.repository.TransactionRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.math.BigDecimal

@SpringBootApplication
class KotlinPlaygroundApplication {
	
	@Bean
	fun initData(accountRepository: AccountRepository, transactionRepository: TransactionRepository) = CommandLineRunner {
		val account1 = Account(firstName = "John", lastName = "Cena", email = "john.cena@example.com",
			balance = BigDecimal(5000.00), currency = Currency.USD)
		val account2 = Account(firstName = "Jane", lastName = "Smith", email = "jane.smith@example.com",
			balance = BigDecimal(10000.00),currency = Currency.GBP)
		accountRepository.saveAll(listOf(account1, account2))
		
		val transactions = listOf(
			Transaction(account = account1, amount = BigDecimal(1200.50), currency = Currency.USD,
				description = "Salary Payment"),
			Transaction(account = account1, amount = BigDecimal(-150.75), currency = Currency.USD,
				description = "Utility Bill"),
			Transaction(account = account2, amount = BigDecimal(5000.00), currency = Currency.GBP,
				description = "Freelance Income"),
			Transaction(account = account2, amount = BigDecimal(-200.00), currency = Currency.GBP,
				description = "Rent Payment")
		)
		transactionRepository.saveAll(transactions)
	}
	
}

fun main(args: Array<String>) {
	runApplication<KotlinPlaygroundApplication>(*args)
}
