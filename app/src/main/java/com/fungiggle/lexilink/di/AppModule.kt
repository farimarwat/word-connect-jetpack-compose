package com.fungiggle.lexilink.di

import android.app.Application
import androidx.room.Room
import com.fungiggle.lexilink.data.GameDatabase
import com.fungiggle.lexilink.data.answer.AnswerDao
import com.fungiggle.lexilink.data.word.WordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val DATABASE_NAME = "wordconnect.db"
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesDatabase(context:Application):GameDatabase{
        val instance = Room.databaseBuilder(
            context,
            GameDatabase::class.java,
            DATABASE_NAME
        )
            .createFromAsset(DATABASE_NAME)
        return instance.build()
    }

    @Singleton
    @Provides
    fun providesWordDao(database: GameDatabase):WordDao{
        return database.getWordDao()
    }

    @Singleton
    @Provides
    fun providesAnswerDao(database: GameDatabase):AnswerDao{
        return database.getAnswerDao()
    }
}