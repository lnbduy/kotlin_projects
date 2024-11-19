package engine.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import engine.dto.QuizResponseDTO
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "quiz")
data class Quiz(
    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "text", nullable = false)
    val text: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private val id: Int = 0

    @OneToMany(mappedBy = "quiz", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JsonManagedReference
    var options: MutableList<QuizOption> = mutableListOf()

    @OneToMany(mappedBy = "quiz", cascade = [CascadeType.ALL])
    @JsonManagedReference
    @JsonIgnore
    var answers: MutableList<Answer> = mutableListOf()

    @ManyToOne
    @JsonIgnore
    @JoinColumn(referencedColumnName = "id")
    var author: User? = null

    fun toQuizResponseDto(): QuizResponseDTO {
        return QuizResponseDTO(
            id = id,
            title = title,
            text = text,
            options = options.map { it.content }
        )
    }
}
