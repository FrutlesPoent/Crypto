package lab5

import client.BlindSignatureClient
import server.BlindSignatureServer

fun main() {

    val server = BlindSignatureServer()
    val client = BlindSignatureClient()

    for (i in 0 until  20) {
        server.start()
        client.start()
        server.calculateS()
        client.calculateOwnS()
        server.checkBilluten()
    }
    server.printResults()

}