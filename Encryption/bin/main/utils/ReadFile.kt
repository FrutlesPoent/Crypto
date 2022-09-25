package utils

import java.io.File

object ReadFile {

    fun readFileAsLinesUsingBufferedReader(filename: String): ByteArray =
        File(filename).readBytes()

    fun readFileUsingResource(filename: String) {
        this::class.java.getResource(filename)?.readText(Charsets.UTF_8)
    }
}