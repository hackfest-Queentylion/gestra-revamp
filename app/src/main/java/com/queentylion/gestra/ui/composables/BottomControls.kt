package com.queentylion.gestra.ui.composables

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.queentylion.gestra.R
import com.queentylion.gestra.ui.screens.translate.TranslateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BottomControls(
    activeButtonIndex: Int,
    onRadioClicked: (Int) -> Unit,
    changeText: (String) -> Unit,
    changeTextColor: (Color) -> Unit,
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()
    var isRecording by remember {
        mutableStateOf(false)
    }
    val microphonePermissionState =
        rememberPermissionState(android.Manifest.permission.RECORD_AUDIO)

    val speechRecognizer = remember {
        SpeechRecognizer.createSpeechRecognizer(context)
    }
    val recognitionListener = remember {
        object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                changeText("")
            }

            override fun onBeginningOfSpeech() {}

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {
                Log.d("SpeechRecognition", "End of speech")
                changeText("Start speaking")
                changeTextColor(Color(0xFFD3D3D3))
                isRecording = false
            }

            override fun onError(error: Int) {
                Log.e("SpeechRecognition", "Error code: $error")
                changeText("Start speaking")
                changeTextColor(Color(0xFFD3D3D3))
                isRecording = false
            }

            override fun onResults(results: Bundle?) {
                if (results == null) {
                    changeText("Start speaking")
                    changeTextColor(Color(0xFFD3D3D3))
                } else {
                    val speechResult =
                        results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.get(0)
                    speechResult?.let {
                        Log.d("SpeechRecognition", "Recognized speech: $speechResult")
                        changeText(speechResult)
                        changeTextColor(Color(0xFF191C20))
                        isRecording = false
                    }
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {
                Log.d("SpeechRecognition", "Partial result received")
                if (partialResults == null) {
                    changeText("Start speaking")
                    changeTextColor(Color(0xFFD3D3D3))
                } else {
                    val results =
                        partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    val partialResult = results?.firstOrNull() ?: ""
                    coroutineScope.launch {
                        withContext(Dispatchers.IO) {
                            if (partialResult.isNotBlank()) {
                                changeText(partialResult)
                                changeTextColor(Color(0xFFD3D3D3))
                            }
                        }
                    }
                }
            }

            override fun onEvent(eventType: Int, params: Bundle?) {}

        }
    }

    speechRecognizer.setRecognitionListener(recognitionListener)

    DisposableEffect(lifecycleOwner) {
        onDispose {
            speechRecognizer.destroy()
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-32).dp)
                .padding(bottom = 30.dp)
        ) {
            ControlButton(
                icon = R.drawable.ic_textinput,
                onButtonClick = {
                    onRadioClicked(0)
                },
                contentTitle = "Text",
                contentDescription = "Go to favorites page",
                isActive = (activeButtonIndex == 0)
            )
            ControlButton(
                icon = R.drawable.ic_gesture_control,
                onButtonClick = {
                    onRadioClicked(1)
                    changeText("Start sign language")
                    changeTextColor(Color(0xFFD3D3D3))
                },
                contentTitle = "Gesture",
                contentDescription = "Go to favorites page",
                isActive = (activeButtonIndex == 1)
            )
            ControlButton(
                icon = R.drawable.ic_microphone,
                onButtonClick = {
                    onRadioClicked(2)
                    changeText("Start speaking")
                    changeTextColor(Color(0xFFD3D3D3))
                },
                contentTitle = "Voice",
                contentDescription = "Go to favorites page",
                isActive = (activeButtonIndex == 2)
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth()
        ) {
            SmallControlButton(
                icon = R.drawable.ic_favorite,
                onButtonClick = { /* TODO */ },
                contentDescription = "Go to favorites page"
            )
            Box(
                modifier = Modifier
                    .offset(y = (-32).dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(enabled = activeButtonIndex != 0) {
                            when (activeButtonIndex) {
                                2 -> {
                                    isRecording = !isRecording
                                    if (isRecording) {
                                        if (!microphonePermissionState.status.isGranted) {
                                            Log.d("AudioPermission", "Asking for permission")
                                            microphonePermissionState.launchPermissionRequest()
                                            isRecording = !isRecording
                                        } else {
                                            Log.d("AudioPermission", "Skipping permission")
                                        }

                                        if (microphonePermissionState.status.isGranted) {
                                            Log.d("OpenMicrophone", "Opening microphone")
                                            val intent =
                                                Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                                    putExtra(
                                                        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                                                    )
                                                    putExtra(
                                                        RecognizerIntent.EXTRA_LANGUAGE,
                                                        Locale
                                                            .getDefault()
                                                            .toString()
                                                    )
                                                    putExtra(
                                                        RecognizerIntent.EXTRA_PARTIAL_RESULTS,
                                                        true
                                                    )
                                                }
                                            speechRecognizer.startListening(intent)

                                        } else {
                                            Log.d("OpenMicrophone", "Not opening microphone")
                                        }
                                    } else {
                                        speechRecognizer.stopListening()
                                    }

                                }

                                3 -> {

                                }
                            }
                        }
                        .background(
                            if (activeButtonIndex != 0) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onPrimaryContainer.copy(
                                0.3f
                            )
                        )
                ) {
                    Icon(
                        imageVector = if (activeButtonIndex == 2) ImageVector.vectorResource(R.drawable.ic_microphone) else ImageVector.vectorResource(
                            R.drawable.ic_gesture_action
                        ),
                        contentDescription = "Start action",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(18.dp)
                            .size(32.dp)
                    )
                }
            }
            SmallControlButton(
                icon = R.drawable.ic_history,
                onButtonClick = { /*TODO*/ },
                contentDescription = "Go to favorites page"
            )
        }
    }
}
