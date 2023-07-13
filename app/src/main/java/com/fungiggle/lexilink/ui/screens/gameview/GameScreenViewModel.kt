package com.fungiggle.lexilink.ui.screens.gameview

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fungiggle.lexilink.data.answer.AnswerRepo
import com.fungiggle.lexilink.data.word.Word
import com.fungiggle.lexilink.data.word.WordRepo
import com.fungiggle.lexilink.data.word.WordWithAnswers
import com.fungiggle.lexilink.models.GameLetter
import com.fungiggle.lexilink.models.GameSolution
import com.fungiggle.lexilink.models.KeyPadButton
import com.fungiggle.lexilink.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(
    val wordRepo: WordRepo,
    val answerRepo: AnswerRepo
) : ViewModel() {
    val listletters:MutableStateFlow<MutableList<KeyPadButton>> = MutableStateFlow(mutableListOf())
    var listSolutions = mutableStateListOf<GameSolution>()
    var mWordWithAnswers:WordWithAnswers? = null
    var level = mutableStateOf("")

    //wordtopreview
    val wordtopreview = mutableStateOf("")


    fun updateWordToPreview(word: String) {
        wordtopreview.value = word
    }
    fun prepareLevel() =
        viewModelScope.launch(Dispatchers.IO) {
            mWordWithAnswers = wordRepo.getWordWithAnswers(false)
            if(mWordWithAnswers != null){
                val dbword = mWordWithAnswers!!.word
                level.value = dbword.serial.toString()
                val answers = answerRepo.list(dbword.id)
                val solutions = mutableListOf<String>()
                answers.forEach{item ->
                    solutions.add(item.answer)
                }
                val letters = dbword.word
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


    fun setSolutionComplete(solution: GameSolution): Boolean {
        val index = listSolutions.indexOf(solution)
        val letters = solution.letters.toMutableList() // Create a copy of the letters list
        letters.forEachIndexed { i, l ->
            letters[i] = l.copy(isvisible = true)
        }
        listSolutions[index] = solution.copy(letters = letters.toMutableStateList(), iscompleted = true)
        return isLevelCompleted()
    }
    fun setCompleteAll():Boolean{
        val solutions = listSolutions.toMutableList()
        solutions.forEachIndexed{ index, item ->
            solutions[index] = item.copy(iscompleted = true)
        }
        listSolutions = solutions.toMutableStateList()
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
        mWordWithAnswers?.let { word ->
            viewModelScope.launch(Dispatchers.IO) {
                wordRepo.update(word.word.copy(completed = true))
            }
        }
        return true
    }
    private fun extractLevel(level:String):String{
        var l = level
        if(level.contains("_")){
            val parts = level.split("_")
            l = parts[1]
        }
        return l
    }
}