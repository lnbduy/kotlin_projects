package machine


enum class Coffee(val water: Int, val milk: Int, val beans: Int, val cost: Int, val displayName: String) {
    ESPRESSO(250, 0, 16, 4, "espresso"),
    LATTE(350, 75, 20, 7, "latte"),
    CAPPUCCINO(200, 100, 12, 6, "cappuccino"),
}


class CoffeeMachine(var water: Int, var milk: Int, var beans: Int, var disposableCups: Int, var money: Int) {
    enum class State { MAIN, BUY, FILL_WATER, FILL_MILK, FILL_BEANS, FILL_CUPS, OFF }

    private var state = State.MAIN

    fun getPrompt(): String {
        return when (state) {
            State.MAIN -> "Write action (buy, fill, take, remaining, exit): "
            State.BUY -> "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: "
            State.FILL_WATER -> "Write how many ml of water do you want to add: "
            State.FILL_MILK -> "Write how many ml of milk do you want to add: "
            State.FILL_BEANS -> "Write how many grams of coffee beans do you want to add: "
            State.FILL_CUPS -> "Write how many disposable cups of coffee do you want to add: "
            State.OFF -> ""
        }
    }

    private fun buy(coffee: Coffee): String {
        return when {
            water < coffee.water -> "Sorry, not enough water!"
            milk < coffee.milk -> "Sorry, not enough milk!"
            beans < coffee.beans -> "Sorry, not enough coffee beans!"
            disposableCups < 1 -> "Sorry, not enough disposable cups!"
            else -> {
                water -= coffee.water
                milk -= coffee.milk
                beans -= coffee.beans
                disposableCups -= 1
                money += coffee.cost
                "I have enough resources, making you a coffee!"
            }
        }
    }

    private fun getStatus(): String {
        return """
            The coffee machine has:
            $water ml of water
            $milk ml of milk
            $beans g of coffee beans
            $disposableCups disposable cups
            ${'$'}$money of money
        """.trimIndent()
    }

    fun isRunning(): Boolean = state != State.OFF

    fun process(input: String): String {
        when (state) {
            State.MAIN -> when (input) {
                "buy" -> {
                    state = State.BUY
                }

                "fill" -> {
                    state = State.FILL_WATER
                }

                "take" -> {
                    val output = "I gave you $${money}"
                    money = 0
                    return output
                }

                "remaining" -> {
                    return getStatus()
                }

                "exit" -> {
                    state = State.OFF
                }

                else -> return "Invalid Input"
            }

            State.BUY -> {
                if (input != "back") {
                    var coffee = when (input) {
                        "1" -> Coffee.ESPRESSO
                        "2" -> Coffee.LATTE
                        "3" -> Coffee.CAPPUCCINO
                        else -> throw IllegalArgumentException("Invalid input")
                    }
                    buy(coffee)
                }
                state = State.MAIN
            }

            State.FILL_WATER -> {
                water += input.toInt()
                state = State.FILL_MILK
            }

            State.FILL_MILK -> {
                milk += input.toInt()
                state = State.FILL_BEANS
            }

            State.FILL_BEANS -> {
                beans += input.toInt()
                state = State.FILL_CUPS
            }

            State.FILL_CUPS -> {
                disposableCups += input.toInt()
                state = State.MAIN
            }

            State.OFF -> check(false)
        }
        return ""
    }
}