package dev.playground.books.controller

import dev.playground.books.model.Book
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester

/**
 * @GraphQlTest is used for testing GraphQL controllers
 * `lateinit var` is used for `graphQlTester` as it is a dependency injected by Spring
 */
@GraphQlTest(BookController::class)
class BookControllerTest {
    
    // `graphQlTester` gives an ability to test the queries
    @Autowired
    private lateinit var graphQlTester: GraphQlTester
    
    @Test
    fun canGetBooks() {
        graphQlTester
            .documentName("books")          // ../graphql-test/books.graphql file
            .execute()
            .path("books")
            .entityList(Book::class.java)
            .hasSize(4)
    }
    
}