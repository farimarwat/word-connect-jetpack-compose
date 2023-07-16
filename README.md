# Word Puzzle/Connect Game
A word puzzle game based on android jetpack compose.

## Note:
This project is open-source and any one is most welcome to contribute but remember that
before uploading to play store, modify all resources e.g., images, audios etc. All the resources
that below the project/author are strictly prohibited to use in your commercial release. In case
of violation, your app will be reported and suspended from play store/app store.

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

## How To Use
1. Just Clone the repo and done!.

## Game Screen
There are two screen composables (screens) Main Screen which have play/settings buttons and gameview which have the
actual game. It is quite simple and easy to understand
Every thing resides in ui>screens>gameview package. This package containes two files, one is viewmodel and second is gamveview composable.
GameView contains is divided into two parts, one is top and second is bottom. The top section contains the solution pad which letters will be showed invisible/visible. Second and the bottom part contains keypad which can be used to select letters.

## GemShopManager
This singleton class contains every thing to manage gems.


