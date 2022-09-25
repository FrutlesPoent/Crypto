package lab2

import rsa.Rsa
import utils.ReadFile

fun main() {

    Rsa.rsa(ReadFile.readFileAsLinesUsingBufferedReader("images/start/bear.png"))
}