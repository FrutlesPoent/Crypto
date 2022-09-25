package elGamal

import domain.PrimeNumbers
import fastpow.FastPow
import utils.Generate
import utils.WriteFile
import utils.isPrime
import utils.pow
import java.math.BigInteger

object ElGamal {
    private const val degree = 9

    fun elGamal(message: ByteArray) {
        val messageAsByteList = message.toList()
        val pq = generatePQ()
        val g = generateG(pq)
        val bobX = Generate.generateNumber(1, pq.p - 1)
        val bobY = FastPow.pow(g, bobX, pq.p)
        val k = generateK(pq.p)

        val r = FastPow.pow(g, k, pq.p)
        val sendSecureMessage = cipher(messageAsByteList, bobY, k, pq)

        decipher(sendSecureMessage, r, pq, bobX)
    }

    private fun cipher(messageAsByteList: List<Byte>, bobY: BigInteger, k : Int, pq: PrimeNumbers): List<BigInteger> {
        val encryption = mutableListOf<Byte>()
        val sendSecureMessage = mutableListOf<BigInteger>()
        for (it in messageAsByteList) {
            var flag = false
            var num = it.toInt()
            if (num < 0) {
                num *= -1
                flag = true
            }

            var e = FastPow.pow(bobY, k, pq.p).multiply(num.toBigInteger()).mod(pq.p.toBigInteger())//
            if (flag) {
                e *= (-1).toBigInteger()
            }
            encryption.add(e.toByte())
            sendSecureMessage.add(e)
        }
        WriteFile.writeToFile(encryption, "images/elGamal/ElGamalEncryption.gif")
        return sendSecureMessage
    }

    private fun decipher(sendSecureMessage: List<BigInteger>, r: BigInteger, pq: PrimeNumbers, bobX: Int) {
        val decryption = mutableListOf<Byte>()
        for (it in sendSecureMessage) {
            var flag = false
            var num = it.toInt()

            if (num < 0) {
                num *= -1
                flag = true
            }
            var m = FastPow.pow(r, pq.p - 1 - bobX, pq.p).multiply(num.toBigInteger()).mod(pq.p.toBigInteger())

            if (flag) {
                m *= (-1).toBigInteger()
            }
            decryption.add(m.toByte())
        }
        WriteFile.writeToFile(decryption, "images/elGamal/ElGamalDecryption.gif")
    }


    private fun generateG(primeNumbers: PrimeNumbers): Int {
        var g: Int

        do {
            g = Generate.generateNumber(from = 1, to = primeNumbers.p - 1)
        } while (FastPow.pow(g.toBigInteger(), primeNumbers.q, primeNumbers.p).equals(1))

        return g
    }

    private fun generateK(p: Int): Int {
        return Generate.generateNumber(1, p - 2)
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