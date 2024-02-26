```
//1
interface Creature {
    val attackPower: Int
    val defensePower: Int
    fun attack()
}

class GenericCreature(
    override val attackPower: Int,
    override val defensePower: Int,
) : Creature {
    override fun attack() {
        println("Attacking with $attackPower")
    }
}

class Goblin : Creature {
    private val delegate = GenericCreature(2, 1)
    override val attackPower: Int = delegate.attackPower
    override val defensePower: Int = delegate.defensePower
    
    override fun attack() {
        delegate.attack()
    }
    
    // ...
}

fun main() {
    val goblin = Goblin()
    println(goblin.defensePower) // 1
    goblin.attack() // Attacking with 2
}
```


```
//2
interface Creature {
    val attackPower: Int
    val defensePower: Int
    fun attack()
}

open class GenericCreature(
    override val attackPower: Int,
    override val defensePower: Int,
) : Creature {
    override fun attack() {
        println("Attacking with $attackPower")
    }
}

class Goblin : GenericCreature(2, 1) {
    // ...
}

fun main() {
    val goblin = Goblin()
    println(goblin.defensePower) // 1
    goblin.attack() // Attacking with 2
}
```


```
//3
interface Creature {
    val attackPower: Int
    val defensePower: Int
    fun attack()
}

class GenericCreature(
    override val attackPower: Int,
    override val defensePower: Int,
) : Creature {
    override fun attack() {
        println("Attacking with $attackPower")
    }
}

class Goblin : Creature by GenericCreature(2, 1) {
    // ...
}

fun main() {
    val goblin = Goblin()
    println(goblin.defensePower) // 1
    goblin.attack() // Attacking with 2
}
```


```
class Goblin : Creature by GenericCreature(2, 1)

// or
class Goblin(
    att: Int,
    def: Int
) : Creature by GenericCreature(att, def)

// or
class Goblin(
    creature: Creature
) : Creature by creature

// or
class Goblin(
    val creature: Creature = GenericCreature(2, 1)
) : Creature by creature

// or
val creature = GenericCreature(2, 1)

class Goblin : Creature by creature
```


```
class Goblin : Attack by Dagger(), Defense by LeatherArmour()

class Amphibious : Car by SimpleCar(), Boat by MotorBoat()
```


```
//4
interface Creature {
    val attackPower: Int
    val defensePower: Int
    fun attack()
}

class GenericCreature(
    override val attackPower: Int,
    override val defensePower: Int,
) : Creature {
    override fun attack() {
        println("Attacking with $attackPower")
    }
}

class Goblin : Creature by GenericCreature(2, 1) {
    override fun attack() {
        println("Special Goblin attack $attackPower")
    }
}

fun main() {
    val goblin = Goblin()
    println(goblin.defensePower) // 1
    goblin.attack() // Special Goblin attack 2
}
```


```
//5
interface Creature {
    val attackPower: Int
    val defensePower: Int
    fun attack()
}

class GenericCreature(
    override val attackPower: Int,
    override val defensePower: Int,
) : Creature {
    override fun attack() {
        println("Attacking with $attackPower")
    }
}

class Goblin(
    private val creature: Creature = GenericCreature(2, 1)
) : Creature by creature {
    override fun attack() {
        println("It will be special Goblin attack!")
        creature.attack()
    }
}

fun main() {
    val goblin = Goblin()
    goblin.attack()
    // It will be a special Goblin attack!
    // Attacking with 2
}
```


```
@Keep
@Immutable
data class ComposeImmutableList<T>(
    val innerList: List<T>
) : List<T> by innerList
```


```
class StateFlow<T>(
    source: StateFlow<T>,
    private val scope: CoroutineScope
) : StateFlow<T> by source {
    fun collect(onEach: (T) -> Unit) {
        scope.launch {
            collect { onEach(it) }
        }
    }
}
```


```
var fis: InputStream = FileInputStream("/someFile.gz")
var bis: InputStream = BufferedInputStream(fis)
var gis: InputStream = ZipInputStream(bis)
var ois = ObjectInputStream(gis)
var someObject = ois.readObject() as SomeObject
```


```
fun <T> Sequence<T>.filter(
    predicate: (T) -> Boolean
): Sequence<T> {
    return FilteringSequence(this, true, predicate)
}
```


