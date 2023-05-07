package com.example.spash_apargel.navigation

sealed class AppScreens(
    val route: String
    ){
    object MainScreen : AppScreens(route = "main_screen")
    object AdminScreen : AppScreens(route = "admin_screen")
    object NewPlace : AppScreens(route = "new_place")
}
