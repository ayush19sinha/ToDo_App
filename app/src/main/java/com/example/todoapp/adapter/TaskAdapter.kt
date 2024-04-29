package com.example.todoapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.EachTodoItemBinding
import com.example.todoapp.model.ToDoData

class TaskAdapter(private val itemList: MutableList<ToDoData>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val TAG = "TaskAdapter"
    private var listener: TaskAdapterInterface? = null

    fun setListener(listener: TaskAdapterInterface?) {
        this.listener = listener
    }

    inner class TaskViewHolder(val binding: EachTodoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ToDoData, position: Int) {
            with(binding) {
                todoTask.text = item.task

                editTask.setOnClickListener {
                    listener?.onEditItemClicked(item, position)
                }

                deleteTask.setOnClickListener {
                    listener?.onDeleteItemClicked(item, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = EachTodoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.bind(currentItem, position)
        Log.d(TAG, "onBindViewHolder: Task at position $position bound.")
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    interface TaskAdapterInterface {
        fun onDeleteItemClicked(toDoData: ToDoData, position: Int)
        fun onEditItemClicked(toDoData: ToDoData, position: Int)
    }
}
