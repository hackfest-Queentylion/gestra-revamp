package com.queentylion.gestra.ui.screens.translate

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.queentylion.gestra.Greeting
import com.queentylion.gestra.ui.composables.BottomControls
import com.queentylion.gestra.ui.composables.ResultBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Glove(

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
                        IconButton(onClick = { /* do something */ }) {
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
            BottomControls()
        }
    }
}