package com.queentylion.gestra.ui.screens.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.queentylion.gestra.ui.screens.conversation.Conversation
import com.queentylion.gestra.ui.screens.navscaffold.NavScaffold
import com.queentylion.gestra.ui.screens.translate.Translate

object Routes {
    const val NavScaffold = "nav_scaffold"
    const val Translate = "translate"
    const val Conversation = "conversation"
}

@Composable
fun Main() {
    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = Routes.NavScaffold,
        modifier = Modifier.fillMaxSize()
    ) {

        composable(
            Routes.NavScaffold,
            enterTransition = {
                when (initialState.destination.route) {
                    Routes.Translate ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Down,
                            animationSpec = tween(300)
                        )

                    Routes.Conversation ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Up,
                            animationSpec = tween(300)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Routes.Translate ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Up,
                            animationSpec = tween(300)
                        )

                    Routes.Conversation ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Down,
                            animationSpec = tween(300)
                        )

                    else -> null
                }
            },
        ) {
            NavScaffold(mainNavController = navController)
        }

        composable(
            Routes.Translate,
            enterTransition = {
                when (initialState.destination.route) {
                    Routes.NavScaffold ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Up,
                            animationSpec = tween(300)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Routes.NavScaffold ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Down,
                            animationSpec = tween(300)
                        )

                    else -> null
                }
            },
        ) {
            Translate(mainNavController = navController)
        }

        composable(
            Routes.Conversation,
            enterTransition = {
                when (initialState.destination.route) {
                    Routes.NavScaffold ->
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Down,
                            animationSpec = tween(300)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Routes.NavScaffold ->
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Up,
                            animationSpec = tween(300)
                        )

                    else -> null
                }
            },
        ) {
            Conversation(mainNavController = navController)
        }
    }
}