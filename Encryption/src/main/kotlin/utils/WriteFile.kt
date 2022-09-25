package utils

import java.io.File

object WriteFile {

    fun writeToFile(arr: List<Byte>, filename: String) {
        File(filename).writeBytes(arr.toByteArray())
    }
}