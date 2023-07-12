package com.fungiggle.lexilink.components

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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fungiggle.lexilink.R
import com.fungiggle.lexilink.models.GameLetter
import com.fungiggle.lexilink.ui.screens.gameview.GameScreenViewModel

@Composable
fun SolutionPad(
    letter:GameLetter?,
    viewmodel:GameScreenViewModel){

    val solutions = viewmodel.listSolutions
    val itemsize = if(solutions.size <4){
        60.dp
    } else if(solutions.size == 4){
        48.dp
    } else {
        32.dp
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
    val section1 = groupedSolutions.getOrElse(SECTION_1,{null})
    val section2 = groupedSolutions.getOrElse(SECTION_2,{null})
    val section3 = groupedSolutions.getOrElse(SECTION_3,{null})

    Box(
        modifier = Modifier
            .padding(start=16.dp, end = 16.dp)
        ,
    ){

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
                                itemsize =itemsize,
                                viewmodel = viewmodel )
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
                                itemsize =itemsize,
                                viewmodel = viewmodel
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
                            itemsize =itemsize,
                            viewmodel = viewmodel
                        )
                    }
                }
            }
        }

    }
   /* letter?.offset?.let { offset ->
        showStars = true
        LottieAnimation(
            composition = composition,
            progress = {
                progress
            },
            modifier = Modifier
                .alpha(alphaStars)
                .offset {
                    IntOffset(offset.x.toInt(),offset.y.toInt())
                }
        )
    }*/
}