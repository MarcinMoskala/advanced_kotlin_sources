```
//1
fun getTheAnswer(answer: String = "24"): String {
  return answer?.reversed()!!
}
```


```
val answer : String? = maybeGetTheAnswer()
if (answer != null) {
  println("The answer is $answer")
}
```


```
plugins {
  kotlin("jvm") version "..."
  // Add this line
  id("io.gitlab.arturbosch.detekt") version "..."
}
```


```
//2
fun main() {
  println(42)
}
```


```
//3
fun main() {
  // Non compliant
  System.out.print("Hello")

  // Compliant
  println("World!")
}
```


```
@KotlinCoreEnvironmentTest
internal class MyRuleTest(
  private val env: KotlinCoreEnvironment
) {
  @Test
  fun `reports usages of System_out_println`() {
    val code = """
      fun main() {
        System.out.println("Hello")
      }
    """.trimIndent()
    
    val findings = MyRule(Config.empty)
                   .compileAndLintWithContext(env, code)
    findings shouldHaveSize 1
  }

  @Test
  fun `does not report usages Kotlin's println`() {
    val code = """
      fun main() {
        println("Hello")
      }
    """.trimIndent()
    
    val findings = MyRule(Config.empty)
                   .compileAndLintWithContext(env, code)
    findings shouldHaveSize 0
  }
}
```


```
class MyRule(config: Config) : Rule(config) {
  override val issue = Issue(
    javaClass.simpleName,
    Severity.CodeSmell,
    "Custom Rule",
    Debt.FIVE_MINS,
  )

  override fun visitClass(klass: KtClass) {
    // ...
  }
}
```


```
class MyRule(config: Config) : Rule(config) {
  override val issue = //...

  override fun visitDotQualifiedExpression(
    expression: KtDotQualifiedExpression
  ) {
    super.visitDotQualifiedExpression(expression)
    if (expression.text.startsWith("System.out.println")) {
      report(CodeSmell(
        issue,
        Entity.from(expression),
        "Use Kotlin stdlib's println instead.",
      ))
    }
  }
}
```


```
plugins {
  kotlin("jvm") version "..."
  id("io.gitlab.arturbosch.detekt") version "..."
}

dependencies {
  detektPlugin("org.example:detekt-custom-rule:...")
}
```