package com.example.todoapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ToDoAppDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addTask(toDoAppEntity:  ToDoAppData)

    @Query("Select * from `to-do-table`")
    abstract fun getAllTask(): Flow<List<ToDoAppData>>

    @Query("Select * from `to-do-table` where id = :id")
    abstract fun getATaskById(id:Long) :Flow<ToDoAppData>

    @Delete
    abstract suspend fun deleteTask(toDoAppEntity: ToDoAppData)

    @Update
    abstract suspend fun updateTask(toDoAppEntity: ToDoAppData)

    @Update
    abstract suspend fun updateStatus(toDoAppEntity: ToDoAppData)


}