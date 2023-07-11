package com.fungiggle.lexilink.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fungiggle.lexilink.R

@Composable
fun BottomBar(
    onHintClicked:()->Unit ={},
    onShuffleClicked:()->Unit = {}
){
    Row(modifier = Modifier
        .padding(start = 4.dp, end = 4.dp)
        .fillMaxSize(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        ButtonRound(
            default = R.drawable.hint_default,
            variant = R.drawable.hint_variant){
            onHintClicked()
        }
        ButtonRound(
            default = R.drawable.shuffle_default,
            variant = R.drawable.shuffle_variant){
            onShuffleClicked()
        }
    }
}