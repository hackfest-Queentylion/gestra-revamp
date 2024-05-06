package com.queentylion.gestra.ui.screens.navscaffold

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.queentylion.gestra.Greeting
import com.queentylion.gestra.R
import com.queentylion.gestra.ui.composables.BottomNavigationBar
import com.queentylion.gestra.ui.composables.FullPanel
import com.queentylion.gestra.ui.composables.Screen
import com.queentylion.gestra.ui.composables.SmallPanel
import com.queentylion.gestra.ui.screens.home.Home
import com.queentylion.gestra.util.ConnectionState
import javax.inject.Inject
import com.queentylion.gestra.ui.screens.settings.Settings

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun NavScaffold(
    navViewModel: NavScaffoldViewModel = hiltViewModel(),
    mainNavController: NavController,
//    activity: Activity = LocalContext.current as Activity
) {
    val navController = rememberNavController()
    val navUiState = navViewModel.uiState.collectAsState()

//    val bluetoothEnabledState by navViewModel.bluetoothEnabledState.observeAsState()
//    val bluetoothPermissionState by navViewModel.bluetoothPermissionState.observeAsState()
//
//    val permissionState = rememberMultiplePermissionsState(
//        permissions = listOf(
//            Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT,
//            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
//        )
//    )
//    val lifecycleOwner = LocalLifecycleOwner.current

//    DisposableEffect(
//        key1 = lifecycleOwner,
//        effect = {
//            val observer = LifecycleEventObserver { _, event ->
//                if (event == Lifecycle.Event.ON_START) {
//                    permissionState.launchMultiplePermissionRequest()
//                    if (permissionState.allPermissionsGranted && navViewModel.gloveReceiveManager.connectionState == ConnectionState.Disconnected) {
//                        navViewModel.gloveReceiveManager.reconnect()
//                    }
//                }
//                if (event == Lifecycle.Event.ON_STOP) {
//                    if (navViewModel.gloveReceiveManager.connectionState == ConnectionState.Connected) {
//                        navViewModel.gloveReceiveManager.disconnect()
//                    }
//                }
//            }
//            lifecycleOwner.lifecycle.addObserver(observer)
//
//            onDispose {
//                lifecycleOwner.lifecycle.removeObserver(observer)
//            }
//        }
//    )

//    LaunchedEffect(Unit) {
////        permissionState.launchMultiplePermissionRequest()
//        navViewModel.checkBluetoothPermission(activity)
//        navViewModel.checkBluetoothEnabled()
//        Log.d("MMEK1", bluetoothEnabledState.toString())
//        Log.d("MMEK2", bluetoothPermissionState.toString())
//    }

//    when {
//        bluetoothPermissionState == false -> {
//            Log.d("MMEK2", "ememek")
//            navViewModel.requestBluetoothPermission(activity)
//            if (bluetoothEnabledState == false) {
//                navViewModel.requestBluetoothEnable(activity)
//            }
//        }
//
//        bluetoothEnabledState == false -> {
//            Log.d("MMEK1", "maks")
//            navViewModel.requestBluetoothEnable(activity)
//        }
//    }

    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 5.dp
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = navUiState.value.currentDestinationLabel,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 22.sp
                        )
                    },
                    actions = {
                        IconButton(onClick = { /* TODO: Create a profile page */ }) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                onNavButtonPressed = { label ->
                    navViewModel.setNavigationScreen(label)
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(
                Screen.Conversation.route,
                enterTransition = {
                    EnterTransition.None
                },
                exitTransition = {
                    ExitTransition.None
                },
            ) { Greeting(name = "Conversation") }
            composable(
                Screen.Home.route,
                enterTransition = {
                    when (initialState.destination.route) {
                        "setting" ->
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(300)
                            )

                        else -> null
                    }
                },
                exitTransition = {
                    when (targetState.destination.route) {
                        "setting" ->
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(300)
                            )

                        else -> null
                    }
                },
            ) {
                Home(mainNavController = mainNavController)
//                Greeting(name = "This is home")
            }
            composable(
                Screen.Setting.route,
                enterTransition = {
                    when (initialState.destination.route) {
                        "home" ->
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(300)
                            )

                        else -> null
                    }
                },
                exitTransition = {
                    when (targetState.destination.route) {
                        "home" ->
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(300)
                            )

                        else -> null
                    }
                },
            ) {
                Settings()
            }
        }
    }
}