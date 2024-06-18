package bullscows

import kotlin.random.Random

class ValidationException(override val message: String?) : Exception(message)

class BullAndCowGame {
    private var secretCode: String = ""
    private var turn = 0;

    fun play() {
        try {
            val length = getInputAsInt("Input the length of the secret code:")
            val numOfSymbols = getInputAsInt("Input the number of possible symbols in the code:")
            validateLengthAndNumOfSymbols(length, numOfSymbols)

            generateSecretCode(length, numOfSymbols)

            println("Okay, let's start a game!")
            gameLoop()
        } catch (ex: ValidationException) {
            println(ex.message)
        }
    }

    private fun getInputAsInt(prompt: String): Int {
        println(prompt)
        val input = readln()
        return input.toIntOrNull() ?: throw ValidationException("Error: \"$input\" isn't a number")
    }

    private fun validateLengthAndNumOfSymbols(length: Int, numOfSymbols: Int) {
        if (length <= 0) {
            throw ValidationException("Error: invalid length")
        }
        if (numOfSymbols < length) {
            throw ValidationException("Error: it's not possible to generate a code with a length of $length with $numOfSymbols unique symbols.")
        }
        if (length > 36 || numOfSymbols > 36) {
            throw ValidationException("Error: maximum number of possible symbols in the code is 36 (0-9, a-z)")
        }
    }

    private fun generateSecretCode(length: Int, numOfSymbols: Int) {
        val charPool = generateCharPool(numOfSymbols)
        val uniqueDigits = StringBuilder()

        do {
            val randomNum = Random.nextInt(0, charPool.size - 1)
            val randomChar = charPool[randomNum]
            if (!uniqueDigits.contains(randomChar)) {
                uniqueDigits.append(randomChar)
            }
        } while (uniqueDigits.length < length)

        secretCode = uniqueDigits.toString()

        val range = charPool.last().let { if (it >= 'a') "0-9, a-$it" else "0-$it" }
        println("The secret is prepared: ${"*".repeat(length)} ($range).")
    }

    private fun generateCharPool(numOfSymbols: Int): List<Char> {
        return List(numOfSymbols) { if (it < 10) '0' + it else 'a' + it - 10 }
    }

    private fun gameLoop() {
        do {
            turn++
            val answer = getPlayerAnswer()
        } while (!gradePlayerAnswer(answer))
    }

    private fun getPlayerAnswer(): String {
        println("Turn $turn. Answer:")
        return readln()
    }

    private fun gradePlayerAnswer(answer: String): Boolean {
        val countBulls = answer.indices.count { i -> secretCode[i] == answer[i] }
        val countCows = answer.count { it in secretCode } - countBulls
        populateResponse(countBulls, countCows)
        return countBulls == secretCode.length
    }

    private fun populateResponse(countBull: Int, countCow: Int) {
        val grade = when {
            countBull == 0 && countCow == 0 -> "None"
            else -> {
                val bullStr = toText(countBull, "bull")
                val cowStr = toText(countCow, "cow")
                listOf(bullStr, cowStr).filter { it.isNotEmpty() }.joinToString(" and ")
            }
        }

        val congratulations = if (countBull == secretCode.length) {
            "\nCongratulations! You guessed the secret code"
        } else ""

        var response = "Grade: $grade."
        if (congratulations.isNotEmpty()) response = "$response$congratulations"

        println(response)
    }

    private fun toText(count: Int, type: String): String {
        return when (count) {
            0 -> ""
            1 -> "$count $type"
            else -> "$count ${type}s"
        }
    }
}