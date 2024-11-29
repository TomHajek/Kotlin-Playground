package dev.playground.books.controller

import dev.playground.books.model.Author
import dev.playground.books.model.Book
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class BookController {
    
    // these are mapped to `Query` in `schema.graphqls` file
    @QueryMapping
    fun books(): List<Book> {
        return Book.books
    }
    
    @QueryMapping
    fun bookById(@Argument id: Int): Book? {
        return Book.getBookById(id)
    }
    
    // this one is mapping author to our schema and get the author from the book
    @SchemaMapping
    fun author(book: Book): Author? {
        return Author.getAuthorById(book.authorId)
    }
    
}