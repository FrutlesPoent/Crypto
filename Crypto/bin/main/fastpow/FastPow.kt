package fastpow

import java.math.BigInteger

object FastPow {

    fun pow(number: Int, degree: Int, mod: Int): BigInteger {
        val listOfNumbers = calculateDegree(divideDegree(degree), mod, number).reversed()
        val numberInBinaryList = mutableListOf<Int>()
        (Integer.toBinaryString(degree)).toCharArray().forEach { numberInBinaryList.add(it.digitToInt()) }

        var result = BigInteger("1")
        for (i in 0 until numberInBinaryList.size) {
            if (numberInBinaryList[i] == 1) {
                result *= listOfNumbers[i]
            }
        }

        return result.mod(mod.toBigInteger())
    }

    fun pow(number: BigInteger, degree: Int, mod: Int): BigInteger {
        val listOfNumbers = calculateDegree(divideDegree(degree), mod, number.toInt()).reversed()
        val numberInBinaryList = mutableListOf<Int>()
        (Integer.toBinaryString(degree)).toCharArray().forEach { numberInBinaryList.add(it.digitToInt()) }

        var result = BigInteger("1")
        for (i in 0 until numberInBinaryList.size) {
            if (numberInBinaryList[i] == 1) {
                result *= listOfNumbers[i]
            }
        }

        return result.mod(mod.toBigInteger())
    }

    private fun divideDegree(degree: Int): Int {
        var result = 1
        var degreeCounter = 1
        if (degree == 1) return 1
        do {
            degreeCounter *= 2
            result += 1
        } while (degreeCounter * 2 <= degree)

        return result
    }

    private fun calculateDegree(degreeCount: Int, mod: Int, number: Int): List<BigInteger> {
        var previousStep: BigInteger = number.mod(mod).toBigInteger()
        val resultList = mutableListOf<BigInteger>()
        resultList.add(previousStep)
        var counter = 1
        while (counter < degreeCount) {
            val currentStep: BigInteger = (previousStep * previousStep).mod(mod.toBigInteger())
            resultList.add(currentStep)
            previousStep = currentStep
            counter += 1
        }
        return resultList
    }
}