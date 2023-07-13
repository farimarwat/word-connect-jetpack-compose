package com.fungiggle.lexilink.data.answer

import androidx.room.Dao
import androidx.room.Query

@Dao
interface AnswerDao {
    @Query("SELECT * FROM answers WHERE word_id = :wordid")
    suspend fun list(wordid:String):List<Answer>
}