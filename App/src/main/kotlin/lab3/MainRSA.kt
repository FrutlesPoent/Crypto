package lab3

import rsa.signature.RSASign
import utils.ReadFile

fun main() {

    RSASign.rsaSign(ReadFile.readFileAsLinesUsingBufferedReader("images/start/bear.png"))
}