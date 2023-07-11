package com.fungiggle.lexilink.models

data class GameSolution(
    val letters:List<GameLetter>,
    val iscompleted:Boolean = false
)
