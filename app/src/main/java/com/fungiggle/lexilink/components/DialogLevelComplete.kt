package com.fungiggle.lexilink.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fungiggle.lexilink.R
import com.fungiggle.lexilink.ui.theme.LexiLink_Background

@Composable
@Preview
fun DialogLevelComplete(onOkClicked:()->Unit = {}){
    Dialog(onDismissRequest = { }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ){
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier =Modifier.size(300.dp),
                    painter = painterResource(id = R.drawable.level_cleared),
                    contentDescription = "Level Cleared",
                    contentScale = ContentScale.Fit
                )
                ButtonContinue{
                    onOkClicked()
                }
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
}