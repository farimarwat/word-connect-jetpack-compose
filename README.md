# Word Puzzle/Connect Game
A word puzzle game based on android jetpack compose.

## Video Demo (Click on image):

<a href="https://youtube.com/shorts/4mfJrG7XfLs?feature=share">
            <img src="https://github.com/farimarwat/word-connect-jetpack-compose/blob/master/preview.png" width="30%" height="30%"/>
</a>

## Important:
This project is open-source and every one is most welcome to contribute but remember that
before uploading to play store, modify all resources e.g., images, audios etc. All the resources
that belongs to the project/author including brand "LexiLink" are strictly prohibited to use in your commercial release due to copy right act. In case
of violation, your app will be reported and suspended from play store/app store when your app/game will be on peak.

## Note:
Some major changes made. Data classes are seperated to increase readability. So completely update the project
## Documentation
Used Technologies:
1. Jetpack Compose
2. Canvas
3. Animation
4. Dagger-Hilt
5. Room Database
6. MVVM
7. Navigation Component
8. Single Activity and Multiple Screens
9. Only 200 levels available

## How To Use
1. Just Clone the repo and done!.

## Data Structure <a href="https://github.com/farimarwat/word-connect-jetpack-compose/tree/master/app/src/main/java/com/fungiggle/lexilink/data">(Models)</a>
1. Chapter: is a kind of group
2. Level: Chapter has many levels
3. Solution: Levels has many solutions


## Screens
There are two screen composables (screens) <a href="https://github.com/farimarwat/word-connect-jetpack-compose/blob/master/app/src/main/java/com/fungiggle/lexilink/ui/screens/main/MainScreen.kt">Main Screen</a> which have play/settings buttons and gameview which have the
actual game. It is quite simple and easy to understand.

Every thing resides in ui>screens>gameview package. This package containes two files, one is viewmodel and second is gamveview composable.

<a href="https://github.com/farimarwat/word-connect-jetpack-compose/blob/master/app/src/main/java/com/fungiggle/lexilink/ui/screens/gameview/GameScreen.kt">GameView</a> is divided into two parts, one is top and second is bottom. The top section contains the solution pad where letters will be showed invisible/visible. Second and the bottom part contains keypad which can be used to select letters.

## Getting Started

## Splash Screen
Gems are initialized on this screen: 
<a href="https://github.com/farimarwat/word-connect-jetpack-compose/blob/master/app/src/main/java/com/fungiggle/lexilink/ui/screens/SplashActivity.kt">SplashActivity.kt</a>
```
CoroutineScope(Dispatchers.IO).launch{

            //Check if app is installed first time. if
            //First time then initial free gems are provided
            GemShopManager.initializeSharedPrefs(mContext)
            if(GemShopManager.isFirstInstallation()){
                GemShopManager.initializeGemsTotal()
                GemShopManager.setFirstInstallationStatus(false)
            }
        }
```
## Prepare Level
Code below in **<a href="https://github.com/farimarwat/word-connect-jetpack-compose/blob/master/app/src/main/java/com/fungiggle/lexilink/ui/screens/gameview/GameScreen.kt">GameScreen.kt</a>** is responsible to initialy prepare the level/

<a href="https://github.com/farimarwat/word-connect-jetpack-compose/blob/master/app/src/main/java/com/fungiggle/lexilink/ui/screens/gameview/GameScreenViewModel.kt">GameScreenViewModel.kt</a> has all related methods
```
LaunchedEffect(Unit) {
        viewModel.prepareLevel()
    }
```
## Top Bar
Top bar contains two sections. One to display level and second is to show available gems.
It takes two params, level and gems which is already provided by viewmodel.
```
fun TopBar(level:String, gems:Int)
```
## Bottom Bar
It has two callbacks, one to trigger hint and second to shuffle the letters.

On hint, gems are first checked, if it has enough gems then gems are consumed and hint is provided.

**Note:** GemShopManager.kt which is responsible to manage gems, resides in utils package
```
BottomBar(
  onHintClicked = {},
  onShuffleClicked = {}
)
```
## Ad button
Add button is only visible if gems are not enough to consume. The consume amount can be changed in 
<a href="https://github.com/farimarwat/word-connect-jetpack-compose/blob/master/app/src/main/java/com/fungiggle/lexilink/utils/GemShopManager.kt">GemShopManager.kt</a>
```
val adbuttonmodifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterEnd)
            ButtonWatchAd(
                modifier = adbuttonmodifier
            ){

                //Show your add here
            }
```
## To add more levels
To add more levels, go to the dJango python project. Download the repo, run server and add more levels.
Next generate the database which is compatable with kotlin room database on Chapters page. Next copy that database (wordgame.db) to asset folder.
Python project: https://github.com/farimarwat/word-connect-database-populator/

**Note:** I will gradually update the documentation.
## Versio Log
**31/07/2023**

Major changes of seperating data models in Chapter. Chapter has levels. Level has solutions.

**18/07/2023**

1. ad indicator added
2. hint indicator added
3. error sound added

Initial release
## Support Me
If you want to donate then you are welcome to buy me a cup of tea via **PATREON** because this encourages me to give you more free stuff
and continue to  maintain this library

<a href="https://patreon.com/farimarwat">Buy Now!</a>
