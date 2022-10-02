package el.gamal.signature

import domain.PrimeNumbers
import fastpow.FastPow
import gcd.GCD
import hashfunction.md5
import hashfunction.md5ToByteArray
import utils.*
import java.math.BigInteger

object ElGamalSign {

    private const val degree = 6
    private const val fileName = "signatureMessages/elgamal.bin"

    fun elGamalSign(message: ByteArray) {
        val pq = generatePQ()
        val g = generateG(pq)
        val aliceX = Generate.generateNumber(1, pq.p - 1)
        val aliceY = FastPow.pow(g, aliceX, pq.p)
        val k = generateK(pq.p)
        val r = FastPow.pow(g, k, pq.p)
        signMessage(message, aliceX, r.toInt(), k, pq.p - 1)
        val signMessageString = ReadFile.readFileAsText(fileName)
        val signMessageInt = mutableListOf<Int>()
        signMessageString.forEach {
            signMessageInt.add(it.toInt())
        }
        println(checkSign(message, r.toInt(), signMessageInt, g, pq.p, aliceY.toInt()))
    }

    private fun signMessage(message: ByteArray, x: Int, r: Int, k: Int, mod: Int): List<Int> {
        val h = md5(message).md5ToByteArray()
        val s = mutableListOf<Int>()
        for (it in 0 until h.size) {

            val u = (h[it].toInt().toBigInteger().minus(x.toBigInteger() * r.toBigInteger())).mod(mod.toBigInteger())
            val result = (inverseModuleNumber(k, mod).toBigInteger() * u).mod(mod.toBigInteger())
            s.add(result.toInt())
        }

        WriteFile.writeToFileText(s, fileName)
        return s
    }

    //x         r    h  mod
    //1277725 114822 53 1733278

    private fun checkSign(message: ByteArray, r: Int, s: List<Int>, g: Int, p: Int, y: Int): Boolean {
        val h = md5(message).md5ToByteArray()
        for (it in 0 until h.size) {
            val tmp = divWithPow(FastPow.pow(y, r, p), FastPow.pow(r, s[it], p), p.toBigInteger())
            if (tmp != FastPow.pow(g, h[it].toInt(), p).toInt()) {
                return false
            }
        }
        return true
    }

    fun inverseModuleNumber(number: Int, mod: Int): Int {
        val temp = GCD.uniteGcd(number, mod)
        var c: Int
        c = temp.x
        while (c < 0) {
            c += mod
        }
        if (divWithPow(number, c, mod) == 1) {
            return c
        } else {
            c = temp.y
            while (c < 0) {
                c += mod
            }
            return c
        }
    }

    private fun divWithPow(num1: Int, num2: Int, mod: Int): Int {
        val c = num1.toBigInteger()
        val d = num2.toBigInteger()
        val multiplyResult = (c.mod((mod).toBigInteger())) * (d.mod((mod).toBigInteger()))

        return multiplyResult.mod((mod).toBigInteger()).toInt()
    }

    //142 126 179
    private fun divWithPow(num1: BigInteger, num2: BigInteger, mod: BigInteger): Int {
        val c = num1
        val d = num2
        val multiplyResult = (c.mod((mod))) * (d.mod((mod)))

        return multiplyResult.mod((mod)).toInt()
    }

    private fun generateG(primeNumbers: PrimeNumbers): Int {
        var g: Int

        do {
            g = Generate.generateNumber(from = 1, to = primeNumbers.p - 1)
        } while (FastPow.pow(g.toBigInteger(), primeNumbers.q, primeNumbers.p).equals(1))

        return g
    }

    private fun generateK(p: Int): Int {
        var k: Int
        do {
            k = Generate.generateNumber(1, p - 1)
        } while (GCD.gcd(k, p - 1) != 1)
        return k
    }

    // 1<g<p-1
    // g^q % p != 1
    private fun generatePQ(): PrimeNumbers {
        var q: Int
        var p: Int
        val until = 10.pow(degree)
        do {
            q = Generate.generatePrime(until)
            p = 2 * q + 1
        } while (!q.isPrime() || !p.isPrime())

        return PrimeNumbers(p, q)
    }
}