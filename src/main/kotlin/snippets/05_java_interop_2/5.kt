package f_05_java_interop_2.s_5

import java.math.BigDecimal

class Money(val amount: BigDecimal, val currency: String) {
    companion object {
        fun usd(amount: Double) = 
            Money(amount.toBigDecimal(), "PLN")
    }
}

object MoneyUtils {
    fun parseMoney(text: String): Money = TODO()
}

fun main() {
    val m1 = Money.usd(10.0)
    val m2 = MoneyUtils.parseMoney("10 EUR")
}
