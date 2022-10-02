package rsa.signature

import domain.PrimeNumbers
import gcd.GCD
import domain.SecretNumbers
import fastpow.FastPow
import hashfunction.md5
import hashfunction.md5ToByteArray
import utils.*
import java.math.BigInteger

object RSASign {

    private const val degree = 3

    fun rsaSign(message: ByteArray) {
        val primeNumbers = generatePQ()
        val aliceN = primeNumbers.q.toBigInteger() * primeNumbers.p.toBigInteger()
        val aliceF = (primeNumbers.p - 1).toBigInteger() * (primeNumbers.q - 1).toBigInteger()
        val numbers = findD(aliceF.toInt())
        signMessage(message, numbers.first, aliceN.toInt())
        val readMessageCode = ReadFile.readFileAsText("test.bin")
        val readMessageCodeInt = mutableListOf<Int>()
        readMessageCode.forEach {
            readMessageCodeInt.add(it.toInt())
        }
        println(checkSign(message, numbers.second, aliceN.toInt(), readMessageCodeInt))
    }

    private fun signMessage(message: ByteArray, c: Int, N: Int) {
        val y = md5(message).md5ToByteArray()
        val s = mutableListOf<Int>()
        for (it in 0 until y.size) {
            s.add(FastPow.pow(y[it].toInt(), c, N).toInt())
        }

        WriteFile.writeToFileText(s, "test.bin")
    }

    private fun checkSign(message: ByteArray, d: Int, N: Int, s: List<Int>): Boolean {
        var w: Int
        val y = md5(message).md5ToByteArray()
        for (it in 0 until y.size) {
            w = FastPow.pow(s[it], d, N).toInt()
            if (w != y[it].toInt()) {
                return false
            }
        }
        return true
    }

    private fun findD(bobF: Int): Pair<Int, Int> {
        var d: Int
        do {
            d = Generate.generateNumber(0, bobF)

        } while (GCD.gcd(d, bobF) != 1)

        val temp = GCD.uniteGcd(d, bobF)
        var c: Int
        c = temp.x
        while (c < 0) {
            c += bobF
        }
        if (divWithPow(d, c, bobF) == 1) {
            return Pair(c, d)
        } else {
            c = temp.y
            while (c < 0) {
                c += bobF
            }
            return Pair(c, d)
        }
    }

    private fun divWithPow(num1: Int, num2: Int, mod: Int): Int {
        val c = num1.toBigInteger()
        val d = num2.toBigInteger()
        val multiplyResult = (c.mod((mod).toBigInteger())) * (d.mod((mod).toBigInteger()))

        return multiplyResult.mod((mod).toBigInteger()).toInt()
    }

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

    private fun generateSecretNumbers(prime: Int): SecretNumbers {
        var c: Int
        do {
            c = Generate.generateNumber(10.pow(degree))
        } while (GCD.gcd(c, prime - 1) != 1)

        val temp = GCD.uniteGcd(c, prime - 1)
        var d: Int
        d = temp.x
        while (d < 0) {
            d += prime - 1
        }
        if (divWithPow(d, c, prime - 1) == 1) {
            return SecretNumbers(c, d)
        } else {
            d = temp.y
            while (d < 0) {
                d += prime - 1
            }
            return SecretNumbers(c, d)
        }
    }
}