package com.fungiggle.lexilink.ui.screens.gameview

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fungiggle.lexilink.models.GameLetter
import com.fungiggle.lexilink.models.GameSolution
import com.fungiggle.lexilink.models.KeyPadButton
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
            listletters.value.clear()
            listSolutions.clear()
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
                    GameSolution(gameletters.toMutableStateList())
                )
            }

            val sorted = list.sortedBy { it.letters.size }
            listSolutions.addAll(sorted)

        }

    fun showLetter():GameLetter?{
        val gameLetter:GameLetter? = null
        listSolutions.forEachIndexed{sindex, sitem ->
            sitem.letters.forEachIndexed{lindex,litem ->
                if(!litem.isvisible){
                    listSolutions[sindex].letters[lindex] = litem.copy(isvisible = true)
                    return litem
                }
            }
        }
        return gameLetter
    }

    fun setSolutionComplete(solution:GameSolution):Boolean{
        val index = listSolutions.indexOf(solution)
        listSolutions[index] = solution.copy(iscompleted = true)
        return isLevelCompleted()
    }
    fun isExists(list:List<KeyPadButton>):GameSolution?{
        var word = ""
        list.forEach {kpb ->
            word += kpb.label.uppercase()
        }
        listSolutions.forEach { solution ->
            if(solution.isEqual(word)){
                return solution
            }
        }

        return null
    }

    fun isLevelCompleted():Boolean{
        listSolutions.forEach { solution ->
            if(!solution .iscompleted){
                return false
            }
        }
        return true
    }
}