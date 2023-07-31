package com.fungiggle.lexilink.data.level

import javax.inject.Inject

class LevelRepo @Inject constructor(val dao:LevelDao) {
    suspend fun get(completed:Boolean) = dao.get(completed)
    suspend fun update(word:Level) = dao.update(word)
    suspend fun getLevelWithSolutions(chapterid: Int, levelcompleted:Boolean)
    = dao.getLevelWithSolutions(chapterid,levelcompleted)

    suspend fun getCompletedLevelCount() = dao.getCompletedLevelCount()
}