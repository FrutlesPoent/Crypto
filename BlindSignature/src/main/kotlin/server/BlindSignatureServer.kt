package server

import FileNames
import domain.PrimeNumbers
import fastpow.FastPow
import gcd.GCD
import hash.md5
import hash.md5ToByteArray
import utils.*
import java.io.File
import java.math.BigInteger

class BlindSignatureServer {

    private val degree = 4

    private var c: Int = 0
    private var N: Int = 0
    private var d: Int = 0

    private var against: Int = 0
    private var positive: Int = 0
    private var neutral: Int = 0

    private val pass = mutableSetOf<String>()

    private var alreadyVoted: Boolean = false

    fun start() {
        alreadyVoted = false
        val primeNumbers = generatePQ()
        N = (primeNumbers.q.toBigInteger() * primeNumbers.p.toBigInteger()).toInt()   /// ne mojet bit' oshibka
        val f = (primeNumbers.p - 1).toBigInteger() * (primeNumbers.q - 1).toBigInteger()
        val numbers = findD(f.toInt())
        c = numbers.first
        d = numbers.second
        WriteFile.writeToFileText(listOf(numbers.second), FileNames.serverFileNameD)
        WriteFile.writeToFileText(listOf(N), FileNames.serverFileNameN)

    }

    fun calculateS() {
        val s = mutableListOf<Int>()
        val clientMessage = ReadFile.readFileAsText(FileNames.clientMessageFileName)
        if (!pass.containsAll(clientMessage)){
            pass.addAll(clientMessage)
        } else {
            alreadyVoted = true
        }

        for (it in clientMessage) {
            s.add(FastPow.pow(it.toInt(), c, N).toInt())
        }
        WriteFile.writeToFileText(s, FileNames.serverFileNameS)
    }

    fun checkBilluten() {
        val clientSignature = ReadFile.readFileAsText(FileNames.clientSignatureFileName)
        val n = ReadFile.readFileAsText(FileNames.clientFileNamen)[0].toInt()
        val h = md5(byteArrayOf(n.toByte())).md5ToByteArray()
        var flag: Boolean = true
        for (it in 0 until clientSignature.size) {
            val tmp = FastPow.pow(clientSignature[it].toInt(), d, N).toInt()
            if (h[it].toInt() != tmp){
                flag = false
            }
        }
        if (flag && !alreadyVoted) {
            when(n shr 16) {
                1 -> positive += 1
                0 -> against += 1
                else -> neutral += 1
            }
        }
    }

    fun printResults(){
        println("za : $positive")
        println("protiv : $against")
        println("terpila : $neutral")
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
}