package utils

import java.io.File
import java.math.BigInteger

object WriteFile {

    fun writeToFile(arr: List<Byte>, filename: String) {
        File(filename).writeBytes(arr.toByteArray())
    }

    fun writeToFileText(array: List<Int>, filename: String) {
        File(filename).printWriter().use { out -> array.forEach { out.println(it) } }
    }
}