package com.fungiggle.lexilink.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.fungiggle.lexilink.R
import com.fungiggle.lexilink.ui.theme.LexiLinkTheme
import com.fungiggle.lexilink.ui.theme.LexiLink_Background
import com.fungiggle.lexilink.ui.theme.LexiLink_YellowDark
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        super.onCreate(savedInstanceState)
        setContent {
            LexiLinkTheme {
                // A surface container using the 'background' color from the theme
                Splash()
            }
        }
    }
}

@Composable
@Preview
fun Splash(){
    val context = LocalContext.current
    LaunchedEffect(Unit){
        delay(3000)
        context.startActivity(
            Intent(context, MainActivity::class.java)
        )
        (context as Activity).finish()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LexiLink_Background),
        contentAlignment = Alignment.Center
    ){
        Text(
            text= stringResource(id = R.string.studio_name),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = LexiLink_YellowDark
        )
    }
}