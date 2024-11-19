package engine.dto

data class QuizResponseDTO(val id: Int, val title: String, val text: String, val options: List<String>)
