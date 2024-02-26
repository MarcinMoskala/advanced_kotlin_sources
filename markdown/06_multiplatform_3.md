```
class WorkoutViewModel(
    private val timer: TimerService,
    private val speaker: SpeakerService,
    private val loadTrainingUseCase: LoadTrainingUseCase
    // ...
) : ViewModel() {
    private var state: WorkoutState = ...
            
    val title = MutableStateFlow("")
    val imgUrl = MutableStateFlow("")
    val progress = MutableStateFlow(0)
    val timerText = MutableStateFlow("")
    
    init {
        loadTraining()
    }
    
    fun onNext() {
        // ...
    }

    // ...
}
```


```
// commonMain
expect abstract class ViewModel() {
    open fun onCleared()
}

// androidMain
abstract class ViewModel : androidx.lifecycle.ViewModel() {
    val scope = viewModelScope
    
    override fun onCleared() {
        super.onCleared()
    }
}

// iOS source sets
actual abstract class ViewModel actual constructor() {
    actual open fun onCleared() {
    }
}
```


```
// commonMain
expect abstract class ViewModel() {
    val scope: CoroutineScope
    open fun onCleared()
}

// androidMain
abstract class ViewModel : androidx.lifecycle.ViewModel() {
    val scope = viewModelScope
    
    override fun onCleared() {
        super.onCleared()
    }
}

// iOS source sets
actual abstract class ViewModel actual constructor() {
    actual val scope: CoroutineScope = MainScope()
    
    actual open fun onCleared() {
        scope.cancel()
    }
}
```


```
// commonMain
interface SpeakerService {
    fun speak(text: String)
}

// Android application
class AndroidSpeaker(context: Context) : SpeakerService {
    
    private var tts = TextToSpeech(context, null)
    
    override fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }
}
```


```
val title: String by viewModel.title.collectAsState()
val imgUrl: String by viewModel.imgUrl.collectAsState()
val progress: Int by viewModel.progress.collectAsState()
val timerText: String by viewModel.timerText.collectAsState()
```