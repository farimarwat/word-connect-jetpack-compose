package com.fungiggle.lexilink.data.solution

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wordapp_solution")
data class Solution(

    @PrimaryKey
    @ColumnInfo(name="id")
    val id:Int,

    @ColumnInfo(name="solution_word")
    val solutionWord:String,

    @ColumnInfo(name="solution_details")
    val solutionDetails:String?,

    @ColumnInfo(name="level_id")
    val levelId:Int
)
