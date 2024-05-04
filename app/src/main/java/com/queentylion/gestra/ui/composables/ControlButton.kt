package com.queentylion.gestra.ui.composables

import android.graphics.Paint.Align
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ControlButton(
    @DrawableRes icon: Int,
    onButtonClick: () -> Unit,
    contentTitle: String,
    contentDescription: String,
    isActive: Boolean = true
) {
    Button(
        onClick = { onButtonClick() },
        shape = RoundedCornerShape(13.dp),
        border = BorderStroke(if (isActive) 0.dp else (0.7).dp, MaterialTheme.colorScheme.outline.copy(0.5f)),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isActive) MaterialTheme.colorScheme.secondaryContainer.copy(0.65f) else Color.Transparent
        ),
        modifier = Modifier.size(95.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = contentDescription,
                tint = if (!isActive) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = contentTitle,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 5.dp).fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = if (!isActive) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}