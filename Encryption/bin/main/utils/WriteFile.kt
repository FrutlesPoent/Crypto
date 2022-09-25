package utils

import java.io.File

object WriteFile {

    fun writeToFile(arr: List<Byte>) {
        File("test2.png").writeBytes(arr.toByteArray())
    }
}