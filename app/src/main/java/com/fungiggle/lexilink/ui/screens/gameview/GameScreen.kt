package com.fungiggle.lexilink.ui.screens.gameview

import android.media.MediaPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.farimarwat.picloo.utils.GEMS_TO_CONSUME
import com.farimarwat.picloo.utils.GemShopManager
import com.fungiggle.lexilink.R
import com.fungiggle.lexilink.components.AnimatedOverlay
import com.fungiggle.lexilink.components.BottomBar
import com.fungiggle.lexilink.components.ButtonWatchAd
import com.fungiggle.lexilink.components.DialogLevelComplete
import com.fungiggle.lexilink.components.GoodJob
import com.fungiggle.lexilink.components.KeyPad
import com.fungiggle.lexilink.components.PreviewWord
import com.fungiggle.lexilink.components.SolutionPad
import com.fungiggle.lexilink.components.TopBar
import com.fungiggle.lexilink.ui.theme.LexiLink_WordPreview
import com.fungiggle.lexilink.utils.LEVEL_CLEAR_DELAY
import com.fungiggle.lexilink.utils.SoundPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val viewModel: GameScreenViewModel = hiltViewModel()
    val mListLetters = viewModel.mListletters
    var shuffle by remember {
        mutableStateOf(false)
    }
    val mLevel by viewModel.level
    val dataprepared by viewModel.dataPrepared

    var levelCompleted by remember {
        mutableStateOf(false)
    }
    var solutionCompleted by remember{
        mutableStateOf(false)
    }
    var mGems by remember{
        mutableIntStateOf(0)
    }
    LaunchedEffect(Unit){
        mGems = GemShopManager.getGemsTotal()
    }
    LaunchedEffect(Unit) {
        viewModel.prepareLevel()
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
                        TopBar(mLevel,mGems)
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
                            contentScale = ContentScale.FillBounds
                        )

                        //Solution pad goes here
                        if (dataprepared) {
                            val solutionsList = viewModel.mListSolutions
                            SolutionPad(solutionsList)
                        }

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

                        if (dataprepared) {
                            KeyPad(
                                shuffle = shuffle,
                                list = mListLetters,
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
                                    val solution = viewModel.isExists(list)
                                    if (solution != null) {
                                        val alllevelscompleted = viewModel.setSolutionComplete(solution)
                                        if(alllevelscompleted){ // level is completed
                                            levelCompleted = true
                                        } else { //only solution is completed
                                            solutionCompleted = true
                                        }
                                    }
                                    viewModel.wordtopreview.value = ""
                                }
                            )
                        }
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
                                scope.launch(Dispatchers.IO) {
                                    val gemsConsumed = viewModel.consumeGems()
                                    if(gemsConsumed){
                                        val solution = viewModel.showLetter()
                                        if(solution != null){
                                            val allLettersShowed = viewModel.isAllLettersShowed(solution)
                                            if(allLettersShowed){
                                                val alllevelscompleted = viewModel.setSolutionComplete(solution)
                                                if(alllevelscompleted){ // level is completed
                                                    levelCompleted = true
                                                } else { //only solution is completed
                                                    solutionCompleted = true
                                                }
                                            }
                                        }
                                        mGems = GemShopManager.getGemsTotal()
                                    }
                                }
                            },
                            onShuffleClicked = {
                                scope.launch{
                                    SoundPlayer.playSound(context,R.raw.shuffle)
                                }
                                shuffle = true
                                mListLetters.shuffle()
                            }
                        )
                    }
                }
            }
        }


        //Screen Overlay
        if(!viewModel.dataPrepared.value){
            LaunchedEffect(Unit){
                scope.launch {
                    SoundPlayer.playSound(context,R.raw.overlay_enter)
                }
            }
        } else {
            LaunchedEffect(Unit){
                scope.launch {
                    SoundPlayer.playSound(context,R.raw.overlay_exit)
                }
            }
        }
        //Preview
        PreviewWord(
            modifier = Modifier
                .align(Alignment.Center)
            ,
            viewmodel = viewModel,
        )
        if(mGems < GEMS_TO_CONSUME){

            //Ad
            val adbuttonmodifier = Modifier
                .padding(top=60.dp)
                .size(80.dp)
                .align(Alignment.CenterEnd)
            ButtonWatchAd(
                modifier = adbuttonmodifier
            ){

                //Show your add here
            }
        }

        AnimatedOverlay(animate = !viewModel.dataPrepared.value)


        //level completed
        if (levelCompleted) {
            LaunchedEffect(Unit){
                scope.launch {
                    SoundPlayer.playSound(context,R.raw.level_completed)
                }
            }
            DialogLevelComplete {
                levelCompleted = false
                viewModel.prepareLevel()
            }
        }

        //Solution completed
        if(solutionCompleted){
            val modifier = Modifier
                .align(Alignment.Center)
            GoodJob(modifier)
            LaunchedEffect(Unit){
                scope.launch(Dispatchers.IO) {
                    SoundPlayer.playSound(context,R.raw.good_job)
                    delay(2000)
                    solutionCompleted = false
                }
            }
        }

    }
}

