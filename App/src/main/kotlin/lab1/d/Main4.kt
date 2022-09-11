package lab1.d

import babystep.BabyStep
import utils.Generate
import utils.Generate.pow

fun main() {

//    println(BabyStep.run(a = 2, p = 101, y = 47))
//    print(BabyStep.run(a = 5, p = 7, y = 4))
    val p = Generate.generatePrime(10.pow(4))
    val a = Generate.generateNumber(p)

    val y = Generate.generateNumber(p)

    println("A: $a \n P: $p \n Y: $y\n")
    println(BabyStep.run(a = a, p = p, y = y))
}