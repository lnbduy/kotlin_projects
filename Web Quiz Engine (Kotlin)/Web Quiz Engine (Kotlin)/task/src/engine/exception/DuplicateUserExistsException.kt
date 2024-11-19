package engine.exception

data class DuplicateUserExistsException(override val message: String?): RuntimeException(message)
