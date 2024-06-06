package tictactoe

fun main() {
    val grid = listOf(mutableListOf('_', '_', '_'), mutableListOf('_', '_', '_'), mutableListOf('_', '_', '_'))
    printGrid(grid)
    playGame(grid)
}

private fun playGame(grid: List<MutableList<Char>>) {
    var gameStatus = ""
    var xTurn = true
    do {
        if (xTurn) {
            getNextMove(grid, 'X')
        } else getNextMove(grid, 'O')
        xTurn = !xTurn
        printGrid(grid)
        gameStatus = checkGameStatus(grid)
    } while (!listOf("Draw", "X wins", "O wins").contains(gameStatus))
    println(gameStatus)
}

fun getNextMove(grid: List<MutableList<Char>>, char: Char) {
    var validMove = false
    var move = ""
    do {
        move = readln()
        validMove = checkValidMove(move, grid)
    } while (!validMove)
    val (x, y) = move.split(" ")
    grid[x.toInt() - 1][y.toInt() - 1] = char
}

fun checkValidMove(move: String, grid: List<MutableList<Char>>): Boolean {
    val coordinates = move.split(" ")
    if (coordinates.size != 2) {
        println("Enter two numbers separated by space!")
        return false
    }
    val (x, y) = coordinates
    return when {
        x.toIntOrNull() == null || y.toIntOrNull() == null -> {
            println("You should enter numbers!")
            false
        }
        x.toInt() !in 1..3 || y.toInt() !in 1..3 -> {
            println("Coordinates should be from 1 to 3!")
            false
        }
        grid[x.toInt() - 1][y.toInt() - 1] != '_' -> {
            println("This cell is occupied! Choose another one!")
            false
        }
        else -> true
    }
}

fun printGrid(grid: List<MutableList<Char>>) {
    println("---------")
    grid.forEach { row ->
        println("| ${row[0]} ${row[1]} ${row[2]} |")
    }
    println("---------")
}

fun checkGameStatus(grid: List<MutableList<Char>>): String {
    return when {
        checkImpossible(grid) -> "Impossible"
        checkWin(grid, 'X') -> "X wins"
        checkWin(grid, 'O') -> "O wins"
        grid.flatten().any { it == '_' } -> "Game not finished"
        else -> "Draw"
    }
}

fun checkImpossible(grid: List<MutableList<Char>>): Boolean {
    val xCount = grid.flatten().count { it == 'X' }
    val oCount = grid.flatten().count { it == 'O' }
    return Math.abs(xCount - oCount) > 1 || (checkWin(grid, 'X') && checkWin(grid, 'O'))
}

fun checkWin(grid: List<MutableList<Char>>, s: Char): Boolean {
    val win = listOf(
        listOf(grid[0][0], grid[0][1], grid[0][2]),
        listOf(grid[1][0], grid[1][1], grid[1][2]),
        listOf(grid[2][0], grid[2][1], grid[2][2]),
        listOf(grid[0][0], grid[1][0], grid[2][0]),
        listOf(grid[0][1], grid[1][1], grid[2][1]),
        listOf(grid[0][2], grid[1][2], grid[2][2]),
        listOf(grid[0][0], grid[1][1], grid[2][2]),
        listOf(grid[0][2], grid[1][1], grid[2][0])
    )
    return win.any { it.all { it == s } }
}