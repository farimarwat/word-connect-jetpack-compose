package com.fungiggle.lexilink.data.chapter

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wordapp_chapter")
data class Chapter(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id:Int,
    @ColumnInfo(name="chapter_name")
    val chapterName:String,
    @ColumnInfo(name="chapter_completed")
    val chapterCompleted:Boolean
)
