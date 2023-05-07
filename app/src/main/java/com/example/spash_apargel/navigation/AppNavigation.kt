package com.example.spash_apargel.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.spash_apargel.composables.home.HomeScreen
import com.example.spash_apargel.composables.admin.AdminScreen
import com.example.spash_apargel.composables.place.NewPlace
import com.example.spash_apargel.mvvm.ApargelViewModel

@Composable

fun AppNavigation (apargelViewModel: ApargelViewModel, navController: NavHostController){
    NavHost(navController = navController,
        startDestination = AppScreens.MainScreen.route){
        composable(route = AppScreens.MainScreen.route){ HomeScreen(apargelViewModel, navController) }
        composable(route = AppScreens.AdminScreen.route){ AdminScreen(apargelViewModel, navController) }
        composable(route = AppScreens.NewPlace.route){ NewPlace(apargelViewModel, navController) }
    }
}

