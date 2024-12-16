package com.example.todoapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "To-Do-Table")
data class ToDoAppData @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "task-title")
    val task: String = "",
    @ColumnInfo(name = "category")
    val category: String = "",
    @ColumnInfo(name = "time")
    val time: Date,
    @ColumnInfo(name = "date")
    val date: Date,
    @ColumnInfo(name = "status")
    val status: String = "Pending",
    @ColumnInfo(name = "isChecked")
    val isChecked: Boolean= false,




    //val date : LocalDate = LocalDate.now()

)

