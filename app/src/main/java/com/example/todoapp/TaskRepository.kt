package com.example.todoapp

import kotlinx.coroutines.flow.Flow
import java.util.Date

class TaskRepository(private val toDoAppDao: ToDoAppDao) {

    suspend fun addTask(toDoAppData: ToDoAppData){
        toDoAppDao.addTask(toDoAppData)
    }

    fun getTasks(): Flow<List<ToDoAppData>> = toDoAppDao.getAllTask()

    fun getTaskById(id:Long) :Flow<ToDoAppData>{
        return toDoAppDao.getATaskById(id)
    }

    suspend fun updateTask(toDoAppData: ToDoAppData){
        toDoAppDao.updateTask(toDoAppData)
    }
    suspend fun updateStatus(toDoAppData: ToDoAppData){
        toDoAppDao.updateStatus(toDoAppData)
    }
    suspend fun deleteTask(toDoAppData: ToDoAppData){
        toDoAppDao.deleteTask(toDoAppData)
    }


}