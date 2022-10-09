package hash

import java.math.BigInteger
import java.security.MessageDigest

internal fun md5(message: ByteArray): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(message)).toString(16).padStart(32, '0')
}

internal fun String.md5ToByteArray(): ByteArray =
    this.toByteArray()