package com.example.todoapp

import CompletedTask
import PendingTask
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    viewModel: ToDoAppViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screen.taskScreen.route){
        composable(Screen.taskScreen.route){
        TaskScreen(  navController, viewModel)

        }
       composable(Screen.addscreen.route +"/{id}",
           arguments = listOf(
               navArgument("id"){
                   type = NavType.LongType
                   defaultValue = 0L
                  // nullable = false
               }
           )
           ){
           entry -> val id = entry.arguments?.getLong("id") ?: 0L
           AddTask(id = id, viewModel = viewModel, navController = navController )
       }
        composable(Screen.pendingScreen.route){
         PendingTask(navController = navController, viewModel)
        }
        composable(Screen.completedScreen.route){
            CompletedTask(navController = navController, viewModel)
        }
    }
}