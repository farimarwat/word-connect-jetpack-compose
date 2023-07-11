package com.fungiggle.lexilink.models

import java.util.UUID

data class GameLetterOffset(
   val solutionid:String = UUID.randomUUID().toString()
)
