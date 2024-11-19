package engine.dto

import engine.entity.Answer
import engine.entity.Quiz
import engine.entity.QuizOption
import engine.entity.User
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class CreateQuizDTO(
    @field: NotNull
    @field: NotEmpty
    val title: String,

    @field: NotNull
    @field: NotEmpty
    val text: String,

    @field: Size(min = 2)
    val options: MutableList<String>,

    val answer: MutableList<Int>?
) {
    fun toQuiz(user: User): Quiz {
        val quiz = Quiz(title, text)
        val quizOptions = options.map { QuizOption(content = it, quiz = quiz) }.toMutableList()
        quiz.options = quizOptions

        val quizAnswers = answer?.map { Answer(answer = it, quiz = quiz) }?.toMutableList() ?: mutableListOf()
        quiz.answers = quizAnswers
        quiz.author = user
        return quiz
    }
}
