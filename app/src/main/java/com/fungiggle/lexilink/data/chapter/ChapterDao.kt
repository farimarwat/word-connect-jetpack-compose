package com.fungiggle.lexilink.data.chapter

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update

@Dao
interface ChapterDao {

    @Query("SELECT * FROM wordapp_chapter WHERE chapter_completed = :completed")
    suspend fun listChapters(completed:Int):List<Chapter>

    @Update
    suspend fun update(chapter:Chapter)

    @Query("SELECT * FROM wordapp_chapter WHERE chapter_completed = :chaptercompleted ORDER BY id ASC")
    suspend fun getChapter(chaptercompleted:Boolean):Chapter?


}