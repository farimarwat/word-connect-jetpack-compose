package com.fungiggle.lexilink.components

import android.util.Log
import androidx.compose.animation.core.Animatable

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fungiggle.lexilink.R
import kotlin.random.Random

@Composable
fun ShowLetterPopup(
    hintOffset:Offset,letterOffset:Offset,
){
    val SPEED = 600
    val x = remember { Animatable(hintOffset.x) }
    val y = remember { Animatable(hintOffset.y) }
    LaunchedEffect(letterOffset) {
        x.animateTo(
            targetValue = letterOffset.x,
            animationSpec = tween(durationMillis = SPEED)
        )
    }
    LaunchedEffect(letterOffset) {
        y.animateTo(
            targetValue = letterOffset.y,
            animationSpec = tween(durationMillis = SPEED)
        )
    }
    Box(
        modifier = Modifier
            .size(60.dp)
            .offset {
                    IntOffset(x.value.toInt(),y.value.toInt())
            }
           ,
        contentAlignment = Alignment.Center
    ){
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.star)
        )
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = 3
        )
        LottieAnimation(composition = composition, progress = { progress})
    }
}