package com.fungiggle.lexilink.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fungiggle.lexilink.R
import kotlin.random.Random

@Composable
fun LeafMain(){
    val infiniteTransition = rememberInfiniteTransition()
    val rotationAnimation by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 4000,
                delayMillis = 4000,
                easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
    )
    Image(
        modifier = Modifier
            .size(160.dp)
            .graphicsLayer(
                rotationZ = rotationAnimation,
                transformOrigin = TransformOrigin(
                    pivotFractionX = 0.1f,
                    pivotFractionY = 1f
                )
            )
        ,
        painter = painterResource(id = R.drawable.main_bottom_leaf),
        contentDescription = "Bottom Leaf")
}