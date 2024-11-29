package dev.playground.books.model

data class Book(
    val id: Int,
    val name: String,
    val pageCount: Int,
    val authorId: Int
) {
    // this companion object equal to writing static methods in java
    companion object {
        val books = listOf(
            Book(1, "Quran", 604, 3),
            Book(2, "Harry Potter", 700, 2),
            Book(3, "Foobar", 100, 1),
            Book(4, "Spring Boot", 344, 2)
        )
        
        fun getBookById(id: Int): Book? {
            return books.find { it.id == id }
        }
    }
    
}