package dev.playground.books.model

data class Author(
    val id: Int,
    val name: String
) {
    companion object {
        val authors = listOf(
            Author(1, "John Doe"),
            Author(2, "Mike Smith"),
            Author(3, "")
        )
        
        fun getAuthorById(id: Int): Author? {
            return authors.find { it.id == id }
        }
    }
}
