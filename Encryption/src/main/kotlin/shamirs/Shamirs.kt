package shamirs

import domain.SecretNumbers
import fastpow.FastPow
import gcd.GCD
import utils.Generate
import utils.Generate.generatePrime
import utils.WriteFile
import utils.pow

object Shamirs {

    private const val degree = 9

    fun cipher(message: ByteArray) {
        val aPrime = generatePrime(10.pow(degree))
        val alice = generateSecretNumbers(aPrime)
        val bob = generateSecretNumbers(aPrime)

        val messageAsByteList = message.toList()
        val decryption = mutableListOf<Byte>()
        val encryption = mutableListOf<Byte>()
        for (it in messageAsByteList) {
            var flag = false
            var num = it.toInt()
            if (num < 0) {
                num *= -1
                flag = true
            }
            val x1 = FastPow.pow(num, alice.c, aPrime)
            val x2 = FastPow.pow(x1, bob.c, aPrime)
            encryption.add(x2.toByte())
            val x3 = FastPow.pow(x2, alice.d, aPrime)
            var x4 = FastPow.pow(x3, bob.d, aPrime)

            if (flag) {
                x4 *= (-1).toBigInteger()
            }
            decryption.add(x4.toByte())
        }

        WriteFile.writeToFile(encryption, "images/Shamir/ShamirEncryption.png")
        WriteFile.writeToFile(decryption, "images/Shamir/ShamirDecryption.png")
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

//    private fun generateSecretNumbers(prime: Int): SecretNumbers {
//        var c: Int
//        var d: Int
//        do {
//            c = Generate.generateNumber(until = 10.pow(degree))
//            d = Generate.generateNumber(until = 10.pow(degree))
//        } while (divWithPow(c, d, prime - 1) != 1)
//
//        return SecretNumbers(c, d)
//    }

    private fun divWithPow(num1: Int, num2: Int, mod: Int): Int {
        val c = num1.toBigInteger()
        val d = num2.toBigInteger()
        val multiplyResult = (c.mod((mod).toBigInteger())) * (d.mod((mod).toBigInteger()))

        return multiplyResult.mod((mod).toBigInteger()).toInt()
    }
}