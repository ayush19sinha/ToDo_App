package com.example.todoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.todoapp.databinding.FragmentToDoDialogBinding
import com.example.todoapp.model.ToDoData
import com.google.android.material.textfield.TextInputEditText

class ToDoDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentToDoDialogBinding
    private var listener: OnDialogNextBtnClickListener? = null
    private var toDoData: ToDoData? = null

    fun setListener(listener: OnDialogNextBtnClickListener) {
        this.listener = listener
    }

    companion object {
        const val TAG = "DialogFragment"
        private const val TASK_ID_KEY = "taskId"
        private const val TASK_KEY = "task"

        @JvmStatic
        fun newInstance(taskId: String, task: String) = ToDoDialogFragment().apply {
            arguments = Bundle().apply {
                putString(TASK_ID_KEY, taskId)
                putString(TASK_KEY, task)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToDoDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { args ->
            val taskId = args.getString(TASK_ID_KEY)
            val task = args.getString(TASK_KEY)
            toDoData = ToDoData(taskId.orEmpty(), task.orEmpty())
            binding.todoEt.setText(toDoData?.task)
        }

        binding.todoClose.setOnClickListener {
            dismiss()
        }

        binding.todoNextBtn.setOnClickListener {
            val todoTask = binding.todoEt.text.toString()
            if (todoTask.isNotEmpty()) {
                toDoData?.let { data ->
                    if (data.taskId.isNotEmpty()) {
                        data.task = todoTask
                        listener?.updateTask(data, binding.todoEt)
                    } else {
                        listener?.saveTask(todoTask, binding.todoEt)
                    }
                }
            }
        }
    }

    interface OnDialogNextBtnClickListener {
        fun saveTask(todoTask: String, todoEdit: TextInputEditText)
        fun updateTask(toDoData: ToDoData, todoEdit: TextInputEditText)
    }
}
