package com.fungiggle.lexilink.data.word

import javax.inject.Inject

class WordRepo @Inject constructor(val dao:WordDao) {
    suspend fun get(completed:Boolean) = dao.get(completed)
    suspend fun update(word:Word) = dao.update(word)
    suspend fun getWordWithAnswers(completed: Boolean) = dao.getWordWithAnswers(completed)
}