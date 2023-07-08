package com.fungiggle.lexilink.ui.screens.gameview

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fungiggle.lexilink.R
import com.fungiggle.lexilink.ui.components.BottomBar
import com.fungiggle.lexilink.ui.components.ButtonRound
import com.fungiggle.lexilink.ui.components.KeyPad
import com.fungiggle.lexilink.ui.components.TopBar
import com.fungiggle.lexilink.utils.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
@Preview(
    name = "Large", widthDp = 360, heightDp = 740
)
fun MainViewPreviewLarge(){
    GameScreen()
}
@Composable
@Preview(
    name = "small", widthDp = 360, heightDp = 580
)
fun MainViewPreviewSmall(){
    GameScreen()
}
@Composable
fun GameScreen(){
    val context = LocalContext.current
    val viewModel:GameScreenViewModel  =  hiltViewModel()
    val list = viewModel.lettersList
    var shuffle by remember{
        mutableStateOf(false)
    }

    var centerMain by remember{
        mutableStateOf(Offset(0f,0f))
    }
    var sizeMain by remember {
        mutableStateOf(Size(0f,0f))
    }
    val radiusMain by animateFloatAsState(
        targetValue = if(shuffle) 50f else sizeMain.width/3f,
        animationSpec = tween(200)
    ){
        CoroutineScope(Dispatchers.IO).launch{
            list.shuffle()
            delay(100)
            shuffle = false
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
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
                Column(modifier = Modifier
                    .fillMaxSize()
                ) {
                    //Top Menu
                    Box(modifier = Modifier
                        .weight(0.2f)){
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
                    Box(modifier = Modifier
                        .weight(1f)){
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
                    ){
                        KeyPad(
                            radiusMain,
                            list = list
                        ) {center ,size ->
                          centerMain = center
                          sizeMain = size
                        }
                    }


                    //Bottom
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .weight(0.2f)
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                            ,
                            painter = painterResource(id = R.drawable.gameview_bottombar),
                            contentDescription = "Bottom Bar",
                            contentScale = ContentScale.FillBounds
                        )
                        BottomBar(
                            onHintClicked = {

                            },
                            onShuffleClicked = {
                                shuffle = true
                                Log.e(TAG,"Shuffle Clicked: $shuffle")
                            }
                        )
                    }
                }
            }
        }
    }
}