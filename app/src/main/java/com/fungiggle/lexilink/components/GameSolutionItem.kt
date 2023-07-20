package com.fungiggle.lexilink.components

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fungiggle.lexilink.models.GameLetter
import com.fungiggle.lexilink.models.GameSolution
import com.fungiggle.lexilink.ui.screens.gameview.GameScreenViewModel
import com.fungiggle.lexilink.utils.TAG


@Composable
fun GameSolutionItem(
    solution: GameSolution,
    itemsize: Dp,
    solutions: List<GameSolution>
) {
    val rotate by animateFloatAsState(
        targetValue = if (solution.iscompleted && solution.animate) 360f else 0f,
        animationSpec = tween(300)
    )
    Box(
        modifier = Modifier
            .padding(top = 4.dp, bottom = 4.dp)
            .graphicsLayer {
                rotationX = rotate
            },
        contentAlignment = Alignment.Center
    ) {
        val modifier = Modifier
            .padding(2.dp)
            .size(itemsize)
        LazyRow {
            itemsIndexed(solution.letters) { index, letter ->
                GameLetterItem(modifier = modifier, letter = letter) { offset ->
                    val solutionindex = solutions.indexOf(solution)
                    if (solutionindex != -1) {
                        solutions[solutionindex].letters[index] = letter.copy(offset = offset)
                    }

                }
            }
        }
    }
}