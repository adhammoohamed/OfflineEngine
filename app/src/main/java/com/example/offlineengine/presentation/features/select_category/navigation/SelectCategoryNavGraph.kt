package com.example.offlineengine.presentation.features.select_category.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.offlineengine.presentation.features.select_category.screen.SelectCategory
import com.example.offlineengine.presentation.features.select_category.viewmodel.SelectCategoryViewModel
import com.example.offlineengine.presentation.navigation.Screen

fun NavGraphBuilder.selectCategory(navController: NavHostController) {
    composable(route = Screen.SelectCategory.route) {
        SelectCategory(
            viewModel = hiltViewModel<SelectCategoryViewModel>(),
            onBack = {
            navController.popBackStack()
        }, onNext = {
            navController.navigate(Screen.Home.route)
        })
    }
}