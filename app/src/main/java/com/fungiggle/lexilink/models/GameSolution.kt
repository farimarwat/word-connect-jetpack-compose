package com.fungiggle.lexilink.models

import androidx.compose.runtime.snapshots.SnapshotStateList
import java.util.UUID

data class GameSolution(
    val id:String = UUID.randomUUID().toString(),
    val letters:SnapshotStateList<GameLetter>,
    val iscompleted:Boolean = false,
    val animate:Boolean = false
){
    fun isEqual(word:String):Boolean{
        var isequal = false
        var w = ""
        this.letters.forEach { letter ->
            w += letter.label.uppercase()
        }
        if(w == word){
            isequal = true
        }
        return isequal
    }
}
