package com.fungiggle.lexilink.ui.screens

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnApplyWindowInsetsListener
import android.view.View.OnSystemUiVisibilityChangeListener
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fungiggle.lexilink.navigation.DestinationGame
import com.fungiggle.lexilink.navigation.DestinationMain
import com.fungiggle.lexilink.ui.screens.gameview.GameScreen
import com.fungiggle.lexilink.ui.screens.main.MainScreen
import com.fungiggle.lexilink.ui.theme.LexiLinkTheme
import com.fungiggle.lexilink.utils.SoundPlayer
import com.fungiggle.lexilink.utils.TAG

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity(){
    lateinit var mContext:Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        CoroutineScope(Dispatchers.Main).launch{
            SoundPlayer.init(mContext)
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
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            val listener = object:OnApplyWindowInsetsListener{
                override fun onApplyWindowInsets(v: View, insets: WindowInsets): WindowInsets {
                    val isSystemBarsVisible = insets.isVisible(WindowInsets.Type.systemBars())
                    if(isSystemBarsVisible){
                        CoroutineScope(Dispatchers.Main).launch{
                            delay(3000)
                            hideSystemUI()
                        }
                    }
                    return insets
                }

            }
            window.decorView.setOnApplyWindowInsetsListener(listener)
        } else {
            val listener = object :OnSystemUiVisibilityChangeListener{
                @Deprecated("Deprecated in Java")
                override fun onSystemUiVisibilityChange(visibility: Int) {
                    Log.e(TAG,"System Ui Visibility: $visibility")
                    if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                        CoroutineScope(Dispatchers.Main).launch{
                            delay(3000)
                            hideSystemUI()
                        }
                    }
                }

            }
            window.decorView.setOnSystemUiVisibilityChangeListener(listener)
        }

        hideSystemUI()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus) hideSystemUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        SoundPlayer.release()
    }
}
