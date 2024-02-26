package f_05_java_interop_3.s_2

class Pizza(
    val tomatoSauce: Int = 1,
    val cheese: Int = 0,
    val ham: Int = 0,
    val onion: Int = 0,
)

class EmailSender {
    fun send(
        receiver: String,
        title: String = "",
        message: String = "",
    ) { 
        /*...*/
    }
}
