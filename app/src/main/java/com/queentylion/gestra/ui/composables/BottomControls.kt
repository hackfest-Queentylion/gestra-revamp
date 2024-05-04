package com.queentylion.gestra.ui.composables

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.queentylion.gestra.R
import java.util.Locale

@Composable
fun BottomControls(
    activeButtonIndex: Int,
    onRadioClicked: (Int) -> Unit
) {

    val recognitionListener = object: RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {
            TODO("Not yet implemented")
        }

        override fun onBeginningOfSpeech() {
            TODO("Not yet implemented")
        }

        override fun onRmsChanged(rmsdB: Float) {
            TODO("Not yet implemented")
        }

        override fun onBufferReceived(buffer: ByteArray?) {
            TODO("Not yet implemented")
        }

        override fun onEndOfSpeech() {
            TODO("Not yet implemented")
        }

        override fun onError(error: Int) {
            TODO("Not yet implemented")
        }

        override fun onResults(results: Bundle?) {
            TODO("Not yet implemented")
        }

        override fun onPartialResults(partialResults: Bundle?) {
            TODO("Not yet implemented")
        }

        override fun onEvent(eventType: Int, params: Bundle?) {
            TODO("Not yet implemented")
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
                },
                contentTitle = "Gesture",
                contentDescription = "Go to favorites page",
                isActive = (activeButtonIndex == 1)
            )
            ControlButton(
                icon = R.drawable.ic_microphone,
                onButtonClick = {
                    onRadioClicked(2)
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
                            /*TODO*/
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
