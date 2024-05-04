package com.queentylion.gestra.ui.composables

import android.os.Build.VERSION_CODES.Q
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun ResultBox(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp, top = 30.dp, bottom = 48.dp)
    ) {
        Column(
            modifier = Modifier
//                .shadow(elevation = 1.dp, shape = RoundedCornerShape(5.dp))
//                .clip(RoundedCornerShape(18.dp))
                .fillMaxHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(text = "Contoh")
        }
    }
}