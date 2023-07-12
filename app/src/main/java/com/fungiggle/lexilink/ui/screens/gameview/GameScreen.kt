package com.fungiggle.lexilink.ui.screens.gameview

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fungiggle.lexilink.R
import com.fungiggle.lexilink.components.BottomBar
import com.fungiggle.lexilink.components.KeyPad
import com.fungiggle.lexilink.components.SolutionPad
import com.fungiggle.lexilink.components.TopBar
import com.fungiggle.lexilink.models.GameLetter
import com.fungiggle.lexilink.ui.theme.LexiLink_WordPreview
import com.fungiggle.lexilink.utils.TAG

@Composable
@Preview(
    name = "Large", widthDp = 360, heightDp = 740
)
fun MainViewPreviewLarge() {
    GameScreen()
}

@Composable
@Preview(
    name = "small", widthDp = 360, heightDp = 580
)
fun MainViewPreviewSmall() {
    GameScreen()
}

@Composable
fun GameScreen() {
    val viewModel: GameScreenViewModel = hiltViewModel()
    val list = viewModel.listletters.collectAsState()
    var shuffle by remember {
        mutableStateOf(false)
    }
    var letterUpdated by remember {
        mutableStateOf<GameLetter?>(null)
    }

    LaunchedEffect(Unit) {
        val letters = "CUP"
        val solutions = listOf("CUP", "UP", "FUN")
        viewModel.prepareLevel(letters, solutions)
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.gameview_background),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            //Upper
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    //Top Menu
                    Box(
                        modifier = Modifier
                            .weight(0.2f)
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxSize(),
                            painter = painterResource(id = R.drawable.gameview_topbar_menu),
                            contentDescription = "Solution Pad",
                            contentScale = ContentScale.FillBounds
                        )
                        TopBar()
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxSize(),
                            painter = painterResource(id = R.drawable.gameview_solutionpad),
                            contentDescription = "Solution Pad",
                            contentScale = ContentScale.Fit
                        )

                        //Solution pad goes here
                        SolutionPad(letter = letterUpdated, viewmodel = viewModel)

                    }
                }
            }

            //Lower
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(id = R.drawable.fabric),
                    contentDescription = "Fabric",
                    contentScale = ContentScale.FillBounds
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    //Selection pad
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {

                        KeyPad(
                            shuffle = shuffle,
                            list = list.value,
                            onSetShuffle = {
                                shuffle = false
                            },
                            onSelected = { list ->
                                var word = ""
                                list.forEach { btn ->
                                    word += btn.label
                                }
                                viewModel.updateWordToPreview(word)
                            },
                            onCompleted = { list ->
                                val map = list.map {
                                    it.label
                                }
                                viewModel.wordtopreview.value = ""
                            }
                        )
                    }


                    //Bottom
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(0.2f)
                    ) {

                        Image(
                            modifier = Modifier
                                .fillMaxSize(),
                            painter = painterResource(id = R.drawable.gameview_bottombar),
                            contentDescription = "Bottom Bar",
                            contentScale = ContentScale.FillBounds
                        )
                        BottomBar(
                            onHintClicked = {
                                val result = viewModel.showLetter()
                                if (result != null) {
                                    Log.e(TAG, "Letter: $result")
                                    letterUpdated = result
                                }
                            },
                            onShuffleClicked = {
                                shuffle = true
                                list.value.shuffle()
                            }
                        )
                    }
                }
            }
        }

        //Preview
        PreviewWord(
            modifier = Modifier
                .align(Alignment.Center),
            viewmodel = viewModel
        )

    }
}

@Composable
fun PreviewWord(modifier: Modifier, viewmodel: GameScreenViewModel) {
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