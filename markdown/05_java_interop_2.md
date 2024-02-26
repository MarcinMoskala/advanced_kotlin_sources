```
//1
import kotlin.properties.Delegates.notNull

class User {
    var name = "ABC" // getter, setter, field
    var surname: String by notNull() //getter, setter, delegate
    val fullName: String // only getter
        get() = "$name $surname"
}
```


```
// Compiles to the analog of the following Java code
public final class User {
    // $FF: synthetic field
    static final KProperty[] $$delegatedProperties = ...

    @NotNull
    private String name = "ABC";
    
    @NotNull
    private final ReadWriteProperty surname$delegate;
    
    @NotNull
    public final String getName() {
        return this.name;
    }
    
    public final void setName(@NotNull String var1) {
        Intrinsics.checkNotNullParameter(var1, "<set-?>");
        this.name = var1;
    }
    
    @NotNull
    public final String getSurname() {
        return (String) this.surname$delegate
                .getValue(this, $$delegatedProperties[0]);
    }
    
    public final void setSurname(@NotNull String v) {
        Intrinsics.checkNotNullParameter(v, "<set-?>");
        this.surname$delegate
                .setValue(this, $$delegatedProperties[0], v);
    }
    
    @NotNull
    public final String getFullName() {
        return this.name + ' ' + this.getSurname();
    }
    
    public User() {
        this.surname$delegate = Delegates.INSTANCE.notNull();
    }
}
```


```
class User {
    @SomeAnnotation
    var name = "ABC"
}
```


```
class User {
    @field:SomeAnnotation
    var name = "ABC"
}
```


```
//2
annotation class A
annotation class B
annotation class C
annotation class D
annotation class E

class User {
    @property:A
    @get:B
    @set:C
    @field:D
    @setparam:E
    var name = "ABC"
}
```


```
// Compiles to the analog of the following Java code
public final class User {
    @D
    @NotNull
    private String name = "ABC";
    
    @A
    public static void getName$annotations() {
    }
    
    @B
    @NotNull
    public final String getName() {
        return this.name;
    }
    
    @C
    public final void setName(@E @NotNull String var1) {
        Intrinsics.checkNotNullParameter(var1, "<set-?>");
        this.name = var1;
    }
}
```


```
class User(
    @param:A val name: String
)
```


```
//3
annotation class A

class User {
    @A
    val name = "ABC"
}
```


```
// Compiles to the analog of the following Java code
public final class User {
    @NotNull
    private String name = "ABC";
    
    @A
    public static void getName$annotations() {
    }
    
    @NotNull
    public final String getName() {
        return this.name;
    }
}
```


```
//4
annotation class A
annotation class B

@A
class User @B constructor(
    val name: String
)
```


```
// Compiles to the analog of the following Java code
@A
public final class User {
    @NotNull
    private final String name;
    
    @NotNull
    public final String getName() {
        return this.name;
    }
    
    @B
    public User(@NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        super();
        this.name = name;
    }
}
```


```
annotation class Positive

fun @receiver:Positive Double.log() = ln(this)

// Java alternative
public static final double log(@Positive double $this$log) {
    return Math.log($this$log);
}
```


```
//5
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
```


```
// Java
public class JavaClass {
    public static void main(String[] args) {
        Money m1 = Money.Companion.usd(10.0);
        Money m2 = MoneyUtils.INSTANCE.parseMoney("10 EUR");
    }
}
```


```
// Kotlin
class Money(val amount: BigDecimal, val currency: String) {
    companion object {
        @JvmStatic
        fun usd(amount: Double) =
            Money(amount.toBigDecimal(), "PLN")
    }
}

object MoneyUtils {
    @JvmStatic
    fun parseMoney(text: String): Money = TODO()
}

fun main() {
    val money1 = Money.usd(10.0)
    val money2 = MoneyUtils.parseMoney("10 EUR")
}
```


```
// Java
public class JavaClass {
    public static void main(String[] args) {
        Money m1 = Money.usd(10.0);
        Money m2 = MoneyUtils.parseMoney("10 EUR");
    }
}
```


```
//6
// Kotlin
class Box {
    var name = ""
}
```


```
// Java
public class JavaClass {
    public static void main(String[] args) {
        Box box = new Box();
        box.setName("ABC");
        System.out.println(box.getName());
    }
}
```


```
//7
// Kotlin
class Box {
    @JvmField
    var name = ""
}
```


```
// Java
public class JavaClass {
    public static void main(String[] args) {
        Box box = new Box();
        box.name = "ABC";
        System.out.println(box.name);
    }
}
```


```
//8
// Kotlin
object Box {
    @JvmField
    var name = ""
}
```


```
// Java
public class JavaClass {
    public static void main(String[] args) {
        Box.name = "ABC";
        System.out.println(Box.name);
    }
}
```


```
//9
// Kotlin
class MainWindow {
    // ...
    
    companion object {
        const val SIZE = 10
    }
}
```


```
// Java
public class JavaClass {
    public static void main(String[] args) {
        System.out.println(MainWindow.SIZE);
    }
}
```


```
//10
class User {
    var name = "ABC"
    var isAdult = true
}
```


```
// Java alternative
public final class User {
    @NotNull
    private String name = "ABC";
    private boolean isAdult = true;
    
    @NotNull
    public final String getName() {
        return this.name;
    }

    public final void setName(@NotNull String var1) {
        Intrinsics.checkNotNullParameter(var1, "<set-?>");
        this.name = var1;
    }

    public final boolean isAdult() {
        return this.isAdult;
    }

    public final void setAdult(boolean var1) {
        this.isAdult = var1;
    }
}
```