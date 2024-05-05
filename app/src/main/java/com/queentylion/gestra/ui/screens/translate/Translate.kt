package com.queentylion.gestra.ui.screens.translate

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.queentylion.gestra.R
import com.queentylion.gestra.ui.composables.BottomControls
import com.queentylion.gestra.ui.composables.ClickablePanel
import com.queentylion.gestra.ui.composables.ResultBox
import com.queentylion.gestra.ui.screens.main.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Translate(
    mainNavController: NavController
) {
    var isGeminiChecked by remember { mutableStateOf(false) }
    var activeButtonIndex by remember { mutableStateOf(1) }

    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 5.dp
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Glove",
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
                .padding(paddingValues)
                .fillMaxHeight()
        ) {
            ClickablePanel(
                imageRes = R.drawable.img_gemini,
                isWarning = false,
                hasToggle = true,
                isToggleChecked = isGeminiChecked,
                onTogglePressed = {
                    isGeminiChecked = it
                },
                text = "Empower interaction via Gemini",
                contentDescription = "Panel for toggling gemini action"
            )
            ResultBox(
                modifier = Modifier
                    .weight(1f)
            )
            Divider(
                thickness = (0.5).dp,
                modifier = Modifier
                    .offset(y = (-32).dp)
                    .padding(vertical = 25.dp, horizontal = 40.dp)
                    .background(MaterialTheme.colorScheme.outline.copy(0.5f))
            )
            BottomControls(
                activeButtonIndex = activeButtonIndex,
                onRadioClicked = {
                    activeButtonIndex = it
                }
            )
        }
    }
}