package lab1.b

import gcd.GCD
import utils.Generate

fun main() {

    val a = Generate.generateNumber()
    val b = Generate.generateNumber(a)

    println("A: $a")
    println("B: $b")

    println(GCD.uniteGcd(a, b).toString())
}
