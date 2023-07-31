package com.fungiggle.lexilink.data.level

import androidx.room.Embedded
import androidx.room.Relation
import com.fungiggle.lexilink.data.solution.Solution

data class LevelWithSolutions(
    @Embedded
    val level:Level,

    @Relation(
        parentColumn = "id",
        entityColumn = "level_id"
    )
    val solutions:List<Solution>
)
