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
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.fungiggle.lexilink.ui.components.BottomBar
import com.fungiggle.lexilink.ui.components.KeyPad
import com.fungiggle.lexilink.ui.components.TopBar
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
    val list = viewModel.lettersList
    var shuffle by remember {
        mutableStateOf(false)
    }
    val wordToPreview by  viewModel.wordtopreview.observeAsState()
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
                    //Selection Pad
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxSize(),
                            painter = painterResource(id = R.drawable.gameview_solutionpad),
                            contentDescription = "Solution Pad",
                            contentScale = ContentScale.Fit
                        )
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
                            list = list,
                            onSetShuffle = {
                                shuffle = false
                            },
                            onSelected = { btn ->
                                Log.e(TAG, "Label: ${btn.label}")
                                viewModel.updateWordToPreview(btn.label)
                            },
                            onCompleted = { list ->
                                val map = list.map {
                                    it.label
                                }
                                viewModel.clearWordToPreview()
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

                            },
                            onShuffleClicked = {
                                shuffle = true
                                list.shuffle()
                            }
                        )
                    }
                }
            }
        }

        //Preview
        wordToPreview?.let {
            PreviewWord(
                modifier = Modifier
                    .align(Alignment.Center),
                word =it
            )
        }

    }
}

@Composable
fun PreviewWord(modifier: Modifier, word: String) {
    val showScale by animateFloatAsState(
        targetValue = if (word.isNotEmpty()) 1f else 0f,
        animationSpec = tween(1000)
    )
    if (word.isNotEmpty()) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(LexiLink_WordPreview.copy(alpha = 0.5f))
                .padding(start = 8.dp, end = 8.dp)
                .scale(showScale)
                .then(modifier),
        ) {
            Text(
                text = word,
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}