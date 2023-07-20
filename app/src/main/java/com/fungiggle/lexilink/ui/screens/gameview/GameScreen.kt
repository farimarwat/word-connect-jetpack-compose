package com.fungiggle.lexilink.ui.screens.gameview

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fungiggle.lexilink.utils.GEMS_TO_CONSUME
import com.fungiggle.lexilink.utils.GemShopManager
import com.fungiggle.lexilink.R
import com.fungiggle.lexilink.components.AnimatedOverlay
import com.fungiggle.lexilink.components.BottomBar
import com.fungiggle.lexilink.components.ButtonWatchAd
import com.fungiggle.lexilink.components.DialogLevelComplete
import com.fungiggle.lexilink.components.GoodJob
import com.fungiggle.lexilink.components.KeyPad
import com.fungiggle.lexilink.components.PreviewWord
import com.fungiggle.lexilink.components.ShowLetterPopup
import com.fungiggle.lexilink.components.SolutionPad
import com.fungiggle.lexilink.components.TopBar
import com.fungiggle.lexilink.utils.SoundPlayer
import com.fungiggle.lexilink.utils.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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
    var offsetShowLetterTarget by remember {
        mutableStateOf(Offset.Zero)
    }
    var offsetHint by remember{
        mutableStateOf(Offset.Zero)
    }
    var showPopupLetter by remember {
        mutableStateOf(false)
    }
    var showHand by remember {
        mutableStateOf(false)
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
                                    val listLocal = list.toList()
                                    var word = ""
                                    listLocal.forEach { btn ->
                                        word += btn.label
                                    }
                                    viewModel.updateWordToPreview(word)
                                },
                                onCompleted = { list ->
                                    val listLocal = list.toList()
                                   scope.launch {

                                       //check if already solved
                                       val differedIsSolved = scope.async {
                                           val solution = viewModel.isSolved(listLocal)
                                           solution
                                       }
                                       val solved = differedIsSolved.await()
                                       if(solved != null){
                                           val index = viewModel.mListSolutions.indexOfFirst {
                                               it.id == solved.id
                                           }
                                           if(index != -1){
                                               val animate = viewModel.mListSolutions[index].animate
                                               viewModel.mListSolutions[index] = viewModel.mListSolutions[index].copy(animate = !animate)
                                               SoundPlayer.playSound(context,R.raw.not_allowed)
                                               viewModel.wordtopreview.value = ""
                                           }
                                           return@launch
                                       }//

                                       //if not already solved then check if solution exists for the
                                       //selected word
                                       val differedIsExists = scope.async {
                                           val solution = viewModel.isExists(listLocal)
                                           solution
                                       }
                                       val solution = differedIsExists.await()
                                       if(solution == null){
                                           SoundPlayer.playSound(context,R.raw.not_allowed)
                                           viewModel.wordtopreview.value = ""
                                           return@launch
                                       }

                                       //If solution exists then set it as complete
                                       val alllevelscompleted = viewModel.setSolutionComplete(solution)
                                       if(alllevelscompleted){ // level is completed
                                           levelCompleted = true
                                       } else { //only solution is completed
                                           solutionCompleted = true
                                       }
                                       viewModel.wordtopreview.value = ""
                                   }
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
                            onHintClicked = { offset ->
                                offsetHint = offset
                                scope.launch(Dispatchers.IO) {
                                    val gemsConsumed = viewModel.consumeGems()
                                    if(!gemsConsumed){
                                        showHand = true
                                        SoundPlayer.playSound(context,R.raw.not_allowed)
                                        return@launch;
                                    }
                                    SoundPlayer.playSound(context,R.raw.show_letter)
                                    val deffered = scope.async {
                                        val solution = viewModel.showLetter()
                                        solution
                                    }
                                    val dsolution = deffered.await()
                                    if(dsolution != null){
                                        val solution = dsolution.solution
                                        val letter = dsolution.letter
                                        //showing start popup over the letter
                                        showPopupLetter = true
                                        offsetShowLetterTarget = letter.offset

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
                .align(Alignment.CenterEnd)
            ButtonWatchAd(
                modifier = adbuttonmodifier,
                showHand = showHand
            ){

                //Show your add here
            }
        }
        LaunchedEffect(showHand){
            if(showHand){
                delay(3000)
                showHand = false
            }
        }

        //letter start
        if(showPopupLetter){
            LaunchedEffect(Unit){
                scope.launch {
                    delay(1000)
                    showPopupLetter = false
                }
            }

            ShowLetterPopup(
                hintOffset = offsetHint,
                letterOffset = offsetShowLetterTarget
            )
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

