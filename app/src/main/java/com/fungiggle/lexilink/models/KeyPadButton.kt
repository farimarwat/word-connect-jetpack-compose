package com.fungiggle.lexilink.models

import androidx.compose.ui.geometry.Offset

data class KeyPadButton(
    val id:String,
    val label:String,
    var center:Offset,
    val radius:Float,
)
