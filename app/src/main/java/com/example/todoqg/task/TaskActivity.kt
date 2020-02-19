package com.example.todoqg.task

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.todoqg.R
import com.example.todoqg.tasklist.Task
import kotlinx.android.synthetic.main.activity_task.*
import kotlinx.android.synthetic.main.item_task.*
import java.util.*


class TaskActivity : AppCompatActivity() {
    companion object {
        const val TASK_KEY = "task_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        var currentId: String? = null

        val extras = intent.extras
        extras?.let { it ->
            val currentTask = it.get("currentTask") as Task?
            currentTask?.let {
                editTextTitle.setText(currentTask.title)
                editTextDescription.setText(currentTask.description)
                currentId = currentTask.id
            }
        }

//        when {
//            intent?.action == Intent.ACTION_SEND -> {
//                if ("text/plain" == intent.type) {
//                    handleSendText(intent)
//                }
//            }
//        }

        btnValidate.setOnClickListener {
            val taskTitle = findViewById<EditText>(R.id.editTextTitle).text.toString()
            val taskDescription = findViewById<EditText>(R.id.editTextDescription).text.toString()

            if (taskTitle.isNotEmpty()) {
//                val id = if (currentId.isNullOrBlank()) UUID.randomUUID().toString() else currentId
                val id = currentId.let { currentId } ?: run { UUID.randomUUID().toString() }
                val task = if (taskDescription.isEmpty()) Task(id = id, title = taskTitle) else Task(id = id, title = taskTitle, description = taskDescription)
                intent.putExtra(TASK_KEY, task)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, "Title is needed", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    private fun handleSendText(intent: Intent) {
//        val test = intent.getStringExtra(Intent.EXTRA_TEXT)
//
//        if (!test.isNullOrBlank()) {
//            editTextTitle.setText(test)
//        }
//    }
}
