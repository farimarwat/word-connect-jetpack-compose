package com.fungiggle.lexilink.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fungiggle.lexilink.R
import com.fungiggle.lexilink.models.GameLetter
import java.util.UUID

@Composable
@Preview
fun GameLetterItemPreview(){
    val letter = GameLetter(
        label = "A",
        isvisible = false
    )
    GameLetterItem(letter = letter)
}
@Composable
fun GameLetterItem(modifier: Modifier=Modifier,letter: GameLetter){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.key_solution_default),
            contentDescription = "Background",
            contentScale = ContentScale.Fit
        )
        val scaleSolution by animateFloatAsState(
            targetValue = if(letter.isvisible) 1f else 0f,
            animationSpec = tween(200)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .scale(scaleSolution)
            ,
            contentAlignment = Alignment.Center
        ){
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.key_solution_variant),
                contentDescription = "Background",
                contentScale = ContentScale.Fit
            )
            Text(
                text = letter.label,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}