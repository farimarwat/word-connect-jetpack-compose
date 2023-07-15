package com.fungiggle.lexilink.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.fungiggle.lexilink.R
@Composable
@Preview(
    name = "Large", widthDp = 360, heightDp = 740
)
fun AnimatedOverlayP(){
    AnimatedOverlay(animate = true)
}
@Composable
fun AnimatedOverlay(animate:Boolean){
    Column(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            modifier = Modifier
                .weight(1f),
            visible = animate,
            enter = slideInVertically(),
            exit = slideOutVertically() + shrinkOut()
            ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.overlay) , contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
        }
        AnimatedVisibility(
            modifier = Modifier
                .weight(1f),
            visible = animate,
            enter = slideInHorizontally(),
            exit = slideOutHorizontally() + shrinkOut()
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.overlay) , contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
        }
    }

}