package com.fungiggle.lexilink.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fungiggle.lexilink.ui.screens.gameview.GameScreenViewModel
import com.fungiggle.lexilink.ui.theme.LexiLink_WordPreview

@Composable
fun PreviewWord(modifier: Modifier,
                viewmodel: GameScreenViewModel,
) {

    val word = remember {
        viewmodel.wordtopreview
    }
    val showScale by animateFloatAsState(
        targetValue = if (word.value.isNotEmpty()) 1f else 0f,
        animationSpec = tween(200)
    )
    Box(
        modifier = Modifier
            .scale(showScale)
            .clip(RoundedCornerShape(16.dp))
            .background(LexiLink_WordPreview.copy(alpha = 0.5f))
            .padding(start = 8.dp, end = 8.dp)
            .then(modifier),
    ) {
        Text(
            text = word.value,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center
        )
    }

}