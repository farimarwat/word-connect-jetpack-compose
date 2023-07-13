package com.fungiggle.lexilink.data.word

import androidx.room.Embedded
import androidx.room.Relation
import com.fungiggle.lexilink.data.answer.Answer

data class WordWithAnswers(
    @Embedded
    val word:Word,

    @Relation(
        parentColumn = "_id",
        entityColumn = "word_id"
    )
    val answers:List<Answer>
)
