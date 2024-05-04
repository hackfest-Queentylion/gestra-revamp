package com.queentylion.gestra.ui.screens.translate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.queentylion.gestra.ui.composables.BottomControls
import com.queentylion.gestra.ui.composables.ResultBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Translate(

) {
    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 5.dp
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Glove",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 22.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { /* TODO: Navigation icon */ }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Go back to home page"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* TODO: Create a profile page */ }) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = "Change language configuration"
                            )
                        }
                    },
                )
            }
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxHeight()
        ) {
            ResultBox(
                modifier = Modifier
                    .weight(1f)
            )
            Divider(
                thickness = (0.5).dp,
                modifier = Modifier
                    .offset(y = (-32).dp)
                    .padding(vertical = 25.dp, horizontal = 40.dp)
                    .background(MaterialTheme.colorScheme.outline.copy(0.5f))
            )
            BottomControls()
        }
    }
}