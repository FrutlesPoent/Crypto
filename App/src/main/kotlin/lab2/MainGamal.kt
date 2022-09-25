package lab2

import elGamal.ElGamal
import utils.ReadFile

fun main() {

    ElGamal.elGamal(ReadFile.readFileAsLinesUsingBufferedReader("images/start/boom.gif"))
}