package f_01_generics_variance_3.s_6

class Box<T>(val value: T)

val boxStr: Box<String> = Box("Str")
val boxAny: Box<out Any> = boxStr
