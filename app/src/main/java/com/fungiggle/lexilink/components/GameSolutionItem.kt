package com.fungiggle.lexilink.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fungiggle.lexilink.models.GameLetter
import com.fungiggle.lexilink.models.GameSolution

@Composable
@Preview
fun GameSolutionItemPreview(){
    val solution = GameSolution(
        letters = listOf(
            GameLetter(label = "C", isvisible = true),
            GameLetter(label = "U", isvisible = true),
            GameLetter(label = "P", isvisible = true)
        )
    )
    GameSolutionItem(solution = solution, 60.dp)
}
@Composable
fun GameSolutionItem(solution: GameSolution, itemsize:Dp){
  Box(
      modifier = Modifier
          .padding(top=4.dp, bottom = 4.dp),
      contentAlignment = Alignment.Center
  ){
      val modifier = Modifier
          .padding(2.dp)
          .size(itemsize)
      LazyRow{
          items(solution.letters){ letter ->
              GameLetterItem(modifier = modifier,letter = letter)
          }
      }
  }
}