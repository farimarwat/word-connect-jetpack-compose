package com.fungiggle.lexilink.ui.screens.main

import android.content.Intent
import android.graphics.Color.alpha
import android.view.MotionEvent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.fungiggle.lexilink.R
import com.fungiggle.lexilink.navigation.DestinationGame
import kotlinx.coroutines.delay

@Composable
@Preview
fun MainScreenPreview(){
    MainScreen(rememberNavController())
}
@Composable
fun MainScreen(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxSize()

    ){
        val infiniteTransition = rememberInfiniteTransition()
        val rotationAnimation = infiniteTransition.animateFloat(
            initialValue = -20f,
            targetValue = 20f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = LinearOutSlowInEasing),
                repeatMode = RepeatMode.Reverse,
            ),
        )
        //background
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.main_background),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ){
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp)
                    ,
                    painter = painterResource(id =R.drawable.main_rect_bamboo),
                    contentDescription = "Main Header",
                )

                Image(
                    modifier = Modifier
                        .size(220.dp)
                        .padding(top = 16.dp)
                        .align(Alignment.Center)
                    ,
                    painter = painterResource(id =R.drawable.main_rect_board),
                    contentDescription = "Main Header",
                    contentScale = ContentScale.Fit
                )
                Column(
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.Center)
                        .rotate(rotationAnimation.value)
                    ,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                        ,
                        painter = painterResource(id = R.drawable.main_rect_lexi),
                        contentDescription = "Lexi",
                        contentScale = ContentScale.Fit
                    )
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                        ,
                        painter = painterResource(id = R.drawable.main_rect_link),
                        contentDescription = "Lexi",
                        contentScale = ContentScale.Fit
                    )
                }
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(180.dp)
                        .padding(top = 16.dp)
                        .align(Alignment.TopCenter)
                    ,
                    painter = painterResource(id =R.drawable.main_rect_leafs),
                    contentDescription = "Main Header",
                )
            }

            //button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f),
                contentAlignment = Alignment.Center
            ){
                ButtonPlay(navController)
            }

            //Bottom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
            ){
                BottomBar()
            }
        }
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ButtonPlay(navController: NavController){
    var image by remember {
        mutableStateOf(R.drawable.main_play_default)
    }
    Box(
        modifier = Modifier
            .pointerInteropFilter {
                when(it.action){
                    MotionEvent.ACTION_DOWN ->{
                        image = R.drawable.main_play_clicked
                    }
                    MotionEvent.ACTION_UP ->{
                        image = R.drawable.main_play_default
                        navController.navigate(DestinationGame.route)
                    }
                }
                true
            }
    ){
        Image(
            modifier = Modifier
                .size(200.dp),
            painter = painterResource(id = image),
            contentDescription = "Play Default")
    }
}
@Composable
fun BottomBar(){
    Row(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ){
        Image(
            modifier = Modifier
                .size(160.dp),
            painter = painterResource(id = R.drawable.main_bottom_leaf),
            contentDescription = "Bottom Leaf")
        ButtonSettings()

    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ButtonSettings(){
    var image by remember {
        mutableStateOf(R.drawable.main_settings_default)
    }
    Box(
        modifier = Modifier
            .padding(32.dp)
            .size(60.dp)
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        image = R.drawable.main_settings_clicked
                    }

                    MotionEvent.ACTION_UP -> {
                        image = R.drawable.main_settings_default
                    }
                }
                true
            }
    ){
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = image),
            contentDescription = "Bottom Leaf")
    }
}

private fun getRandomTranslationY(): Float {
    // Adjust the range of random translation as needed
    return (-20..20).random().toFloat()
}
