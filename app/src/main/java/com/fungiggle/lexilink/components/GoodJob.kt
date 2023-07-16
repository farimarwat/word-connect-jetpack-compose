package com.fungiggle.lexilink.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fungiggle.lexilink.R

@Composable
fun GoodJob(modifier: Modifier){
    Box(
        modifier = Modifier
            .size(300.dp)
            .background(Color.Transparent)
            .then(modifier)
        ,
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.good_job),
                contentDescription = "Level Cleared",
                contentScale = ContentScale.Fit
            )
        }
        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(R.raw.stars_show_letter)
        )
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = 3
        )
        LottieAnimation(composition = composition, progress = { progress})
    }
}