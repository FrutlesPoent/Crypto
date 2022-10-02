package lab3

import el.gamal.signature.ElGamalSign
import utils.ReadFile

fun main() {

    ElGamalSign.elGamalSign(ReadFile.readFileAsLinesUsingBufferedReader("images/start/bear.png"))
}

