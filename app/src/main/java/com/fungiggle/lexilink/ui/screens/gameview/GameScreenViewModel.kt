package com.fungiggle.lexilink.ui.screens.gameview

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fungiggle.lexilink.models.GameLetter
import com.fungiggle.lexilink.models.GameLevel
import com.fungiggle.lexilink.models.GameSolution
import com.fungiggle.lexilink.models.KeyPadButton
import com.fungiggle.lexilink.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor() : ViewModel() {
    val listletters:MutableStateFlow<MutableList<KeyPadButton>> = MutableStateFlow(mutableListOf())
    var listSolutions = mutableStateListOf<GameSolution>()

    //wordtopreview
    val wordtopreview = mutableStateOf("")


    fun updateWordToPreview(word: String) {
        wordtopreview.value = word
    }

    fun prepareLevel(letters: String, solutions: List<String>) =
        viewModelScope.launch(Dispatchers.IO) {

            //Preparing Keypad Selection
            for (l in letters) {
                listletters.value.add(
                    KeyPadButton(label = l.toString())
                )
            }
            //Preparing Solution View
            val list = mutableListOf<GameSolution>()
            for (word in solutions) {
                val gameletters = mutableListOf<GameLetter>()
                for (letter in word) {
                    gameletters.add(
                        GameLetter(label = letter.toString())
                    )
                }
                list.add(
                    GameSolution(gameletters)
                )
            }

            val sorted = list.sortedBy { it.letters.size }
            listSolutions.addAll(sorted)

        }

}