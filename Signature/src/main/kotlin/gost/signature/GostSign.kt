package gost.signature

import domain.Numbers
import fastpow.FastPow
import gcd.GCD
import hashfunction.md5
import hashfunction.md5ToByteArray
import utils.*

object GostSign {

    const val filename = "signatureMessages/gostMain.bin"
    const val filenameAddition = "signatureMessages/gostR"

    fun gostSign(message: ByteArray) {
        val pqb = generatePQB()
        val a = generateA(pqb)
        val x = Generate.generateNumber(pqb.q)
        val y = FastPow.pow(a, x, pqb.p)
        signMessage(message, pqb.q, a, pqb.p, x)
        val readFirst = ReadFile.readFileAsText(filename)
        val sList = mutableListOf<Int>()
        readFirst.forEach {
            sList.add(it.toInt())
        }
        val readSecond = ReadFile.readFileAsText(filenameAddition)
        val r = readSecond[0].toInt()
        val s: Pair<List<Int>, Int> = Pair(sList, r)
        println(checkSign(message, s, pqb, a, y.toInt()))

    }

    private fun signMessage(message: ByteArray, q: Int, a: Int, p: Int, x: Int): Pair<List<Int>, Int> {
        val h = md5(message).md5ToByteArray()
        var k: Int
        var r: Int
        val s = mutableListOf<Int>()
        do {
            var flag = true
            do {
                k = Generate.generateNumber(q)
                r = FastPow.pow(a, k, p).toInt().mod(q)
            } while (r == 0)

            for (it in 0 until h.size) {
                val tmp = (k * h[it].toInt() + x * r).mod(q)
                if (tmp == 0) {
                    flag = false
                }
                s.add(tmp)
            }
        } while (!flag)

        WriteFile.writeToFileText(s, "signatureMessages/gostMain.bin")
        WriteFile.writeToFileText(listOf(r), "signatureMessages/gostR")

        return Pair(s, r)
    }

    private fun checkSign(message: ByteArray, sr: Pair<List<Int>, Int>, pqb: Numbers, a: Int, y: Int): Boolean {
        val h = md5(message).md5ToByteArray()
        if (sr.second > pqb.q)
            return false
        for (it in 0 until sr.first.size) {
            if (sr.first[it] > pqb.q){
                return false
            }
        }

        for (it in 0 until sr.first.size) {
            val u1 = divWithPow(sr.first[it], inverseModuleNumber(h[it].toInt(), pqb.q), pqb.q)
            val u2 = divWithPow((sr.second * (-1)), inverseModuleNumber(h[it].toInt(), pqb.q), pqb.q)
            val v = ((FastPow.pow(a, u1, pqb.p) * FastPow.pow(y, u2, pqb.p)).mod(pqb.p.toBigInteger())).mod(pqb.q.toBigInteger())
            if (v != sr.second.toBigInteger()){
                return false
            }
        }

        return true
    }

    private fun generatePQB(): Numbers {
        var q: Int
        var p: Int
        var b: Int
        do {
            q = Generate.generateNumber(from = 2.pow(14), to = (2.pow(15) - 1))
        } while (!q.isPrime())

        do {
            b = Generate.generateNumber(from = 2.pow(14), to = (2.pow(15) - 1))
            p = (b * q) + 1
        } while (!p.isPrime())

        return Numbers(p, q, b)
    }

    private fun generateA(pqb: Numbers): Int {
        var a: Int

        var g: Int
        do {
            g = Generate.generateNumber(from = 1, to = pqb.p - 1)
            a = FastPow.pow(g.toBigInteger(), pqb.b, pqb.p).toInt()
        } while (a <= 1)

        return a
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