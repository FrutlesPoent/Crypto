package vernam

import utils.Generate
import utils.WriteFile
import kotlin.experimental.xor

object Vernam {

    fun vernam(message: ByteArray) {

        val keys = generateKeys(message.size)

        val secureMessage = encrypt(keys, message)
        decrypt(keys, secureMessage)

    }

    private fun encrypt(keys: List<Byte>, message: ByteArray): List<Byte> {
        val secureMessage = mutableListOf<Byte>()
        for (i in message.indices) {
            secureMessage.add(keys[i].xor(message[i]))
        }
        WriteFile.writeToFile(secureMessage, "images/Venmar/VenmarEncryption.png")
        return secureMessage
    }

    private fun decrypt(keys: List<Byte>, secureMessage: List<Byte>) {
        val notSecureMessage = mutableListOf<Byte>()
        for (i in secureMessage.indices) {
            notSecureMessage.add(secureMessage[i].xor(keys[i]))
        }

        WriteFile.writeToFile(notSecureMessage, "images/Venmar/VenmarDecryption.png")
    }

    private fun generateKeys(messageLength: Int): List<Byte> {
        val keys = mutableListOf<Byte>()
        do {
            keys.add(Generate.generateBytes().toByte())
        } while (keys.size != messageLength)

        return keys
    }
}