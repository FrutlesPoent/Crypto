package shamirs

import domain.SecretNumbers
import fastpow.FastPow
import utils.Generate
import utils.Generate.generatePrime
import utils.WriteFile
import utils.pow
import java.math.BigInteger

object Shamirs {

    const val degree = 4
    fun cipher(message: ByteArray) {
        val aPrime = generatePrime(10.pow(degree))
        val alice = generateSecretNumbers(aPrime)
        val bob = generateSecretNumbers(aPrime)

//        val x1 = FastPow.pow(10, alice.c, aPrime)
//        val x2 = FastPow.pow(x1, bob.c, aPrime)
//        val x3 = FastPow.pow(x2, alice.d, aPrime)
//        val x4 = FastPow.pow(x3, bob.d, aPrime)
//
        val arr = mutableListOf<Byte>()
        for (it in message) {
            val x1 = FastPow.pow(it.toInt(), alice.c, aPrime)
            val x2 = FastPow.pow(x1, bob.c, aPrime)
            val x3 = FastPow.pow(x2, alice.d, aPrime)
            val x4 = FastPow.pow(x3, bob.d, aPrime)
            arr.add(x4.toByte())
////            var x = FastPow.pow(it.toInt(), alice.c, aPrime)
////            x = FastPow.pow(x, bob.c, aPrime)
////            encryptionList.add(FastPow.pow(x, alice.d, aPrime))
        }

        WriteFile.writeToFile(arr)

    }

    private fun generateSecretNumbers(prime: Int): SecretNumbers {
        var c: Int
        var d: Int
        do {
            c = Generate.generateNumber(until = 10.pow(degree))
            d = Generate.generateNumber(until = 10.pow(degree))
        } while ((c * d).mod(prime - 1) != 1)

        return SecretNumbers(c, d)
    }
}