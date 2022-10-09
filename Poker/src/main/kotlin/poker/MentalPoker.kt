package poker

import domain.Cards
import domain.PrimeNumbers
import fastpow.FastPow
import gcd.GCD
import rsa.Rsa
import utils.Generate
import utils.isPrime
import utils.pow
import java.util.Vector

object MentalPoker {

    private val listOfCards = mutableListOf<Cards>()

    var c = mutableListOf<Int>()
    var d = mutableListOf<Int>()
    var pq = generatePQ()

    fun poker(playerCount: Int = 2) {
        fillListOfCards()
        var table = Vector<Vector<Int>>()
        var playerCards = Vector<Vector<Int>>()

        playerCards.ensureCapacity(playerCount)

        for (it in 0 until  playerCount) {
            val cd = findD(pq.p - 1)
            c.add(cd.first)
            d.add(cd.second)
            playerCards.add(Vector<Int>())
        }
        for(it in 0 .. playerCount){
            table.add(Vector<Int>())
        }
        table.ensureCapacity(playerCount + 1)

        for (it in listOfCards) {
            table[0].add(it.count)
        }

        for (i in 1..playerCount) {
            for (j in 0 until listOfCards.size) {
                table[i].add(FastPow.pow(table[i - 1][j], c[i - 1], pq.p).toInt())
            }
            table[i].shuffle()
        }
        var tableCards = mutableListOf<Int>()
        for (i in 0 until playerCount) {
            for (j in 0 until 2) {
                playerCards[i].add(table[playerCount][i * 2 + j])
            }
        }
        for (i in 0 until 2){
            tableCards.add(table[playerCount][playerCount* 2+ i])
        }

        for (i in 0 until playerCount){
            for (j in 0 until 2){
                for (k in 0 until playerCount){
                    if (i != k){
                        playerCards[i][j] = FastPow.pow(playerCards[i][j], d[k], pq.p).toInt()
                    }
                }
                playerCards[i][j] = FastPow.pow(playerCards[i][j], d[i], pq.p).toInt()
            }
        }
        for(j in 0 until tableCards.size){
            for (i in 0 until playerCount){
                tableCards[j] = FastPow.pow(tableCards[j], d[i], pq.p).toInt()
            }
        }
        var count = 0
        for (i in 0 until playerCount){
            println("player$count cards: ${playerCards[i][0]}, ${playerCards[i][1]}")
            count++
        }

        print("table cards: ")
        for (it in tableCards){
            print("$it  ")
        }
        println("")

    }

    private fun fillListOfCards() {
        listOfCards.add(Cards(2, "2S"))
        listOfCards.add(Cards(3, "3S"))
        listOfCards.add(Cards(4, "4S"))
        listOfCards.add(Cards(5, "5S"))
        listOfCards.add(Cards(6, "6S"))
        listOfCards.add(Cards(7, "7S"))
    }

    private fun findD(p: Int): Pair<Int, Int> {
        var d: Int
        do {
            d = Generate.generateNumber(0, p)

        } while (GCD.gcd(d, p) != 1)

        val temp = GCD.uniteGcd(d, p)
        var c: Int
        c = temp.x
        while (c < 0) {
            c += p
        }
        if (divWithPow(d, c, p) == 1) {
            return Pair(c, d)
        } else {
            c = temp.y
            while (c < 0) {
                c += p
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

    private fun generatePQ(): PrimeNumbers {
        var q: Int
        var p: Int
        val until = 10.pow(4)

        do {
            q = Generate.generatePrime(until)
            p = 2 * q + 1
        } while (!q.isPrime() || !p.isPrime())

        return PrimeNumbers(p, q)
    }


}