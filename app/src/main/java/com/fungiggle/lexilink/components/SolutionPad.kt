package com.fungiggle.lexilink.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fungiggle.lexilink.models.GameSolution

@Composable
fun SolutionPad(solutions:List<GameSolution>){

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
                            GameSolutionItem(solution = sol, itemsize =itemsize )
                        }
                    }
                }
                section2?.let { sec ->
                    LazyColumn(
                        modifier = colModifier,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        items(sec){sol ->
                            GameSolutionItem(solution = sol, itemsize =itemsize )
                        }
                    }
                }

            }
            section3?.let { sec ->
                LazyColumn(
                ){
                    items(sec){sol ->
                        GameSolutionItem(solution = sol, itemsize =itemsize )
                    }
                }
            }
        }
    }
}