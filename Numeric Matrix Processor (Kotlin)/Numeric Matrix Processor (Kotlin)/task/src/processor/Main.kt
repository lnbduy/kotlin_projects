package processor

import java.util.Scanner


fun main() {
    run()
}

fun run() {
    while (true) {
        printMenu()
        println("Your choice:")
        val input = readln().toInt()
        when(input) {
            1 -> addMatrices()
            2 -> multiplyMatrixByAConstant()
            3 -> multiplyMatrices()
            4 -> transposeMatrix()
            5 -> calculateDeterminant()
            6 -> inverseMatrix()
            0 -> break
        }
    }
}

fun printMenu() {
    println("""
        1. Add matrices
        2. Multiply matrix by a constant
        3. Multiply matrices
        4. Transpose matrix
        5. Calculate a determinant
        6. Inverse matrix
        0. Exit
    """.trimIndent())
}

fun addMatrices() {
    val matrix1 = readMatrix("first")
    val matrix2 = readMatrix("second")
    if (matrix1.size != matrix2.size || matrix1[0].size != matrix2[0].size) {
        println("The operation cannot be performed.")
        return
    }
    val resultMatrix = addMatrices(matrix1, matrix2)
    println("The result is:")
    printMatrix(resultMatrix)
}

fun addMatrices(matrix1: Array<DoubleArray>, matrix2: Array<DoubleArray>): Array<DoubleArray> {
    return Array(matrix1.size) { i -> DoubleArray(matrix1[i].size) { j -> matrix1[i][j] + matrix2[i][j] } }
}

fun multiplyMatrixByAConstant() {
    val scanner = Scanner(System.`in`)
    val matrix  = readMatrix()
    print("Enter constant: ")
    val const: Double = scanner.nextDouble()
    val result = multiplyMatrixByAConstant(matrix, const)
    println("The result is:")
    printMatrix(result)
}

fun multiplyMatrices() {
    val matrix1 = readMatrix("first")
    val matrix2 = readMatrix("second")
    if (matrix1[0].size != matrix2.size) {
        println("The operation cannot be performed.")
        return
    }
    val result = multiplyMatrices(matrix1, matrix2)
    printMatrix(result)
}

fun multiplyMatrices(matrix1: Array<DoubleArray>, matrix2: Array<DoubleArray>): Array<DoubleArray> {
    val result : Array<DoubleArray> = Array(matrix1.size) { DoubleArray(matrix2[0].size) }
    for (i in matrix1.indices) {
        for (j in 0 until matrix2[0].size) {
            var sum = 0.0
            for (k in matrix2.indices) {
                sum += matrix1[i][k] * matrix2[k][j]
            }
            result[i][j] = sum
        }
    }
    return result
}

fun multiplyMatrixByAConstant(matrix: Array<DoubleArray>, constant: Double): Array<DoubleArray> {
    return Array(matrix.size) { i -> DoubleArray(matrix[i].size) { j -> matrix[i][j] * constant} }
}

fun transposeMatrix() {
    println("""
        1. Main diagonal
        2. Side diagonal
        3. Vertical line
        4. Horizontal line
    """.trimIndent())
    println("Your choice:")
    val input = readln().toInt()
    val matrix = readMatrix()
    val result = when(input) {
        1 -> transposeMatrixAlongMainDiagonal(matrix)
        2 -> transposeMatrixAlongSideDiagonal(matrix)
        3 -> transposeMatrixAlongVerticalLine(matrix)
        4 -> transposeMatrixAlongHorizontalLine(matrix)
        else -> throw IllegalArgumentException("Invalid input")
    }
    println("The result is:")
    printMatrix(result)
}

fun transposeMatrixAlongMainDiagonal(matrix: Array<DoubleArray>): Array<DoubleArray> {
    return Array(matrix.size) { i -> DoubleArray(matrix[i].size) { j -> matrix[j][i] } }
}
fun transposeMatrixAlongSideDiagonal(matrix: Array<DoubleArray>): Array<DoubleArray> {
    return Array(matrix.size) { i -> DoubleArray(matrix[i].size) { j -> matrix[matrix.size - 1 - j][matrix[i].size - 1 - i] } }
}

fun transposeMatrixAlongVerticalLine(matrix: Array<DoubleArray>): Array<DoubleArray> {
    return Array(matrix.size) { i -> DoubleArray(matrix[i].size) { j -> matrix[i][matrix[i].size - 1 - j] } }
}

fun transposeMatrixAlongHorizontalLine(matrix: Array<DoubleArray>): Array<DoubleArray> {
    return Array(matrix.size) { i -> DoubleArray(matrix[i].size) { j -> matrix[matrix.size - 1 - i][j] } }
}

fun calculateDeterminant() {
    val matrix = readMatrix()
    val result = calculateDeterminant(matrix)
    println("The result is:")
    println("$result\n")
}

fun calculateDeterminant(matrix: Array<DoubleArray>): Double {
    if (matrix.size == 1) {
        return matrix[0][0]
    }
    if (matrix.size == 2) {
        return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]
    }
    var determinant = 0.0
    for (i in matrix.indices) {
        val minor = Array(matrix.size - 1) { DoubleArray(matrix.size - 1) }
        for (j in 1 until matrix.size) {
            for (k in matrix.indices) {
                if (k < i) {
                    minor[j - 1][k] = matrix[j][k]
                } else if (k > i) {
                    minor[j - 1][k - 1] = matrix[j][k]
                }
            }
        }
        determinant += matrix[0][i] * calculateDeterminant(minor) * if (i % 2 == 0) 1 else -1
    }
    return determinant
}

fun inverseMatrix() {
    val matrix = readMatrix()
    val result = inverseMatrix(matrix)
    println("The result is:")
    printMatrix(result)
}

fun inverseMatrix(matrix: Array<DoubleArray>): Array<DoubleArray> {
    val determinant = calculateDeterminant(matrix)
    val cofactorMatrix = Array(matrix.size) { DoubleArray(matrix.size) }
    for (i in matrix.indices) {
        for (j in matrix.indices) {
            val minor = Array(matrix.size - 1) { DoubleArray(matrix.size - 1) }
            for (k in matrix.indices) {
                for (l in matrix.indices) {
                    if (k != i && l != j) {
                        minor[k - if (k > i) 1 else 0][l - if (l > j) 1 else 0] = matrix[k][l]
                    }
                }
            }
            cofactorMatrix[i][j] = (if ((i + j) % 2 == 0) 1.0 else -1.0) * calculateDeterminant(minor)
        }
    }
    val adjugateMatrix = transposeMatrixAlongMainDiagonal(cofactorMatrix)
    return multiplyMatrixByAConstant(adjugateMatrix, 1 / determinant)
}

fun readMatrix(order: String = ""): Array<DoubleArray> {
    val scanner = Scanner(System.`in`)
    print(if (order.isNotEmpty()) "Enter size of $order matrix:" else "Enter size of matrix:")
    val rows = scanner.nextInt()
    val columns = scanner.nextInt()
    println(if (order.isNotEmpty()) "Enter $order matrix:" else "Enter matrix:")
    return Array(rows) { readln().split(" ").map { it.toDouble() }.take(columns).toDoubleArray() }
}

fun printMatrix(matrix: Array<DoubleArray>) {
    matrix.forEach { row -> println(row.joinToString(" ")) }
}
