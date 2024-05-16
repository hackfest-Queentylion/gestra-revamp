package com.queentylion.gestra.ui.screens.calibration

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.queentylion.gestra.ui.composables.AnimatedGlove

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("RestrictedApi")
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun Calibration (
    viewModel: CalibrationViewModel = hiltViewModel(),
    mainNavController: NavController
) {

    val context = LocalContext.current
    val activity = LocalContext.current as? ComponentActivity
    val requiredPermissions = listOf(
        android.Manifest.permission.BLUETOOTH_SCAN,
        android.Manifest.permission.BLUETOOTH_CONNECT,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    val permissionsState = rememberMultiplePermissionsState(requiredPermissions)

    LaunchedEffect(
        key1 = permissionsState.allPermissionsGranted,
        key2 = viewModel.isBluetoothEnabled()
    ) {
        if (permissionsState.allPermissionsGranted && viewModel.isBluetoothEnabled()) {
            viewModel.initializeGloveConnection()
        } else if (permissionsState.allPermissionsGranted) {
            viewModel.enableBluetooth(activity!!)
        } else if (viewModel.isBluetoothEnabled()) {
            permissionsState.launchMultiplePermissionRequest()
        } else {
            permissionsState.launchMultiplePermissionRequest()
        }
    }

    viewModel.initializeBluetooth(context)
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
            Text(
                text = viewModel.gloveReceiveManager.initializingMessage.toString()
            )
            AnimatedGlove(
                modifier = Modifier.fillMaxHeight(0.5f)
            )
        }
}