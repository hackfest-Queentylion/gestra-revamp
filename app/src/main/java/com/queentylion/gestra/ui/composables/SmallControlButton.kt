package com.queentylion.gestra.ui.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

@Composable
fun SmallControlButton(
    @DrawableRes icon: Int,
    onButtonClick: () -> Unit,
    contentDescription: String,
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(17.dp))
            .clickable {
                onButtonClick()
            }
            .background(MaterialTheme.colorScheme.inversePrimary.copy(0.65f))
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(icon),
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier
                .padding(13.dp)
                .size(20.dp)
        )
    }
}