package com.fungiggle.lexilink.components

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fungiggle.lexilink.R
import com.fungiggle.lexilink.models.GameLetter
import com.fungiggle.lexilink.models.GameSolution
import com.fungiggle.lexilink.ui.screens.gameview.GameScreenViewModel
import com.fungiggle.lexilink.utils.TAG

@Composable
fun SolutionPad(solutions:List<GameSolution>){
    val height = LocalConfiguration.current.screenHeightDp
    var itemSize = 48.dp
    if(height <=600){ //small screens
        itemSize = if(solutions.size <4){
            50.dp
        } else if(solutions.size == 4){
            40.dp
        } else {
            32.dp
        }
    } else { // large screen
        itemSize = if(solutions.size <4){
            50.dp
        } else if(solutions.size == 4){
            48.dp
        } else {
            32.dp
        }
    }
    //Grouping Solutions
    val SECTION_1 = "section1"
    val SECTION_2 = "section2"
    val SECTION_3 = "section3"
    val groupedSolutions = solutions.groupBy { solution ->
        when(solution.letters.size){
            in Int.MIN_VALUE..3 -> SECTION_1
            in 4..5 ->SECTION_2
            else -> SECTION_3
        }
    }
    var section1 = groupedSolutions.getOrElse(SECTION_1,{null})
    var section2 = groupedSolutions.getOrElse(SECTION_2,{null})
    val section3 = groupedSolutions.getOrElse(SECTION_3,{null})
    if((section2 != null) || (section3 != null)){
       if(height <= 600){
           itemSize = 40.dp
       } else {
           itemSize = 48.dp
       }
    }
    if (section1 != null && section2 != null && (section1.size + section2.size) <= 4) {
        section1 = section1 + section2
        section2 = null
    }

    Box(
        modifier = Modifier
            .padding(start=16.dp, end = 16.dp)
        ,
    ){

        Log.e(TAG,"SolutionPad Drawn")
        Column() {
            Row(
            ){
                val colModifier = Modifier.padding(start=4.dp,end=4.dp)
                section1?.let { sec ->
                    LazyColumn(
                        modifier = colModifier,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        items(sec){ sol ->
                            GameSolutionItem(solution = sol,
                                itemsize =itemSize)
                        }
                    }
                }
                section2?.let { sec ->
                    LazyColumn(
                        modifier = colModifier,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        items(sec){sol ->
                            GameSolutionItem(solution = sol,
                                itemsize =itemSize
                            )
                        }
                    }
                }

            }

            section3?.let { sec ->
                LazyColumn(
                ){
                    items(sec){sol ->
                        GameSolutionItem(solution = sol,
                            itemsize =itemSize,
                        )
                    }
                }
            }
        }

    }
}