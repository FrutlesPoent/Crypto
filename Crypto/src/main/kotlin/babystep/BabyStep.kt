package babystep

import fastpow.FastPow
import kotlin.math.sqrt

object BabyStep {

    fun run(a: Int, y: Int, p: Int): List<Int> {
        val m = sqrt(p.toDouble()).toInt()
        var k: Int = m + 1

        while (k * m < p){
            k += 1
        }

        val babyStep = babyStep(a, y, p, m)
        val bigStep = bigStep(a, p, k, m)

        //k = m + 1
        //km > p
        //k = m  2k > p

        return findEqualNumbers(babyStep, bigStep, m)
    }


    private fun babyStep(a: Int, y: Int, p: Int, m: Int): List<Int> {
        val babyList = mutableListOf<Int>()
        for (i in 0 until m) {
            val sum = FastPow.pow(a, i, p).toInt()
            //(y*a^i)%p  =
            babyList.add((y % p) * (sum) % p)
            //(ab)%c =  (a%c * b%c)%c
        }
        return babyList
    }


    fun bigStep(a: Int, p: Int, k: Int, m: Int): List<Int> {
        val bigList = mutableListOf<Int>()
        for (i in 1 until k + 1) {
            bigList.add(FastPow.pow(a, i * m, p).toInt())
        }
        return bigList
    }
    //x = im-j

    fun findEqualNumbers(babyList: List<Int>, bigList: List<Int>, m: Int): List<Int> {
        val dictionary = mutableMapOf<Int, Int>()
        val xList = mutableListOf<Int>()

        for (i in babyList.indices) {
            dictionary[babyList[i]] = i
        }

        for (i in bigList.indices) {
            if (dictionary.containsKey(bigList[i])) {
                val temp = dictionary[bigList[i]]
                val x = (i + 1) * m - temp!!
                xList.add(x)
            }
        }

        return xList
    }
}