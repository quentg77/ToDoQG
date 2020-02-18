package com.example.todoqg.tasklist

import java.io.Serializable

data class Task(var id: String,var title: String, var description: String = " no description"): Serializable