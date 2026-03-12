package com.mivanba.catsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mivanba.catsapp.ui.CatDetailScreen
import com.mivanba.catsapp.ui.CatScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.List.route
    ) {
        composable(route = AppScreens.List.route) {
            CatScreen(
                onNavigateToDetail = { catId ->
                    navController.navigate(AppScreens.Detail.createRoute(catId))
                }
            )
        }

        composable(
            route = AppScreens.Detail.route,
            arguments = listOf(navArgument("catId") { type = NavType.StringType })
        ) { // backStackEntry ->
            // val catId = backStackEntry.arguments?.getString("catId") ?: ""

            CatDetailScreen(
                // catId = catId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}