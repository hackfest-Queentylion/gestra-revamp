package com.queentylion.gestra.ui.composables

import android.os.Build.VERSION_CODES.Q
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.queentylion.gestra.R


@Composable
fun ResultBox(
    modifier: Modifier = Modifier,
    resultText: String = "Start sign language",
    textColor: Color = Color(0xFFD3D3D3),
    onSpeakerClick: () -> Unit,
    test: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp, bottom = 48.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Result",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            onSpeakerClick()
                        }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_speaker),
                        contentDescription = "Placeholder icon",
                        tint = MaterialTheme.colorScheme.outline.copy(0.9f),
                        modifier = Modifier
                            .padding(7.dp)
                            .size(25.dp)
                    )
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .clickable { }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_star),
                        tint = MaterialTheme.colorScheme.outline.copy(0.9f),
                        contentDescription = "Placeholder icon",
                        modifier = Modifier
                            .padding(7.dp)
                            .size(25.dp)
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .size(100.dp)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 15.dp)
        ) {
            Text(
//                text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                text = resultText,
                fontSize = 25.sp,
                lineHeight = 30.sp,
                color = textColor
            )
            Text(text = test )
        }
    }
}