package com.example.todoapp

sealed class Screen(val route:String) {
    object addscreen: Screen("add-screen")
    object taskScreen: Screen("task-screen")
    object pendingScreen : Screen("pending-screen")
    object completedScreen : Screen("completed-screen")


}