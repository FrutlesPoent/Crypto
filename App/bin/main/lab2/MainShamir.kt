package lab2

import shamirs.Shamirs
import utils.ReadFile
import utils.WriteFile

fun main() {

//    Shamirs.cipher(ReadFile.readFileAsLinesUsingBufferedReader("test.png"))

    val readF = ReadFile.readFileAsLinesUsingBufferedReader("test.png")
    val readS = ReadFile.readFileAsLinesUsingBufferedReader("test2.png")

    println(readF.contentEquals(readS))
//    val read = ReadFile.readFileAsLinesUsingBufferedReader("test.png")
//    WriteFile.writeToFile(read.toList())

}
