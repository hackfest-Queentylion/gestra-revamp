package com.queentylion.gestra.ui.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.queentylion.gestra.R
import androidx.compose.ui.platform.LocalDensity

sealed class Screen(val route: String, @DrawableRes val icon: Int, val label: String) {
    data object Conversation : Screen("conversation", R.drawable.ic_conversation, "Conversation")
    data object Home : Screen("home", R.drawable.ic_home, "Home")
    data object Setting : Screen("setting", R.drawable.ic_setting, "Setting")
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    Surface(
        elevation = 10.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = 12.dp)
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination
            val items = listOf(
                Screen.Conversation,
                Screen.Home,
                Screen.Setting
            )

            items.forEach { item ->
                val color =
                    if (currentRoute?.hierarchy?.any { it.route == item.route } == true) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(
                            0.3F
                        )
                    }
                val interactionSource = MutableInteractionSource()

                IconButton(
                    modifier = Modifier.indication(interactionSource, indication = null),
                    interactionSource = interactionSource,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(item.icon),
                        contentDescription = item.label,
                        tint = color,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }

    }
}