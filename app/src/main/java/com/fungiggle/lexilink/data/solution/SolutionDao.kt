package com.fungiggle.lexilink.data.solution

import androidx.room.Dao
import androidx.room.Query

@Dao
interface SolutionDao {
    @Query("SELECT * FROM wordapp_solution WHERE level_id = :levelid")
    suspend fun list(levelid:String):List<Solution>
}