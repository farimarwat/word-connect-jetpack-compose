package com.fungiggle.lexilink.components

import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ButtonRound(
    size:Int = 48,
    default:Int,
    variant:Int, onClicked:()->Unit = {}){
    var image by remember{
        mutableStateOf(default)
    }
    Box(modifier = Modifier
        .size(size.dp)
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
    ){
        Image(painter = painterResource(id = image), contentDescription = "Button")
    }
}