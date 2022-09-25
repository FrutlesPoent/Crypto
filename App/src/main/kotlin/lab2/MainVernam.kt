package lab2

import utils.ReadFile
import vernam.Vernam

fun main() {

    Vernam.vernam(ReadFile.readFileAsLinesUsingBufferedReader("images/start/venmar.png"))
}