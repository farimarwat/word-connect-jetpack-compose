package com.fungiggle.lexilink.data.level

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface LevelDao {
    @Query("SELECT * FROM wordapp_level where level_completed = :completed ORDER BY level_serial ASC")
    suspend fun get(completed:Boolean):Level?

    @Transaction
    @Query("SELECT * FROM wordapp_level where chapter_id = :chapterid AND level_completed = :levelcompleted ORDER BY RANDOM() LIMIT 1")
    suspend fun getLevelWithSolutions(chapterid: Int, levelcompleted:Boolean):LevelWithSolutions?

    @Update
    suspend fun update(word:Level)

    @Query("SELECT COUNT(*) FROM wordapp_level WHERE level_completed = 1")
    suspend fun getCompletedLevelCount(): Int
}