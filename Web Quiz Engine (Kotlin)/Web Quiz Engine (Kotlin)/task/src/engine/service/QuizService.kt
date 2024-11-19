package engine.service

import engine.entity.Quiz
import engine.dto.QuizFeedbackDTO
import engine.dto.QuizResponseDTO
import engine.entity.CompletedQuiz
import engine.entity.User
import engine.exception.QuizNotFoundException
import engine.repository.CompletedQuizRepository
import engine.repository.QuizRepository
import org.springframework.boot.actuate.endpoint.SecurityContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class QuizService(
    private val quizRepository: QuizRepository,
    private val completedQuizRepository: CompletedQuizRepository
) {
    fun createQuiz(quiz: Quiz): Quiz {
        return quizRepository.save(quiz)
    }

    fun getQuizById(id: Int): Quiz {
        return quizRepository.findById(id).orElseThrow { QuizNotFoundException("Quiz with $id not found") }
    }

    fun getAllQuizzes(page: Int?, pageSize: Int = 10): Page<QuizResponseDTO> {
        return quizRepository.findAll(PageRequest.of(page ?: 0, pageSize)).map { it.toQuizResponseDto() }
    }

    fun solveQuiz(id: Int, answer: List<Int>): QuizFeedbackDTO {
        val quiz = getQuizById(id)
        val authentication = SecurityContextHolder.getContext().authentication
        val user = authentication.principal as User
        return if (answer.toSet() == quiz.answers.map { it.answer }.toSet()) {
            val completedQuiz = CompletedQuiz(id = id, user =  user )
            completedQuizRepository.save(completedQuiz,)
            QuizFeedbackDTO(true, "Congratulations, you're right!")
        } else {
            QuizFeedbackDTO(false, "Wrong answer! Please, try again.")
        }
    }

    fun deleteQuiz(id: Int, user: User?) {
        val quiz = getQuizById(id)
        if (quiz.author != user) {
            throw AccessDeniedException("You are not the author of this quiz")
        }
        quizRepository.delete(quiz)
    }

    fun getCompletedQuizzes(page: Int?, user: User): Page<CompletedQuiz> {
        return completedQuizRepository.findAllByUserOrderByCompletedAtDesc(user, PageRequest.of(page ?: 0, 10))
    }
}
