package com.fungiggle.lexilink.data.word

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface WordDao {

    @Query("SELECT * FROM words where completed = :completed ORDER BY serial ASC")
    suspend fun get(completed:Boolean):Word?

    @Transaction
    @Query("SELECT * FROM words where completed = :completed ORDER BY serial ASC")
    suspend fun getWordWithAnswers(completed: Boolean):WordWithAnswers?

    @Update
    suspend fun update(word:Word)
}