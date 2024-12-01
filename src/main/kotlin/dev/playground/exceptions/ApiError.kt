package dev.playground.exceptions

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

/**
 * This class is responsible for returning response body in case of error, in the form of:
 *  {
 *      "message": xxx,
 *      "status": xxx,
 *      "code": xxx,
 *      "timestamp": xxx
 *  }
 *
 */
data class ApiError(
        private val _message: String?,                      // if this value is null, return "Something went wrong"
        val status: HttpStatus = HttpStatus.BAD_REQUEST,    // default value
        val code: Int = status.value(),
        val timestamp: LocalDateTime = LocalDateTime.now()
){
    val message: String
        get() = _message ?: "Something went wrong"
}