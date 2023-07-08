package com.fungiggle.lexilink.ui.screens

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fungiggle.lexilink.navigation.DestinationGame
import com.fungiggle.lexilink.navigation.DestinationMain
import com.fungiggle.lexilink.ui.screens.gameview.GameScreen
import com.fungiggle.lexilink.ui.screens.main.MainScreen
import com.fungiggle.lexilink.ui.theme.LexiLinkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        setContent {
            LexiLinkTheme {
                val navController = rememberNavController()
                Box(modifier = Modifier.fillMaxSize()){
                    NavHost(navController = navController, startDestination = DestinationMain.route ){
                        composable(DestinationMain.route){
                            MainScreen(navController)
                        }
                        composable(DestinationGame.route){
                            GameScreen()
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        super.onResume()
    }
}