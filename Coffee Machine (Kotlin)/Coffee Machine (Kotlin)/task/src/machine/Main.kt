package machine

fun main() {
    val machine = CoffeeMachine(water = 400, milk = 540, beans = 120, disposableCups = 9, money = 550)
    while (machine.isRunning()) {
        println(machine.getPrompt())
        val input = readln()
        println(machine.process(input))
    }
}




