package lab1.a

import fastpow.FastPow
import utils.Generate

fun main() {

//    println("Input a, x and p")
//    val a = readLine()?.toInt()
//    val x = readLine()?.toInt()
//    val p = readLine()?.toInt()

    val a = Generate.generateNumber()
    val x = Generate.generateNumber()
    val p = Generate.generateNumber()

    println("Number: $a")
    println("Degree: $x")
    println("Mod: $p")

    println("Result: ${FastPow.pow(a, x, p)}")
}