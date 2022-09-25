import fastpow.FastPow
import org.junit.jupiter.api.Test

class FastPowTest {

    @Test
    fun `run test`() {
        println(FastPow.pow(5, 11, 12))
        println(FastPow.pow(5, 20, 7))
        println(FastPow.pow(3, 21, 11))
        println(FastPow.pow(7, 31, 17))
        println(FastPow.pow(2, 10, 5))
        println(FastPow.pow(5, 17, 11))
        println(FastPow.pow(3, 15, 10))
        println(FastPow.pow(7,12, 9))
        println(FastPow.pow(2, 11, 5))
        println(FastPow.pow(10, 9, 3))
        println(FastPow.pow(1000000000, 1000000000, 12))
    }

    @Test
    fun `second`() {
        println(FastPow.pow(197, 199, 151))
        println(FastPow.pow(203742636, 3, 248986275)) // 192 187 881
        println(FastPow.pow(5, 1, 10))
        println(FastPow.pow(5, 2, 10))
        println(FastPow.pow(5, 4, 3))
    }
}