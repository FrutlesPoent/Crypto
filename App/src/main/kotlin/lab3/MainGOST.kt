package lab3

import gost.signature.GostSign
import utils.ReadFile

fun main() {

    GostSign.gostSign(ReadFile.readFileAsLinesUsingBufferedReader("images/start/bear.png"))
}