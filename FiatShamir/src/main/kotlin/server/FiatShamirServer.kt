package server

import domain.PrimeNumbers
import utils.*
import java.math.BigInteger

class FiatShamirServer {

    private val degree = 4
    private var e: Int = 0
    private var n: Int = 0

    fun start() {
        val pq = generatePQ()
        n = (pq.p.toBigInteger() * pq.q.toBigInteger()).toInt()

        WriteFile.writeToFileText(listOf(n), FiatShamirFileNames.fileNameN)
    }

    fun generateE() {
        e = Generate.generateNumber(from = 0, to = 2)
        WriteFile.writeToFileText(listOf(e), FiatShamirFileNames.fileNameE)
    }

    fun validate(): Boolean {
        val y = ReadFile.readFileAsText(FiatShamirFileNames.fileNameY)[0].toBigInteger()
        var v = ReadFile.readFileAsText(FiatShamirFileNames.fileNameV)[0].toBigInteger()
        val x = ReadFile.readFileAsText(FiatShamirFileNames.fileNameX)[0].toBigInteger()
        v = checkE(v)
        val y2 = (x * v).mod(n.toBigInteger())
        val yn: BigInteger = (y * y).mod(n.toBigInteger())
        if (yn == y2) {
            return true
        }
        return false
    }

    private fun checkE(v: BigInteger): BigInteger {
        if (e == 0) {
            return BigInteger("1")
        }
        return v
    }

    private fun generatePQ(): PrimeNumbers {
        val until = 10.pow(degree)

        val q = Generate.generatePrime(until)
        val p = Generate.generatePrime(until)

        return PrimeNumbers(p, q)
    }
}