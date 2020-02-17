package com.example.todoqg.task

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.todoqg.R
import com.example.todoqg.tasklist.Task
import kotlinx.android.synthetic.main.activity_task.*
import java.util.*


class TaskActivity : AppCompatActivity() {
    companion object {
        const val TASK_KEY = "newTask"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        btnValidate.setOnClickListener {
            val taskTitle = findViewById<EditText>(R.id.editTextTitle).text.toString()
            val taskDescription = findViewById<EditText>(R.id.editTextDescription).text.toString()

            if (taskTitle.isNotEmpty()) {
                val task = if (taskDescription.isEmpty()) Task(id = UUID.randomUUID().toString(), title = taskTitle) else Task(id = UUID.randomUUID().toString(), title = taskTitle, description = taskDescription)
                intent.putExtra(TASK_KEY, task)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, "Title is needed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
