package com.mivanba.catsapp.navigation

sealed class AppScreens(val route: String) {
    object List : AppScreens("cat_list")

    object Detail : AppScreens("cat_detail/{catId}") {
        fun createRoute(catId: String) = "cat_detail/$catId"
    }
}