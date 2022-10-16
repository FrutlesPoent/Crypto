package fiatShamirRgr

import client.FiatShamirClient
import server.FiatShamirServer

fun main() {

    val server = FiatShamirServer()
    val client = FiatShamirClient()

    server.start()
    client.registration()
    val t = 5
    var count = 0
    var auth: Boolean
    do {
        client.authorization()
        server.generateE()
        client.calculateY()
        auth = server.validate()
        count += 1
        if (t < count) {
            break
        }
    }while (!auth)

    if (auth){
        println("Success")
    } else {
        println("Not success")
    }
}