package com.queentylion.gestra.ui.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ClickablePanel(
    @DrawableRes imageRes: Int,
    isWarning: Boolean,
    hasToggle: Boolean,
    isToggleChecked: Boolean,
    onTogglePressed: (Boolean) -> Unit,
    text: String,
    contentDescription: String,
) {
    Button(
        onClick = { onTogglePressed(!isToggleChecked) },
        shape = RoundedCornerShape(13.dp),
        border = BorderStroke(
            (0.7).dp,
            MaterialTheme.colorScheme.outline.copy(0.5f)
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp, top = 20.dp, bottom = 24.dp),
        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 15.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(if (isWarning) MaterialTheme.colorScheme.error.copy(0.5f) else Color.Transparent)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = contentDescription,
                    modifier = Modifier
                        .size(33.dp)
                )
                Text(text = text, modifier = Modifier.padding(start = 12.dp))
            }
            if (hasToggle) {
                Switch(
                    checked = isToggleChecked,
                    onCheckedChange = {
                        onTogglePressed(it)
                    },
                )
            }
        }
    }
}