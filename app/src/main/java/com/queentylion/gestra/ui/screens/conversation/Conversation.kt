package com.queentylion.gestra.ui.screens.conversation

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.queentylion.gestra.R
import com.queentylion.gestra.ui.screens.main.Routes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun Conversation(
    mainNavController: NavController
) {
    var gestureText by remember { mutableStateOf("") }
    var speechText by remember { mutableStateOf("") }
    var isRecording by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

    val microphonePermissionState =
        rememberPermissionState(Manifest.permission.RECORD_AUDIO)

    val speechRecognizer = remember {
        SpeechRecognizer.createSpeechRecognizer(context)
    }
    val recognitionListener = remember {
        object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                speechText = ""
            }

            override fun onBeginningOfSpeech() {}

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {
                Log.d("SpeechRecognition", "End of speech")
                isRecording = false
            }

            override fun onError(error: Int) {
                Log.e("SpeechRecognition", "Error code: $error")
                isRecording = false
            }

            override fun onResults(results: Bundle?) {
                if (results == null) {
                    speechText = ""
                } else {
                    val speechResult =
                        results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.get(0)
                    speechResult?.let {
                        Log.d("SpeechRecognition", "Recognized speech: $speechResult")
                        speechText = speechResult
                        isRecording = false
                    }
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {
                Log.d("SpeechRecognition", "Partial result received")
                if (partialResults == null) {
                    speechText = ""
                } else {
                    val results =
                        partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    val partialResult = results?.firstOrNull() ?: ""
                    coroutineScope.launch {
                        withContext(Dispatchers.IO) {
                            if (partialResult.isNotBlank()) {
                                speechText = partialResult
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

    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 5.dp
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Conversation",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 22.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            mainNavController.navigate(Routes.NavScaffold)
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Go back to home page"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* TODO: Create a profile page */ }) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = "Change language configuration"
                            )
                        }
                    },
                )
            }
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = 20.dp,
                    start = 15.dp,
                    end = 15.dp
                )
                .fillMaxHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(0.5f))
            ) {
                Text(
                    text = gestureText,
                    fontSize = 25.sp,
                    lineHeight = 35.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Column(
                modifier = Modifier
                    .padding(vertical = 15.dp)
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(0.5f))
            ) {
                Text(
                    text = speechText,
                    fontSize = 25.sp,
                    lineHeight = 35.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {

                        }
                        .background(MaterialTheme.colorScheme.onPrimaryContainer)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_gesture_action),
                        contentDescription = "Play button",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(13.dp)
                            .size(25.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {

                        }
                        .background(MaterialTheme.colorScheme.onPrimaryContainer)
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Play button",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(12.dp)
                            .size(50.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
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
                                            putExtra(
                                                RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS,
                                                5000
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
                        .background(MaterialTheme.colorScheme.onPrimaryContainer)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_microphone),
                        contentDescription = "Play button",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(13.dp)
                            .size(25.dp)
                    )
                }

            }
        }

    }
}