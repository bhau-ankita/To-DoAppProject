package com.example.todoapp

import android.content.Context
import androidx.room.Room

object Graph {
    lateinit var dataBase: ToDoDataBase

    val taskRepository by lazy {
        TaskRepository(toDoAppDao = dataBase.toDoAppDao())
    }

    fun provide(context : Context){
        dataBase = Room.databaseBuilder(context, ToDoDataBase::class.java, "todoapp.db").build()  // yeh database ka instance hai

    }
}