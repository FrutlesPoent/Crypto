package client

import fastpow.FastPow
import gcd.GCD
import utils.*

class FiatShamirClient {

    private var s: Int = 0
    private var n: Int = 0
    private var r: Int = 0

    fun registration() {
        n = ReadFile.readFileAsText(FiatShamirFileNames.fileNameN)[0].toInt()
        s = findS(n)
        val v = FastPow.pow(s, 2, n).toInt()
        WriteFile.writeToFileText(listOf(v), FiatShamirFileNames.fileNameV)
    }

    fun authorization() {
        n = ReadFile.readFileAsText(FiatShamirFileNames.fileNameN)[0].toInt()
        r = Generate.generateNumber(from = 1, to = n - 1)
        val x = FastPow.pow(r, 2, n).toInt()
        WriteFile.writeToFileText(listOf(x), FiatShamirFileNames.fileNameX)
    }


    fun calculateY() {
        val e = ReadFile.readFileAsText(FiatShamirFileNames.fileNameE)[0].toInt()
        sPow(e)
        val y = (r.toBigInteger() * s.toBigInteger()).mod(n.toBigInteger())
        WriteFile.writeToFileTextBigInteger(listOf(y), FiatShamirFileNames.fileNameY)
    }

    private fun sPow(e: Int){
        if (e == 0) {
            s = 1
        }
    }

    private fun findS(n: Int): Int {
        var s: Int

        do {
            s = Generate.generateNumber(from = 1, to = n - 1)

        } while (GCD.gcd(s, n) != 1)
        return s
    }
}