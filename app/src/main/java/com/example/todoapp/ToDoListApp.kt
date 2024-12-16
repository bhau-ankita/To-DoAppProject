package com.example.todoapp

import android.app.Application

class ToDoListApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}