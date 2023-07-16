package com.fungiggle.lexilink.components

import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ButtonRound(
    modifier: Modifier,
    default:Int,
    variant:Int, onClicked:()->Unit = {}){
    var image by remember{
        mutableIntStateOf(default)
    }
    Box(modifier = Modifier
        .pointerInteropFilter {
            when(it.action){
                MotionEvent.ACTION_DOWN ->{
                    image = variant
                }
                MotionEvent.ACTION_UP ->{
                    image = default
                    onClicked()
                }
            }
            true
        }
        .then(modifier)
    ){
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = image),
            contentDescription = "Button",
            contentScale = ContentScale.Fit
        )
    }
}