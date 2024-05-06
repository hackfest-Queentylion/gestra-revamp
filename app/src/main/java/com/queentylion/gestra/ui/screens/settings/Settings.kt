package com.queentylion.gestra.ui.screens.settings

import android.annotation.SuppressLint
import android.os.Build
import androidx.core.app.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.queentylion.gestra.ui.composables.AnimatedGlove

@SuppressLint("RestrictedApi")
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun Settings(
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val activity = LocalContext.current as? ComponentActivity

    viewModel.initializeBluetooth(context)
    viewModel.requestBluetoothPermissionsAndEnable(activity!!)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(
            text = "Please repeatedly open and close your hands",
            fontSize = 23.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(bottom = 20.dp)
        )
        AnimatedGlove(
            modifier = Modifier.fillMaxHeight(0.5f)
        )
    }
}