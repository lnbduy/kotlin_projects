package engine.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "completed_quiz")
data class CompletedQuiz(
    @Id
    @Column(name = "completed_quiz_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val completedQuizId: Int = 0,

    @Column(name = "quiz_id")
    val id: Int,

    @ManyToOne
    @JsonIgnore
    @JoinColumn(referencedColumnName = "id")
    private val user: User? = null,

    @Column(name = "completed_at")
    val completedAt: LocalDateTime = LocalDateTime.now()
)
