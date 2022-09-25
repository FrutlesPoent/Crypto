package lab2

import shamirs.Shamirs
import utils.ReadFile

fun main() {

    Shamirs.cipher(ReadFile.readFileAsLinesUsingBufferedReader("images/start/catEars.png"))

}
