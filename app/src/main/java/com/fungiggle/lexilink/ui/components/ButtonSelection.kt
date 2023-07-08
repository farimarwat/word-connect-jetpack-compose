package com.fungiggle.lexilink.ui.components

import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fungiggle.lexilink.R
import com.fungiggle.lexilink.utils.TAG

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ButtonSelection(
    modifier: Modifier = Modifier,
    size: Int,
    alphabet: String,
    offset:Offset,
    onMoved: (character: String) -> Unit = {},
) {
    var image by remember { mutableStateOf(R.drawable.selection_button_default) }

    Box(
        modifier = modifier
            .size(size.dp)
            .offset(x=100.dp,y=100.dp)
            .pointerInteropFilter { motionEvent ->
                when (motionEvent.action) {


                }
                true
            }
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = image),
            contentDescription = "Select",
            contentScale = ContentScale.Fit
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = alphabet
        )
    }
}