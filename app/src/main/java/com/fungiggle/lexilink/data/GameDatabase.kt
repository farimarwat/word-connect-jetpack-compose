package com.fungiggle.lexilink.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fungiggle.lexilink.data.chapter.Chapter
import com.fungiggle.lexilink.data.chapter.ChapterDao
import com.fungiggle.lexilink.data.solution.Solution
import com.fungiggle.lexilink.data.solution.SolutionDao
import com.fungiggle.lexilink.data.level.Level
import com.fungiggle.lexilink.data.level.LevelDao

@Database(
    entities = [
        Level::class,
        Solution::class,
        Chapter::class
    ], version = 1, exportSchema = false
)
abstract class GameDatabase:RoomDatabase() {
    abstract fun getLevelDao():LevelDao
    abstract fun getSolutionDao():SolutionDao

    abstract fun getChapterDao():ChapterDao
}