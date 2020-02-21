package com.example.todoqg.tasklist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoqg.R
import com.example.todoqg.network.Api
import com.example.todoqg.task.TaskActivity
import kotlinx.android.synthetic.main.fragment_task_list.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


const val ADD_TASK_REQUEST_CODE = 123
const val EDIT_TASK_REQUEST_CODE = 124

class TaskListFragment : Fragment() {

    private var taskList = mutableListOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3")
    )

    private val coroutineScope = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_task_list, container, false)

//        if (savedInstanceState != null) {
//            with(savedInstanceState) {
//                var res: ArrayList<Task>? = getParcelableArrayList("list")
//            }
//        }

        val intent = activity!!.intent

        when {
            intent.action == Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    val description = intent.getStringExtra(Intent.EXTRA_TEXT)

                    if (!description.isNullOrBlank()) {
                        val intentTaskActivity = Intent(this.context, TaskActivity::class.java)
                        val task = Task(id = UUID.randomUUID().toString(), title = "", description = description)
                        intentTaskActivity.putExtra("currentTask", task)
                        startActivityForResult(intentTaskActivity, ADD_TASK_REQUEST_CODE)
                    }
                }
            }
        }

        savedInstanceState?.let {
            val taskParcelable: ArrayList<Task>? = it.getParcelableArrayList("list")

            if (taskParcelable != null) {
                taskList = taskParcelable.toMutableList()
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        coroutineScope.launch {
            val userInfo = Api.userService.getInfo().body()!!
            textView_profil.setText("${userInfo.firstName} ${userInfo.lastName}")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val taskListAdapter = TaskListAdapter(taskList)
        recyclerView.adapter = taskListAdapter

        taskListAdapter.onDeleteClickListener = { task ->
            taskList.remove(task)
            recycler.adapter?.notifyDataSetChanged()
        }

        FABAdd.setOnClickListener {
            val intent = Intent(this.context, TaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
        }

        taskListAdapter.onEditClickListener = { task ->
            val intent = Intent(this.context, TaskActivity::class.java)
            intent.putExtra("currentTask", task)
            startActivityForResult(intent, EDIT_TASK_REQUEST_CODE)
        }

        taskListAdapter.onLayoutLongClickListener = {task ->
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, task.description)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelableArrayList("list", ArrayList<Parcelable>(taskList))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ADD_TASK_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            val task = data!!.getSerializableExtra(TaskActivity.TASK_KEY) as Task
            val task = data!!.getParcelableExtra(TaskActivity.TASK_KEY) as Task
            taskList.add(task)
            recycler.adapter?.notifyDataSetChanged()
        } else if (requestCode == EDIT_TASK_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val updatedTask = data!!.getParcelableExtra(TaskActivity.TASK_KEY) as Task
//            taskList.replaceAll { t: Task -> if (t.id == updatedTask.id) updatedTask else t}
            val newTask = taskList.find { task -> task.id == updatedTask.id }
            newTask?.title = updatedTask.title
            newTask?.description = updatedTask.description
            recycler.adapter?.notifyDataSetChanged()
        }
    }
}