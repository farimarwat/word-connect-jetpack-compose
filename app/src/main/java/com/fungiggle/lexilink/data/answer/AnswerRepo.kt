package com.fungiggle.lexilink.data.answer

import javax.inject.Inject

class AnswerRepo @Inject constructor(val dao:AnswerDao) {
    suspend fun list(wordid:String) = dao.list(wordid)
}