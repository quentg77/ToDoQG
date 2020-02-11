package com.example.todoqg.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todoqg.R
import com.example.todoqg.tasklist.Task
import kotlinx.android.synthetic.main.activity_task.*
import java.util.*

class TaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        btnValidate.setOnClickListener {
            Task(id = UUID.randomUUID().toString(), title = "New Task !")// TODO : "finir Ajout de tâche complet"
        }
    }
}
