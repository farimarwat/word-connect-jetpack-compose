package com.fungiggle.lexilink.navigation

interface Destinations {
    val route:String
    val icon:Int
    val label:String
}

object DestinationMain:Destinations{
    override val route: String
        get() = "mainscreen"
    override val icon: Int
        get() = 0
    override val label: String
        get() = "main"
}
object DestinationGame:Destinations{
    override val route: String
        get() = "gamescreen"
    override val icon: Int
        get() = 0
    override val label: String
        get() = "gameview"
}