package com.fungiggle.lexilink.ui.screens.gameview

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fungiggle.lexilink.data.chapter.Chapter
import com.fungiggle.lexilink.data.chapter.ChapterRepo
import com.fungiggle.lexilink.utils.GEMS_TO_CONSUME
import com.fungiggle.lexilink.utils.GemShopManager
import com.fungiggle.lexilink.data.level.LevelRepo
import com.fungiggle.lexilink.data.level.LevelWithSolutions
import com.fungiggle.lexilink.models.GameLetter
import com.fungiggle.lexilink.models.GameSolution
import com.fungiggle.lexilink.models.GameSolutionWithLetter
import com.fungiggle.lexilink.models.KeyPadButton
import com.fungiggle.lexilink.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(
    val levelRepo: LevelRepo,
    val chapterRepo: ChapterRepo
) : ViewModel() {
    var mChapter:Chapter? = null
    var mListletters = mutableStateListOf<KeyPadButton>()
    var mListSolutions = mutableStateListOf<GameSolution>()
    val mListSolutionsSelected = mutableStateListOf<GameSolution>()
    var mLevelWithSolutions:LevelWithSolutions? = null
    var mLevel = mutableStateOf("")
    var mCompletedLevels = mutableIntStateOf(0)
    var dataPrepared = mutableStateOf(false)
    var mGameCompleted = mutableStateOf(false)

    //wordtopreview
    val wordtopreview = mutableStateOf("")


    fun updateWordToPreview(word: String) {
        wordtopreview.value = word
    }
    fun prepareLevel() =
        viewModelScope.launch(Dispatchers.IO) {
            dataPrepared.value = false
            mChapter = null
            mListletters.clear()
            mListSolutions.clear()
            mListSolutionsSelected.clear()
            //Get Completed levels
            mCompletedLevels.value = levelRepo.getCompletedLevelCount()+1
            //getting data from database
            val job = viewModelScope.launch{
                //startagain
                mChapter = chapterRepo.getChapter(false)
                if(mChapter == null){
                    mGameCompleted.value = true
                    return@launch
                }
                mChapter?.let { chapter ->
                    val levelWithSolutions = levelRepo.getLevelWithSolutions(chapter.id,false)
                    Log.e(TAG,"Level With Solutions: ${levelWithSolutions}")
                    if(levelWithSolutions == null){
                        Log.e(TAG,"Level Empty")
                        chapterRepo.update(mChapter!!.copy(chapterCompleted = true))
                        return@launch
                    } else {
                        mLevelWithSolutions = levelWithSolutions
                    }
                }

            }
            job.join()
            mLevelWithSolutions?.let { levelWithSolutions ->
                val dblevel = levelWithSolutions.level
                mLevel.value = mCompletedLevels.value.toString()
                val answers = levelWithSolutions.solutions
                val solutions = mutableListOf<String>()
                answers.forEach{item ->
                    solutions.add(item.solutionWord)
                }
                val letters = dblevel.levelLetters

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
                        GameSolution(letters=gameletters.toMutableStateList())
                    )
                }

                val sorted = list.sortedBy { it.letters.size }
                mListSolutions = sorted.toMutableStateList()
                delay(1000)
                dataPrepared.value = true
            }
        }


    //This will set the letter to visible
    //And return the letter's solution
     fun showLetter():GameSolutionWithLetter?{
        Log.e(TAG,"Start Show Letter")
        mListSolutions.forEachIndexed{ sindex, sitem ->
            sitem.letters.forEachIndexed{lindex,litem ->
                if(!litem.isvisible){
                    mListSolutions[sindex].letters[lindex] = litem.copy(isvisible = true)
                    return GameSolutionWithLetter(sitem,litem)
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
        mListSolutionsSelected.add(mListSolutions[index])
        return isLevelCompleted()
    }

    fun isSolutionExists(list:List<KeyPadButton>):GameSolution?{
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
    fun isSolved(list:List<KeyPadButton>):GameSolution?{
        var word = ""
        list.forEach {kpb ->
            word += kpb.label.uppercase()
        }
        mListSolutionsSelected.forEach{solution ->
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
        mLevelWithSolutions?.let { level ->
            viewModelScope.launch(Dispatchers.IO) {
                levelRepo.update(level.level.copy(levelCompleted = true))
                //checking if chapter is completed
                mChapter?.let { chapter ->
                    val levelWithSolutions = levelRepo.getLevelWithSolutions(chapter.id,false)
                    if(levelWithSolutions == null){
                        chapterRepo.update(chapter.copy(chapterCompleted = true))
                    }
                }
            }
        }
        return true
    }
    //Gem
    fun consumeGems():Boolean {
        val gemsTotal = GemShopManager.getGemsTotal()
        if(gemsTotal >= GEMS_TO_CONSUME){
            GemShopManager.consumeGems(GEMS_TO_CONSUME)
            return true
        }
        return false
    }
}