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
@Table(name ="answer")
data class Answer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private val id: Int = 0,

    @Column(nullable = false)
    val answer: Int,

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    @JsonBackReference
    var quiz: Quiz
)
