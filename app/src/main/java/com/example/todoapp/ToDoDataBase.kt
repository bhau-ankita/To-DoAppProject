package com.example.todoapp

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(
    entities = [ToDoAppData::class],
    version = 1,
   // exportSchema = false
)
@TypeConverters(Converter::class)
abstract class ToDoDataBase :RoomDatabase(){
    abstract fun toDoAppDao(): ToDoAppDao
}