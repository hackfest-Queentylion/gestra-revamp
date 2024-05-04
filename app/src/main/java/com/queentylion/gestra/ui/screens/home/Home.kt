package com.queentylion.gestra.ui.screens.home

import android.provider.Settings.Panel
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.queentylion.gestra.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.queentylion.gestra.ui.composables.FullPanel
import com.queentylion.gestra.ui.composables.SmallPanel

data class PanelData(
    val text: String,
    val backgroundColor: Color,
    val reversed: Boolean,
    @DrawableRes val imgResource: Int
)

@Composable
fun Home() {
    val panelItems = listOf(
        PanelData(
            "Voice Wizard",
            MaterialTheme.colorScheme.secondary,
            true,
            R.drawable.img_smallpanel_mic_background
        ),
        PanelData(
            "Magic Gloves",
            MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f),
            true,
            R.drawable.img_smallpanel_magic_background
        ),
        PanelData(
            "Sign Mastery",
            MaterialTheme.colorScheme.inversePrimary,
            false,
            R.drawable.img_smallpanel_gradhat_background
        ),
        PanelData(
            "Quick Notes",
            Color(0xFFFBBC12),
            false,
            R.drawable.img_smallpanel_write_background
        ),
    )

    Column(
        modifier = Modifier
            .padding(start = 16.dp, top = 20.dp, end = 16.dp)
            .fillMaxHeight()
    ) {
        FullPanel()
        Spacer(modifier = Modifier.size(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(panelItems) { panelData ->
                SmallPanel(data = panelData)
            }
        }
    }

}