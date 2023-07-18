package com.fungiggle.lexilink.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fungiggle.lexilink.R

@Composable
fun ButtonWatchAd(
    modifier: Modifier = Modifier,
    showHand: Boolean = false,
    onClicked: () -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotationAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                delayMillis = 3000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse,
        ),
    )
    Column(
        modifier = Modifier
            .then(modifier),
        horizontalAlignment = Alignment.End
    ) {
        Box(
            modifier = Modifier
                .graphicsLayer(
                    rotationX = rotationAnimation,
                ),
        ) {
            Image(
                modifier = Modifier
                    .size(60.dp)
                    .clickable {
                        onClicked()
                    },
                painter = painterResource(id = R.drawable.button_watch_ad),
                contentDescription = "Ad",
                contentScale = ContentScale.Fit
            )
        }
        if(showHand){
            Box(
                modifier = Modifier
                    .size(60.dp),
            ) {
                val composition by rememberLottieComposition(
                    spec = LottieCompositionSpec.RawRes(R.raw.hand)
                )
                val progress by animateLottieCompositionAsState(
                    composition = composition,
                    iterations = 10
                )
                LottieAnimation(composition = composition, progress = { progress })
            }
        }
    }
}