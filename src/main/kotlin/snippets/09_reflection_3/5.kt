package f_09_reflection_3.s_5

import kotlin.math.ln
import kotlin.random.Random
import kotlin.reflect.KType
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.typeOf

class RandomValueConfig(
    val nullProbability: Double = 0.1,
    val zeroProbability: Double = 0.1,
)

class ValueGenerator(
    private val random: Random = Random,
    val config: RandomValueConfig = RandomValueConfig(),
) {

    inline fun <reified T> randomValue(): T =
        randomValue(typeOf<T>()) as T

    fun randomValue(type: KType): Any? = when {
        type.isMarkedNullable &&
            randomBoolean(config.nullProbability) -> null
        type == typeOf<Boolean>() -> randomBoolean()
        type == typeOf<Int>() -> randomInt()
        // ...
        else -> error("Type $type not supported")
    }

    private fun randomInt() =
        if (randomBoolean(config.zeroProbability)) 0
        else random.nextInt()
    
    private fun randomBoolean() =
        random.nextBoolean()

    private fun randomBoolean(probability: Double) =
        random.nextDouble() < probability
}
