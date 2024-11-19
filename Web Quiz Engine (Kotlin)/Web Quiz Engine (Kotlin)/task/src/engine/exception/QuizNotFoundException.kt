package engine.exception

data class QuizNotFoundException(override val message: String?): RuntimeException(message)

