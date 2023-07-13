package com.fungiggle.lexilink.components

import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fungiggle.lexilink.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview
fun ButtonContinue(onClicked:()->Unit = {}){
    var image by remember{
        mutableIntStateOf(R.drawable.button_continue_default)
    }
    Box(
        modifier = Modifier
            .width(150.dp)
            .height(60.dp)
            .pointerInteropFilter {
                when(it.action){
                    MotionEvent.ACTION_DOWN -> {
                        image = R.drawable.button_continue_variant
                    }
                    MotionEvent.ACTION_UP ->{
                        image = R.drawable.button_continue_default
                        onClicked()
                    }
                }
                true
            },
        contentAlignment = Alignment.Center
    ){
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = image),
            contentDescription = "Continue",
            contentScale = ContentScale.Fit
        )
    }
}