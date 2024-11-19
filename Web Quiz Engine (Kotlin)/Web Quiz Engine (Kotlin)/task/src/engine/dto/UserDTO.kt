package engine.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

data class UserDTO(
    @field: Email(regexp = ".+[@].+[\\.].+", message = "Invalid email format")
    val email: String,

    @field: Size(min = 5, message = "Password must be at least 5 characters long")
    val password: String
)
