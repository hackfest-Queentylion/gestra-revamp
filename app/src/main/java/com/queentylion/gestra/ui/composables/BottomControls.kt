package com.queentylion.gestra.ui.composables

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
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.queentylion.gestra.R

@Composable
fun BottomControls() {
    var activeButtonIndex by remember { mutableStateOf(1) }

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
                    activeButtonIndex = 0
                },
                contentTitle = "Text",
                contentDescription = "Go to favorites page",
                isActive = (activeButtonIndex == 0)
            )
            ControlButton(
                icon = R.drawable.ic_gesture_control,
                onButtonClick = {
                    activeButtonIndex = 1
                },
                contentTitle = "Gesture",
                contentDescription = "Go to favorites page",
                isActive = (activeButtonIndex == 1)
            )
            ControlButton(
                icon = R.drawable.ic_microphone,
                onButtonClick = {
                    activeButtonIndex = 2
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
                onButtonClick = { /*TODO*/ },
                contentDescription = "Go to favorites page"
            )
            Box(
                modifier = Modifier
                    .offset(y = (-32).dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            /*TODO*/
                        }
                        .background(MaterialTheme.colorScheme.onPrimaryContainer)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_gesture_action),
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