package com.queentylion.gestra.ui.screens.translate

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.queentylion.gestra.domain.ble.GloveReceiveManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TranslateViewModel @Inject constructor(
    val gloveReceiveManager : GloveReceiveManager
) : ViewModel() {
//    var textFromSpeech: String? by mutableStateOf(null)
    private var tts: TextToSpeech? = null

    fun textToSpeech(context: Context, text: String) {
        tts = TextToSpeech(context) {
            if (it == TextToSpeech.SUCCESS) {
                tts?.let { textToSpeech ->
                    textToSpeech.language = Locale.getDefault()
                    textToSpeech.setSpeechRate(1.0f)
                    textToSpeech.speak(
                        text,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        null
                    )
                }
            }
        }
    }

}