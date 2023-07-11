package com.fungiggle.lexilink.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
    val colCount = if(solutions.size > 4) 2 else 1

    val itemsize = if(solutions.size <4){
        60.dp
    } else if(solutions.size == 4){
        48.dp
    } else {
        32.dp
    }
    if(colCount == 1){
        Box(){
            LazyColumn{
                items(solutions){ sol ->
                    GameSolutionItem(solution = sol, itemsize =itemsize )
                }
            }
        }
    } else {
        val splitted = solutions.chunked((solutions.size+1)/2)
        Box {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val modifier =  Modifier
                    .padding(start = 8.dp, end=8.dp)
                LazyColumn(modifier = modifier){
                    items(splitted[0]){ sol ->
                        GameSolutionItem(solution = sol, itemsize =itemsize )
                    }
                }
                LazyColumn(modifier = modifier){
                    items(splitted[1]){ sol ->
                        GameSolutionItem(solution = sol, itemsize =itemsize )
                    }
                }
            }
        }
    }
}