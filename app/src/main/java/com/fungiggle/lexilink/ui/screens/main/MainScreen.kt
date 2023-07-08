package com.fungiggle.lexilink.ui.screens.main

import android.content.Intent
import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fungiggle.lexilink.R
import com.fungiggle.lexilink.navigation.DestinationGame

@Composable
fun MainScreen(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxSize()

    ){
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
                    painter = painterResource(id = R.drawable.main_header),
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