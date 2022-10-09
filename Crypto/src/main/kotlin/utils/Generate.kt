package utils

import gcd.GCD
import java.math.BigInteger
import kotlin.math.sqrt
import kotlin.random.Random

object Generate {

    private const val MIN = 0
    private const val MAX = 1000000000

    fun generateNumber(): Int =
        Random.nextInt(MIN, MAX)

    fun generateNumber(from: Int, to: Int): Int =
        Random.nextInt(from, to)

    fun generateNumber(from: Long, to: Long): Long =
        Random.nextLong(from, to)

    fun generateBytes(): Int =
        Random.nextBits(8)

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
    for (i in 2..sqrt(mutableNumber.toDouble()).toInt()) {
        if (mutableNumber % i == 0) {
            return false
        }
    }
    return true
}

fun Long.isPrime(): Boolean {
    val mutableNumber = this
    for (i in 2..sqrt(mutableNumber.toDouble()).toInt()) {
        if (mutableNumber.mod(i) == 0)
            return false
    }
    return true
}

fun Int.pow(x: Int): Int = (2..x).fold(this) { r, _ -> r * this }