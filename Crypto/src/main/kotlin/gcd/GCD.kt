package gcd

import domain.Vector

object GCD {

    private fun gcd(a: Int, b: Int): Int {
        var result: Int
        var aMutable = a
        var bMutable = b
        while (bMutable > 0) {
            result = aMutable.mod(bMutable)
            aMutable = bMutable
            bMutable = result
        }

        return aMutable
    }

    fun uniteGcd(a: Int, b: Int): Vector {
        if (a < b) {
            throw IllegalArgumentException("A should be more than B")
        }
        var u = Vector(a, x = 1, y = 0)
        var v = Vector(b, x = 0, y = 1)
        var q: Int

        while (v.gcd > 0) {
            q = u.gcd.div(v.gcd)
            val temp = Vector(
                gcd = (u.gcd).mod(v.gcd),
                x = u.x - (q * v.x),
                y = u.y - (q * v.y),
            )
            u = v
            v = temp
        }
        return u
    }
}