package com.fungiggle.lexilink.data.word

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word(
    @PrimaryKey
    @ColumnInfo(name = "serial")
    val serial:Int,
    @ColumnInfo(name="_id")
    val id:String,
    @ColumnInfo(name = "word")
    val word:String,
    @ColumnInfo(name="completed")
    val completed:Boolean
)
