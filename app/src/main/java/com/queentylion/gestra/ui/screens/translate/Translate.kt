package com.queentylion.gestra.ui.screens.translate

import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.queentylion.gestra.R
import com.queentylion.gestra.ui.composables.BottomControls
import com.queentylion.gestra.ui.composables.ClickablePanel
import com.queentylion.gestra.ui.composables.ResultBox
import com.queentylion.gestra.ui.screens.main.Routes
import androidx.lifecycle.viewmodel.compose.viewModel
import com.queentylion.gestra.util.ConnectionState
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Translate(
    mainNavController: NavController,
    viewModel: TranslateViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    var isGeminiChecked by remember { mutableStateOf(false) }
    var activeButtonIndex by remember { mutableStateOf(1) }
    var resultText by remember {
        mutableStateOf(if (activeButtonIndex == 1) "Start sign language" else "Start speaking")
    }
    var textColor by remember { mutableStateOf(Color(0xFFD3D3D3)) }

    val context = LocalContext.current

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
                    .weight(1f),
                resultText = resultText,
                textColor = textColor,
                onSpeakerClick = {
                    viewModel.textToSpeech(context, resultText)
                },
                test = viewModel.gloveReceiveManager.initializingMessage.toString()

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
                },
                changeText = {
                    resultText = it
                },
                changeTextColor = {
                    textColor = it
                }
            )
        }
    }
}