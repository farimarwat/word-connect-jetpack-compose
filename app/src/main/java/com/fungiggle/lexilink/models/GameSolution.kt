package com.fungiggle.lexilink.models

data class GameSolution(
    val letters:MutableList<GameLetter>,
    val iscompleted:Boolean = false
)
