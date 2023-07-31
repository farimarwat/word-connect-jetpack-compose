package com.fungiggle.lexilink.data.level

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wordapp_level")
data class Level(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id:Int,

    @ColumnInfo(name = "level_serial")
    val levelSerial:Int,

    @ColumnInfo(name="level_letters")
    val levelLetters:String,

    @ColumnInfo(name = "level_completed")
    val levelCompleted:Boolean,

    @ColumnInfo(name="chapter_id")
    val chapterId:Int

)