```
interface AdFilter {
    fun showToPerson(user: User): Boolean
    fun showOnPage(page: Page): Boolean
    fun showOnArticle(article: Article): Boolean
}

class ShowOnPerson(
    val authorKey: String,
    val prevFilter: AdFilter = ShowAds
) : AdFilter {
    override fun showToPerson(user: User): Boolean =
        prevFilter.showToPerson(user)
    
    override fun showOnPage(page: Page) =
        page is ProfilePage &&
                page.userKey == authorKey &&
                prevFilter.showOnPage(page)
    
    override fun showOnArticle(article: Article) =
        article.authorKey == authorKey &&
                prevFilter.showOnArticle(article)
}

class ShowToLoggedIn(
    val prevFilter: AdFilter = ShowAds
) : AdFilter {
    override fun showToPerson(user: User): Boolean =
        user.isLoggedIn
    
    override fun showOnPage(page: Page) =
        prevFilter.showOnPage(page)
    
    override fun showOnArticle(article: Article) =
        prevFilter.showOnArticle(article)
}

object ShowAds : AdFilter {
    override fun showToPerson(user: User): Boolean = true
    override fun showOnPage(page: Page): Boolean = true
    override fun showOnArticle(article: Article): Boolean = true
}

fun createWorkshopAdFilter(workshop: Workshop) =
    ShowOnPerson(workshop.trainerKey)
        .let(::ShowToLoggedIn)
```


```
class Page
class Article(val authorKey: String)
class User(val isLoggedIn: Boolean)

interface AdFilter {
    fun showToPerson(user: User): Boolean
    fun showOnPage(page: Page): Boolean
    fun showOnArticle(article: Article): Boolean
}

class ShowOnPerson(
    val authorKey: String,
    val prevFilter: AdFilter = ShowAds
) : AdFilter by prevFilter {
    override fun showOnPage(page: Page) =
        page is ProfilePage &&
                page.userKey == authorKey &&
                prevFilter.showOnPage(page)
    
    override fun showOnArticle(article: Article) =
        article.authorKey == authorKey &&
                prevFilter.showOnArticle(article)
}

class ShowToLoggedIn(
    val prevFilter: AdFilter = ShowAds
) : AdFilter by prevFilter {
    override fun showToPerson(user: User): Boolean = 
        user.isLoggedIn
}

object ShowAds : AdFilter {
    override fun showToPerson(user: User): Boolean = true
    override fun showOnPage(page: Page): Boolean = true
    override fun showOnArticle(article: Article): Boolean = true
}

fun createWorkshopAdFilter(workshop: Workshop) =
    ShowOnPerson(workshop.trainerKey)
        .let(::ShowToLoggedIn)
```


```
public class ScopedRaise<E>(
    raise: EffectScope<E>,
    scope: CoroutineScope
) : CoroutineScope by scope, EffectScope<E> by raise
```


```
public suspend fun <E, A, B> Iterable<A>.parMapOrAccumulate(
    context: CoroutineContext = EmptyCoroutineContext,
    transform: suspend ScopedRaise<E>.(A) -> B
): Either<NonEmptyList<E>, List<B>> =
    coroutineScope {
        map {
            async(context) {
                either {
                    val scope = this@coroutineScope
                    transform(ScopedRaise(this, scope), it)
                }
            }
        }.awaitAll().flattenOrAccumulate()
    }

suspend fun test() {
    listOf("A", "B", "C")
        .parMapOrAccumulate { v ->
            this.launch { } // We can do that,
            // because receiver is CoroutineContext
            this.ensure(v in 'A'..'Z') { error("Not letter") }
            // We can do that, because receiver is EffectScope
        }
}
```


```
class IntegrationTestScope(
    applicationTestBuilder: ApplicationTestBuilder,
    val application: Application,
    val backgroundScope: CoroutineScope,
) : TestApplicationBuilder(),
    ClientProvider by applicationTestBuilder
```


```
//6
interface Creature {
    fun attack()
}

class GenericCreature : Creature {
    override fun attack() {
        // this.javaClass.name is always GenericCreature
        println("${this::class.simpleName} attacks")
    }
    
    fun walk() {}
}

class Goblin : Creature by GenericCreature()

open class WildAnimal : Creature {
    override fun attack() {
        // this.javaClass.name depends on actual class
        println("${this::class.simpleName} attacks")
    }
}

class Wolf : WildAnimal()

fun main() {
    GenericCreature().attack() // GenericCreature attacks
    Goblin().attack() // GenericCreature attacks
    WildAnimal().attack() // WildAnimal attacks
    Wolf().attack() // Wolf attacks
    // Goblin().walk() COMPILATION ERROR, no such method
}
```


```
interface Attack {
    val attack: Int
    val defense: Int
}
interface Defense {
    val defense: Int
}
class Dagger : Attack {
    override val attack: Int = 1
    override val defense: Int = -1
}
class LeatherArmour : Defense {
    override val defense: Int = 2
}
class Goblin(
    private val attackDelegate: Attack = Dagger(),
    private val defenseDelegate: Defense = LeatherArmour(),
) : Attack by attackDelegate, Defense by defenseDelegate {
    // We must override this property
    override val defense: Int =
        defenseDelegate.defense + attackDelegate.defense
}
```