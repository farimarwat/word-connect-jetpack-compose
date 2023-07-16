package com.fungiggle.lexilink.ui.screens.gameview

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farimarwat.picloo.utils.GEMS_TO_CONSUME
import com.farimarwat.picloo.utils.GemShopManager
import com.fungiggle.lexilink.data.answer.AnswerRepo
import com.fungiggle.lexilink.data.word.WordRepo
import com.fungiggle.lexilink.data.word.WordWithAnswers
import com.fungiggle.lexilink.models.GameLetter
import com.fungiggle.lexilink.models.GameSolution
import com.fungiggle.lexilink.models.KeyPadButton
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(
    val wordRepo: WordRepo,
    val answerRepo: AnswerRepo
) : ViewModel() {
    var mListletters = mutableStateListOf<KeyPadButton>()
    var mListSolutions = mutableStateListOf<GameSolution>()
    var mWordWithAnswers:WordWithAnswers? = null
    var level = mutableStateOf("")
    var dataPrepared = mutableStateOf(false)

    //wordtopreview
    val wordtopreview = mutableStateOf("")


    fun updateWordToPreview(word: String) {
        wordtopreview.value = word
    }
    fun prepareLevel() =
        viewModelScope.launch(Dispatchers.IO) {
            dataPrepared.value = false
            mListletters.clear()
            mListSolutions.clear()
            //getting data from database
            val job = viewModelScope.launch{
                mWordWithAnswers = wordRepo.getWordWithAnswers(false)
            }
            job.join()

            mWordWithAnswers?.let { wordwithanswers ->
                val dbword = wordwithanswers.word
                level.value = dbword.serial.toString()
                val answers = wordwithanswers.answers
                val solutions = mutableListOf<String>()
                answers.forEach{item ->
                    solutions.add(item.answer)
                }
                val letters = dbword.word

                //Preparing Keypad Selection
                val listletters = mutableListOf<KeyPadButton>()
                for (l in letters) {
                    listletters.add(
                        KeyPadButton(label = l.toString())
                    )
                }
                mListletters = listletters.toMutableStateList()

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
                mListSolutions = sorted.toMutableStateList()
            }

            delay(1000)
            dataPrepared.value = true
        }


    //This will set the letter to visible
    //And return the letter's solution
     fun showLetter():GameSolution?{
        mListSolutions.forEachIndexed{ sindex, sitem ->
            sitem.letters.forEachIndexed{lindex,litem ->
                if(!litem.isvisible){
                    mListSolutions[sindex].letters[lindex] = litem.copy(isvisible = true)
                    return sitem
                }
            }
        }
        return null
    }
     fun isAllLettersShowed(solution:GameSolution):Boolean{
        solution.letters.forEach{ item ->
            if(!item.isvisible){
                return false
            }
        }
        return true
    }

    //This will set the solution complete and return if
    //All solutions are completed
    fun setSolutionComplete(solution: GameSolution): Boolean {
        val index = mListSolutions.indexOf(solution)
        val letters = solution.letters.toMutableList() // Create a copy of the letters list
        letters.forEachIndexed { i, l ->
            letters[i] = l.copy(isvisible = true)
        }
        mListSolutions[index] = solution.copy(letters = letters.toMutableStateList(), iscompleted = true)
        return isLevelCompleted()
    }

    fun isExists(list:List<KeyPadButton>):GameSolution?{
        var word = ""
        list.forEach {kpb ->
            word += kpb.label.uppercase()
        }
        mListSolutions.forEach { solution ->
            if(solution.isEqual(word)){
                return solution
            }
        }
        return null
    }

    fun isLevelCompleted():Boolean{
        mListSolutions.forEach { solution ->
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

    //Gem
    suspend fun consumeGems():Boolean = withContext(Dispatchers.IO){
        val gemsTotal = GemShopManager.getGemsTotal()
        if(gemsTotal >= GEMS_TO_CONSUME){
            GemShopManager.consumeGems(GEMS_TO_CONSUME)
            return@withContext true
        }
        return@withContext false
    }
}