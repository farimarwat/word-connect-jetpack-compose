package com.fungiggle.lexilink.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fungiggle.lexilink.R

@Composable
fun TopBar(){
    Row(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        //left
        ButtonLevel()

        //Right
        ButtonGem()
    }
}
@Composable
@Preview
fun ButtonGem(){
    Box(
        modifier = Modifier
            .padding(8.dp)
            .width(100.dp)
            .height(40.dp)
    ){
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.gameview_topmenu_button),
            contentDescription = "level",
            contentScale = ContentScale.FillBounds
        )
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp,end=16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = R.drawable.gem),
                contentDescription = "Gem")
            Text(
                text = "20",

                )
        }
    }
}
@Composable
@Preview
fun ButtonLevel(){
    Box(
        modifier = Modifier
            .padding(8.dp)
            .width(100.dp)
            .height(40.dp),
        contentAlignment = Alignment.Center
    ){
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.gameview_topmenu_button),
            contentDescription = "level",
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = stringResource(id = R.string.level,101),
            style = MaterialTheme.typography.labelMedium
        )
    }
}