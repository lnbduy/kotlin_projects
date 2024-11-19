package engine.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "quiz_option")
data class QuizOption(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private val id: Int = 0,

    @Column
    val content: String,

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    @JsonBackReference
    var quiz: Quiz? = null
)
