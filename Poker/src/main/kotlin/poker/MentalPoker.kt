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

    fun poker(playerCount: Int = 6) {
        val listOfCards = fillListOfCards()
        val c = mutableListOf<Int>()
        val d = mutableListOf<Int>()
        val pq = generatePQ()
        val table = Vector<Vector<Int>>()
        val playerCards = Vector<Vector<Int>>()

        for (it in 0 until playerCount) {
            val cd = findD(pq.p - 1)
            c.add(cd.first)
            d.add(cd.second)
            playerCards.add(Vector<Int>())
        }
        for (it in 0..playerCount) {
            table.add(Vector<Int>())
        }

        for (it in listOfCards) {
            table[0].add(it.count)
        }

        for (i in 1..playerCount) {
            for (j in 0 until listOfCards.size) {
                table[i].add(FastPow.pow(table[i - 1][j], c[i - 1], pq.p).toInt())
            }
            table[i].shuffle()
        }

        val tableCards = mutableListOf<Int>()

        for (i in 0 until playerCount) {
            for (j in 0 until 2) {
                playerCards[i].add(table[playerCount][i * 2 + j])
            }
        }

        for (i in 0 until 5) {
            tableCards.add(table[playerCount][playerCount * 2 + i])
        }

        for (i in 0 until playerCount) {
            for (j in 0 until 2) {
                for (k in 0 until playerCount) {
                    if (i != k) {
                        playerCards[i][j] = FastPow.pow(playerCards[i][j], d[k], pq.p).toInt()
                    }
                }
                playerCards[i][j] = FastPow.pow(playerCards[i][j], d[i], pq.p).toInt()
            }
        }

        for (j in 0 until tableCards.size) {
            for (i in 0 until playerCount) {
                tableCards[j] = FastPow.pow(tableCards[j], d[i], pq.p).toInt()
            }
        }

        for (i in 0 until playerCount) {
            print("player ${i + 1} cards: ")
            listOfCards.forEach { card ->
                if (card.count == playerCards[i][0])
                    print("${card.cardName}(${card.count}) ")
                if (card.count == playerCards[i][1])
                    print("${card.cardName}(${card.count}) ")
            }
            println("")
        }

        print("Table cards: ")
        for (it in tableCards) {
            listOfCards.forEach { card ->
                if (card.count == it) {
                    val number = card.cardName
                    print("$number($it) ")
                }
            }
        }
        println("")

    }

    private fun fillListOfCards(): List<Cards> {

        val listOfCards = mutableListOf<Cards>()
        val ending = listOf("S", "H", "D", "C")

        var counter = 2
        for (nameCard in 2..10) {
            listOfCards.add(Cards(counter, "${nameCard}S"))
            counter += 1
            listOfCards.add(Cards(counter, "${nameCard}H"))
            counter += 1
            listOfCards.add(Cards(counter, "${nameCard}D"))
            counter += 1
            listOfCards.add(Cards(counter, "${nameCard}C"))
            counter += 1
        }
        for (it in 38..41) {
            listOfCards.add(Cards(counter, "J${ending[it - 38]}"))
            counter += 1
        }
        for (it in 42..45) {
            listOfCards.add(Cards(counter, "Q${ending[it - 42]}"))
            counter += 1
        }

        for (it in 46..49) {
            listOfCards.add(Cards(counter, "K${ending[it - 46]}"))
            counter += 1
        }

        for (it in 50..53) {
            listOfCards.add(Cards(counter, "A${ending[it - 50]}"))
            counter += 1
        }

        return listOfCards
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
        val until = 10.pow(9)

        do {
            q = Generate.generatePrime(until)
            p = 2 * q + 1
        } while (!q.isPrime() || !p.isPrime())

        return PrimeNumbers(p, q)
    }
}