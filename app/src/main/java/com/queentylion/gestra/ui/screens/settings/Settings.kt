package com.queentylion.gestra.ui.screens.settings

import android.annotation.SuppressLint
import android.os.Build
import androidx.core.app.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.queentylion.gestra.ui.composables.AnimatedGlove
import dagger.Component
import com.queentylion.gestra.R
import com.queentylion.gestra.ui.screens.main.Routes

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("RestrictedApi")
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun Settings(
    viewModel: SettingsViewModel = hiltViewModel(),
    mainNavController: NavController
) {


//    LaunchedEffect(permissionsState.allPermissionsGranted) {
//        if (permissionsState.allPermissionsGranted) {
//            viewModel.enableBluetooth(activity!!)
//        } else {
//            permissionsState.launchMultiplePermissionRequest()
//        }
//    }
//
//    LaunchedEffect(viewModel.isBluetoothEnabled()) {
//        if (viewModel.isBluetoothEnabled()) {
//            viewModel.initializeGloveConnection()
//        } else {
//            viewModel.enableBluetooth(activity!!)
//        }
//    }
    Surface(
        modifier = Modifier
            .fillMaxWidth() // Fills the available width
            .padding(16.dp) // Add some padding
            .shadow( // Add a shadow effect
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp)
            ),
        color = MaterialTheme.colors.surface // Use MaterialTheme's surface color (usually white)
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // Add some padding
                .drawBehind { // Draw the bottom border
                    val strokeWidth = 1f // Adjust stroke width as needed
                    val color = Color.Gray // Set border color

                    val y = size.height - strokeWidth / 2f // Calculate bottom position

                    drawLine(
                        color = color,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth.toDp().toPx()
                    )
                }
            ){
                Row( // Use Row for horizontal arrangement
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(10.dp).fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(androidx.compose.material3.MaterialTheme.colorScheme.primary)
                    )
                    {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_home),
                            contentDescription = "weiuhdfiuwe",
                            tint = androidx.compose.material3.MaterialTheme.colorScheme.surface,
                            modifier = Modifier
                                .padding(13.dp)
                        )
                    }
                    Column (verticalArrangement = Arrangement.Top){
                        Text(text = "Test")
                        Text(text = "wejhrgfyuegfuier")
                    }
                    Button(onClick = {
                        mainNavController.navigate(Routes.Calibration)
                    }) {
                        Text(text = "Action")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }


}