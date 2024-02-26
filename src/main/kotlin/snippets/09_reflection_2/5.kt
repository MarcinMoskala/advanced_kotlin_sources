package f_09_reflection_2.s_5

sealed class UserMessages

private data class UserId(val id: String) {
    companion object {
        val ZERO = UserId("")
    }
}

internal fun interface Filter<T> {
    fun check(value: T): Boolean
}

fun main() {
    println(UserMessages::class.visibility) // PUBLIC
    println(UserMessages::class.isSealed) // true
    println(UserMessages::class.isOpen) // false
    println(UserMessages::class.isFinal) // false
    println(UserMessages::class.isAbstract) // false

    println(UserId::class.visibility) // PRIVATE
    println(UserId::class.isData) // true
    println(UserId::class.isFinal) // true

    println(UserId.Companion::class.isCompanion) // true
    println(UserId.Companion::class.isInner) // false

    println(Filter::class.visibility) // INTERNAL
    println(Filter::class.isFun) // true
}
