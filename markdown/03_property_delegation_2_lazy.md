```
val userRepo by lazy { UserRepository() }

// Alternative code not using lazy
private var _userRepo: UserRepository? = null
private val userRepoLock = Any()
val userRepo: UserRepository
    get() {
        synchronized(userRepoLock) {
            if (_userRepo == null) {
                _userRepo = UserRepository()
            }
            return _userRepo!!
        }
    }
```


```
class A {
    val b = B()
    val c = C()
    val d = D()

    // ...
}
```


```
class A {
    val b by lazy { B() }
    val c by lazy { C() }
    val d by lazy { D() }

    // ...
}
```


```
class OurLanguageParser {
    val cardRegex by lazy { Regex("...") }
    val questionRegex by lazy { Regex("...") }
    val answerRegex by lazy { Regex("...") }

    // ...
}
```


```
fun String.isValidIpAddress(): Boolean {
    return this.matches(
        ("\\A(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
        "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\z").toRegex()
    )
}

// Usage
print("5.173.80.254".isValidIpAddress()) // true
```


```
private val IS_VALID_IP_REGEX = 
    ("\\A(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
    "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\z").toRegex()

fun String.isValidIpAddress(): Boolean =
    matches(IS_VALID_IP_REGEX)
```


```
private val IS_VALID_IP_REGEX by lazy {
    ("\\A(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
    "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\z").toRegex()
}

fun String.isValidIpAddress(): Boolean =
    matches(IS_VALID_IP_REGEX)
```


```
data class User(
    val name: String,
    val surname: String,
    val pronouns: Pronouns,
    val gender: Gender,
    // ...
) {
    val fullDisplay: String = produceFullDisplay()
    
    fun produceFullDisplay(): String {
        println("Calculating...")
        // ...
        return "XYZ"
    }
}

fun test() {
    val user = User(...) // Calculating...
    val copy = user.copy() // Calculating...
    println(copy.fullDisplay) // XYZ
    println(copy.fullDisplay) // XYZ
}
```


```
data class User(
    val name: String,
    val surname: String,
    val pronouns: Pronouns,
    val gender: Gender,
    // ...
) {
    val fullDisplay: String
        get() = produceFullDisplay()
    
    fun produceFullDisplay(): String {
        println("Calculating...")
        // ...
        return "XYZ"
    }
}

fun test() {
    val user = User(...)
    val copy = user.copy()
    println(copy.fullDisplay) // Calculating... XYZ
    println(copy.fullDisplay) // Calculating... XYZ
}
```


```
data class User(
    val name: String,
    val surname: String,
    val pronouns: Pronouns,
    val gender: Gender,
    // ...
) {
    val fullDisplay: String by lazy { produceFullDisplay() }
    
    fun produceFullDisplay() {
        println("Calculating...")
        // ...
    }
}

fun test() {
    val user = User(...)
    val copy = user.copy()
    println(copy.fullDisplay) // Calculating... XYZ
    println(copy.fullDisplay) // XYZ
}
```


```
val v1 by lazy { calculate() }
val v2 by lazy(LazyThreadSafetyMode.PUBLICATION){ calculate() }
val v3 by lazy(LazyThreadSafetyMode.NONE) { calculate() }
```


```
class MainActivity : Activity() {
    lateinit var questionLabelView: TextView
    lateinit var answerLabelView: EditText
    lateinit var confirmButtonView: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        questionLabelView = findViewById(R.id.main_question_label)
        answerLabelView = findViewById(R.id.main_answer_label)
        confirmButtonView = findViewById(R.id.main_button_confirm)
    }
}
```


```
class MainActivity : Activity() {
    val questionLabelView: TextView by
        lazy { findViewById(R.id.main_question_label) }
    val answerLabelView: TextView by
        lazy { findViewById(R.id.main_answer_label) }
    val confirmButtonView: Button by
        lazy { findViewById(R.id.main_button_confirm) }
    
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}
```


```
class MainActivity : Activity() {
    var questionLabelView: TextView by
        bindView(R.id.main_question_label)
    var answerLabelView: TextView by
        bindView(R.id.main_answer_label)
    var confirmButtonView: Button by
        bindView(R.id.main_button_confirm)
    
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}

// ActivityExt.kt
fun <T : View> Activity.bindView(viewId: Int) =
    lazy { this.findViewById<T>(viewId) }
```


```
class SettingsActivity : Activity() {
    private val titleString by bindString(R.string.title)
    private val blueColor by bindColor(R.color.blue)
    
    private val doctor by extra<Doctor>(DOCTOR_KEY)
    private val title by extraString(TITLE_KEY)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
    }
}

// ActivityExt.kt
fun <T> Activity.bindString(@IdRes id: Int): Lazy<T> =
    lazy { this.getString(id) }
fun <T> Activity.bindColor(@IdRes id: Int): Lazy<T> =
    lazy { this.getColour(id) }
fun <T : Parcelable> Activity.extra(key: String) =
    lazy { this.intent.extras.getParcelable(key) }
fun Activity.extraString(key: String) =
    lazy { this.intent.extras.getString(key) }
```


```
//1
class Lazy {
    var x = 0
    val y by lazy { 1 / x }
    
    fun hello() {
        try {
            print(y)
        } catch (e: Exception) {
            x = 1
            print(y)
        }
    }
}

fun main(args: Array<String>) {
    Lazy().hello()
}
```