package com.fungiggle.lexilink.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.dp
import com.fungiggle.lexilink.R

@Composable
fun BottomBar(
    onHintClicked:(offset:Offset)->Unit ={},
    onShuffleClicked:()->Unit = {}
){

    Row(modifier = Modifier
        .padding(start = 4.dp, end = 4.dp)
        .fillMaxSize(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        val modifier = Modifier
            .padding(4.dp)
            .size(48.dp)
        ButtonRound(
            modifier = modifier,
            default = R.drawable.hint_default,
            variant = R.drawable.hint_variant){ offset ->
            onHintClicked(offset)
        }
        ButtonRound(
            modifier = modifier,
            default = R.drawable.shuffle_default,
            variant = R.drawable.shuffle_variant){
            onShuffleClicked()
        }
    }
}