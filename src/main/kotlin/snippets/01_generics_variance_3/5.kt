package f_01_generics_variance_3.s_5

class Box<out T>(val value: T)

val boxStr: Box<String> = Box("Str")
val boxAny: Box<Any> = boxStr
