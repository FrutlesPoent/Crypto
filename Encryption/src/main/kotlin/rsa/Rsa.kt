package rsa

import domain.PrimeNumbers
import domain.SecretNumbers
import fastpow.FastPow
import gcd.GCD
import utils.Generate
import utils.WriteFile
import utils.isPrime
import utils.pow
import vernam.Vernam
import java.math.BigInteger

object Rsa {

    private const val degree = 3

    fun rsa(message: ByteArray) {
        val primeNumbers = generatePQ()
        val bobN = primeNumbers.q.toBigInteger() * primeNumbers.p.toBigInteger()
        val bobF = (primeNumbers.p - 1).toBigInteger() * (primeNumbers.q - 1).toBigInteger()
        val numbers = findD(bobF.toInt())
        val secureMessage = encrypt(message, numbers.second, bobN.toInt())
        decrypt(secureMessage, numbers.first, bobN.toInt())

    }

    private fun encrypt(message: ByteArray, d: Int, bobN: Int): List<BigInteger> {
        val sendSecureMessage = mutableListOf<BigInteger>()
        val encryptionList = mutableListOf<Byte>()
        for (it in message) {
            var flag = false
            var num = it.toInt()
            if (num < 0) {
                num *= -1
                flag = true
            }
            var e = FastPow.pow(num, d, bobN.toInt())
            if (flag) {
                e *= (-1).toBigInteger()
            }
            encryptionList.add(e.toByte())
            sendSecureMessage.add(e)
        }

        WriteFile.writeToFile(encryptionList, "images/Rsa/RSAEncryption.png")
        return sendSecureMessage
    }

    private fun decrypt(secureMessage: List<BigInteger>, c: Int, bobN: Int) {
        val decryptionList = mutableListOf<Byte>()
        for (it in secureMessage) {
            var flag = false
            var num = it.toInt()

            if (num < 0) {
                num *= -1
                flag = true
            }
            var m = FastPow.pow(num, c, bobN)
            if (flag) {
                m *= (-1).toBigInteger()
            }
            decryptionList.add(m.toByte())
        }

        WriteFile.writeToFile(decryptionList, "images/Rsa/RSADecryption.png")
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