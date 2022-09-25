package diffyhellman

import domain.Keys
import domain.PrimeNumbers
import fastpow.FastPow
import utils.Generate
import utils.Generate.isPrime
import utils.Generate.pow

object DiffyHellman {

    fun diffyHellman(x: Int, y: Int): Keys {
        val primeNumbers = generatePQ()
        val g = generateG(primeNumbers)

        val y1 = FastPow.pow(g, y, primeNumbers.p)
        val y2 = FastPow.pow(g, x, primeNumbers.p)

        println("P : ${primeNumbers.p}")
        println("Q : ${primeNumbers.q}")
        println("G : $g")
        println("Y1 : $y1")
        println("Y2 : $y2")

        return Keys(
            encrypted = FastPow.pow(y2, y, primeNumbers.p),
            decrypted = FastPow.pow(y1, x, primeNumbers.p),
        )
    }

    private fun generateG(primeNumbers: PrimeNumbers): Int {
        var g: Int

        do {
            g = Generate.generateNumber(from = 1, to = primeNumbers.p - 1)
        } while (FastPow.pow(g.toBigInteger(), primeNumbers.q, primeNumbers.p).equals(1))

        return g
    }
    // 1<g<p-1
    // g^q % p != 1

    private fun generatePQ(): PrimeNumbers {
        var q: Int
        var p: Int
        val until = 10.pow(8)

        do {
            q = Generate.generatePrime(until)
            p = 2 * q + 1
        } while (!q.isPrime() || !p.isPrime())

        return PrimeNumbers(p, q)
    }
}