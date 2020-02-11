package com.example.todoqg.tasklist

import java.io.Serializable

data class Task(val id: String,val title: String, val description: String = " no description"): Serializable