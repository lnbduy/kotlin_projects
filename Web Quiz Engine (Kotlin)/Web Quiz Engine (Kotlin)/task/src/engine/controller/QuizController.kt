package engine.controller

import engine.dto.AnswerDTO
import engine.dto.CreateQuizDTO
import engine.dto.QuizResponseDTO
import engine.dto.QuizFeedbackDTO
import engine.entity.CompletedQuiz
import engine.entity.User
import engine.service.QuizService
import engine.service.UserService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/quizzes")
class QuizController(private val quizService: QuizService, private val userService: UserService) {
    @GetMapping
    fun getAllQuizzes(@RequestParam page: Int?): Page<QuizResponseDTO> {
        return quizService.getAllQuizzes(page)
    }

    @GetMapping("/{id}")
    fun getQuiz(@PathVariable id: Int): QuizResponseDTO {
        return quizService.getQuizById(id).toQuizResponseDto()
    }

    @GetMapping("/completed")
    fun getCompletedQuizzes(@RequestParam page: Int?, @AuthenticationPrincipal user: User): Page<CompletedQuiz> {
        return quizService.getCompletedQuizzes(page = page, user = user)
    }

    @PostMapping("/{id}/solve")
    fun solveQuiz(@PathVariable id: Int, @RequestBody answerDto: AnswerDTO): QuizFeedbackDTO {
        return quizService.solveQuiz(id, answerDto.answer)
    }

    @PostMapping
    fun createQuiz(@Valid @RequestBody createQuizDto: CreateQuizDTO,  @AuthenticationPrincipal user: User): QuizResponseDTO {
        return quizService.createQuiz(createQuizDto.toQuiz(user)).toQuizResponseDto()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteQuiz(@PathVariable id: Int, @AuthenticationPrincipal user: User) {
        val user = userService.findByEmail(user.email)
        quizService.deleteQuiz(id, user)
    }
}

