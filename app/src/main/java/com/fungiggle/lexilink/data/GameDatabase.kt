package com.fungiggle.lexilink.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fungiggle.lexilink.data.answer.Answer
import com.fungiggle.lexilink.data.answer.AnswerDao
import com.fungiggle.lexilink.data.word.Word
import com.fungiggle.lexilink.data.word.WordDao

@Database(
    entities = [
        Word::class,
        Answer::class
    ], version = 1, exportSchema = true
)
abstract class GameDatabase:RoomDatabase() {
    abstract fun getWordDao():WordDao
    abstract fun getAnswerDao():AnswerDao
}