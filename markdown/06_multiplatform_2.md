```
class YamlParser {
    fun parse(text: String): YamlObject { 
        /*...*/
    }
    fun serialize(obj: YamlObject): String { 
        /*...*/
    }

    // ...
}

sealed interface YamlElement

data class YamlObject(
    val properties: Map<String, YamlElement>
) : YamlElement

data class YamlString(val value: String) : YamlElement

// ...
```


```
// build.gradle.kts
plugins {
    kotlin("multiplatform") version "1.8.10"
    // ...
    java
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        browser()
        binaries.library()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                // ...
            }
        }
        val commonTest by getting {
            dependencies {
                // ...
            }
        }
        val jvmMain by getting {
            dependencies {
                // ...
            }
        }
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
    }
}
```


```
@JsExport
class YamlParser {
    fun parse(text: String): YamlObject { 
        /*...*/
    }
    fun serialize(obj: YamlObject): String { 
        /*...*/
    }

    // ...
}

@JsExport
sealed interface YamlElement

@JsExport
data class YamlObject(
    val properties: Map<String, YamlElement>
) : YamlElement

@JsExport
data class YamlString(val value: String) : YamlElement
// ...
```


```
// Using Okio to read file
class FileYamlReader {
    private val parser = YamlParser()
    
    fun read(filePath: String): YamlObject {
        val source = FileSystem.SYSTEM
            .source(filePath)
            .let(Okio::buffer)
        val fileContent = source.readUtf8()
        source.close()
        return parser.parse(fileContent)
    }
}

// Using Ktor client to read URL
class NetworkYamlReader {
    private val parser = YamlParser()
    
    suspend fun read(url: String): YamlObject {
        val resp = client.get(url) {
            headers {
                append(HttpHeaders.Accept, "text/yaml")
            }
        }.bodyAsText()
        return parser.parse(resp)
    }
}
```


```
// jsMain module
@JsExport
@JsName("NetworkYamlReader")
class NetworkYamlReaderJs {
    private val reader = NetworkYamlReader()
    private val scope = CoroutineScope(SupervisorJob())
    
    fun read(url: String): Promise<YamlObject> = scope.promise { 
        reader.read(url) 
    }
}
```