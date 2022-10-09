package client

import FileNames
import fastpow.FastPow
import gcd.GCD
import hash.md5
import hash.md5ToByteArray
import utils.*
import utils.Generate.generatePrime

class BlindSignatureClient {

    private val degree = 16
    private var N: Int = 0
    private var r: Int = 0
    
    fun start() {
        val rnd = Generate.generateNumber(2.pow(degree) - 1)
        val v = selectAnswer()
        val readN = ReadFile.readFileAsText(FileNames.serverFileNameN)
        N = readN[0].toInt()
        val readD = ReadFile.readFileAsText(FileNames.serverFileNameD)
        val d = readD[0].toInt()
        r = generateMutualPrime(N)
        val n = rnd or (v shl 16)
        WriteFile.writeToFileText(listOf(n), FileNames.clientFileNamen)
        val h = md5(byteArrayOf(n.toByte())).md5ToByteArray()
        val message = mutableListOf<Int>()
        for (it in h) {
            message.add((it.toInt().toBigInteger() * FastPow.pow(r, d, N)).mod(N.toBigInteger()).toInt())
        }

        WriteFile.writeToFileText(message, FileNames.clientMessageFileName)
    }

    private fun selectAnswer(): Int {
        val tmp = Generate.generateNumber() % 3
        return if (tmp == 0) {
            1
        } else if (tmp == 1) {
            2
        } else {
            0
        }
    }

    fun calculateOwnS() {
        val serverMessage = ReadFile.readFileAsText(FileNames.serverFileNameS)
        val ownS = mutableListOf<Int>()
        for (it in serverMessage) {
            ownS.add(
                (it.toInt().toBigInteger() * inverseModuleNumber(r, N).toBigInteger()).mod(N.toBigInteger()).toInt()
            )
        }

        WriteFile.writeToFileText(ownS, FileNames.clientSignatureFileName)
    }

    private fun generateMutualPrime(firstPrime: Int): Int {

        var prime: Int = 0
        do {
            prime = generatePrime(10.pow(9))
        } while (!prime.isPrime() && GCD.gcd(firstPrime, prime) != 1)

        return prime
    }

    private fun inverseModuleNumber(number: Int, mod: Int): Int {
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
}