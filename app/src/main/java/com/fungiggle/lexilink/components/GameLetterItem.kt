package com.fungiggle.lexilink.components

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fungiggle.lexilink.R
import com.fungiggle.lexilink.models.GameLetter
import com.fungiggle.lexilink.utils.TAG
import java.util.UUID

@Composable
fun GameLetterItem(
    modifier: Modifier = Modifier,
    letter: GameLetter
) {
    var shouldRotate by remember {
        mutableStateOf(false)
    }
    val rotateSolution by animateFloatAsState(
        targetValue = if(shouldRotate) 360f else 0f,
        animationSpec = tween(200)
    ){
        //shouldRotate = false
    }

    Box(
        modifier = Modifier
            .rotate(rotateSolution)
            .then(modifier)
            ,
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.key_solution_default),
            contentDescription = "Background",
            contentScale = ContentScale.Fit
        )
        val scaleSolution by animateFloatAsState(
            targetValue = if (letter.isvisible) 1f else 0f,
            animationSpec = tween(200)
        ){
            shouldRotate = true
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .scale(scaleSolution)
            ,
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.key_solution_variant),
                contentDescription = "Background",
                contentScale = ContentScale.Fit
            )
            Text(
                text = letter.label,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}