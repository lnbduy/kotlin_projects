package engine.controller

import engine.exception.DuplicateUserExistsException
import engine.exception.QuizNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(QuizNotFoundException::class)
    fun handleQuizNotFoundException(ex: QuizNotFoundException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(DuplicateUserExistsException::class)
    fun handleDuplicateUserExistsException(ex: DuplicateUserExistsException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }
}
