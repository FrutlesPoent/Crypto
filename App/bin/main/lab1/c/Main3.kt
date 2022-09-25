package lab1.c

import diffyhellman.DiffyHellman
import utils.Generate

fun main() {

    val x = Generate.generateNumber()
    val y = Generate.generateNumber()

    println("X: $x")
    println("Y: $y")
    val result = DiffyHellman.diffyHellman(x, y)

    println(result.toString())

}
