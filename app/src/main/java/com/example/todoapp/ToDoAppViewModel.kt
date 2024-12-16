package com.example.todoapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class ToDoAppViewModel(
    private val taskRepository: TaskRepository = Graph.taskRepository
): ViewModel() {
    var toDoAppDataTaskState by mutableStateOf("")
    var toDoAppDataCategoryState by mutableStateOf("")
    var toDoAppDateState by mutableStateOf(Date())



    fun ontoDoAppDataTaskChanged(newString: String) {
        toDoAppDataTaskState = newString
    }

    fun ontoDoAppDataCategoryChanged(newString: String) {
        toDoAppDataCategoryState = newString
    }

    fun ontoDoAppDataDateChange(newString: Date) {
        toDoAppDateState = newString
    }

    lateinit var getAllTask: Flow<List<ToDoAppData>>

    init {
        viewModelScope.launch {
            getAllTask = taskRepository.getTasks()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addTask(toDoAppData: ToDoAppData) {

        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.addTask(toDoAppData = toDoAppData)
        }

    }

    fun getTaskById(id: Long): Flow<ToDoAppData> {
        return taskRepository.getTaskById(id)
    }

    fun updateTask(toDoAppData: ToDoAppData) {
        viewModelScope.launch {
            taskRepository.updateTask(toDoAppData = toDoAppData)
        }
    }

    fun updateTaskStatus(task: ToDoAppData) {
        viewModelScope.launch {
            taskRepository.updateStatus(task) // Call the repository method to update the task
        }
    }
    fun deleteTask(toDoAppData: ToDoAppData) {
        viewModelScope.launch {
            taskRepository.deleteTask(toDoAppData = toDoAppData)
        }

    }
}



