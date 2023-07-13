package com.fungiggle.lexilink.data.answer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "answers")
data class Answer(
    @PrimaryKey
    @ColumnInfo(name="_id")
    val id:Int,
    @ColumnInfo(name="answer")
    val answer:String,
    @ColumnInfo(name="word_id")
    val wordId:String
)
