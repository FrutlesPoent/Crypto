package utils

import kotlin.random.Random

object Generate {

    private const val MIN = 0
    private const val MAX = 1000000000

    fun generateNumber(): Int =
        Random.nextInt(MIN, MAX)

    fun generateNumber(from: Int, to: Int): Int =
        Random.nextInt(from, to)

    fun generateNumber(until: Int): Int =
        Random.nextInt(0, until)

    fun generatePrime(until: Int): Int {

        var number: Int

        do {
            number = generateNumber(until)
        } while (!number.isPrime())

        return number
    }
}
fun Int.isPrime(): Boolean {
    val mutableNumber = this
    for (i in 2..mutableNumber / 2) {
        if (mutableNumber % i == 0) {
            return false
        }
    }
    return true
}

fun Int.pow(x: Int): Int = (2..x).fold(this) { r, _ -> r * this }