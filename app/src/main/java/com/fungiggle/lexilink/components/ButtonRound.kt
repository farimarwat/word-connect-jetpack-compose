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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ButtonRound(
    modifier: Modifier,
    default:Int,
    variant:Int, onClicked:(offset:Offset)->Unit = {}){
    var image by remember{
        mutableIntStateOf(default)
    }
    var offset by remember{
        mutableStateOf(Offset.Zero)
    }
    Box(modifier = Modifier
        .onGloballyPositioned {
            offset = Offset(it.positionInRoot().x,it.positionInRoot().y)
        }
        .pointerInteropFilter {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    image = variant
                }

                MotionEvent.ACTION_UP -> {
                    image = default
                    onClicked(offset)
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