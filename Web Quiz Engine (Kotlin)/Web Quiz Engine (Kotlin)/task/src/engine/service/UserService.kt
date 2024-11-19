package engine.service

import engine.entity.User
import engine.exception.DuplicateUserExistsException
import engine.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserService(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {
    fun registerUser(email: String, password: String): User {
        if (userRepository.findByEmail(email) != null) {
            throw DuplicateUserExistsException("User with email $email already exists")
        }
        val encodedPassword = passwordEncoder.encode(password)
        val user = User(email = email, password = encodedPassword)
        return userRepository.save(user)
    }

    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }
}
