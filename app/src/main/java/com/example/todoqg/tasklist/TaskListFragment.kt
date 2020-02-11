package com.example.todoqg.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoqg.R
import kotlinx.android.synthetic.main.fragment_task_list.*
import java.util.*

private val taskList = mutableListOf(
    Task(id = "id_1", title = "Task 1", description = "description 1"),
    Task(id = "id_2", title = "Task 2"),
    Task(id = "id_3", title = "Task 3")
)

class TaskListFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_task_list, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val taskListAdapter = TaskListAdapter(taskList)
        recyclerView.adapter = taskListAdapter

        taskListAdapter.onDeleteClickListener = {task ->
            taskList.remove(task)
            recycler.adapter?.notifyDataSetChanged()
        }

        FABAdd.setOnClickListener {
            taskList.add(Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}"))
            recycler.adapter?.notifyDataSetChanged()
        }


    }
}