package com.queentylion.gestra.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.queentylion.gestra.R

@Composable
fun AnimatedGlove(modifier: Modifier = Modifier) {
    val gloveComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.animation_hand
        )
    )

    val gloveProgress by animateLottieCompositionAsState(
        gloveComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )

    LottieAnimation(composition = gloveComposition, progress = gloveProgress, modifier = modifier)
}