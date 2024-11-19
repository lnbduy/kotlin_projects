package engine.controller

import engine.dto.UserDTO
import engine.service.UserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserRegistrationController(private val userService: UserService) {
    @PostMapping("/api/register")
    fun registerUser(@Valid @RequestBody userDto: UserDTO) {
        userService.registerUser(userDto.email, userDto.password)
    }
}
