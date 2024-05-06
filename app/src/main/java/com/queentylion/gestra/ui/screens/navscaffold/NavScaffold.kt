package com.queentylion.gestra.ui.screens.navscaffold

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.queentylion.gestra.Greeting
import com.queentylion.gestra.R
import com.queentylion.gestra.ui.composables.BottomNavigationBar
import com.queentylion.gestra.ui.composables.FullPanel
import com.queentylion.gestra.ui.composables.Screen
import com.queentylion.gestra.ui.composables.SmallPanel
import com.queentylion.gestra.ui.screens.home.Home
import com.queentylion.gestra.ui.screens.settings.Settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavScaffold(
    navViewModel: NavScaffoldViewModel = viewModel(),
    mainNavController: NavController
) {
    val navController = rememberNavController()
    val navUiState = navViewModel.uiState.collectAsState()

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