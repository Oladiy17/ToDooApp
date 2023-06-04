package com.example.todoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAddToDoPopUpBinding
import com.example.todoapp.utils.ToDoData
import com.google.android.material.textfield.TextInputEditText


class AddToDoPopUpFragment : DialogFragment() {

    private lateinit var binding: FragmentAddToDoPopUpBinding
    private lateinit var listener: DialogNextBtnClickListeners
    private var toDoData: ToDoData? = null

    fun setListener(listener: DialogNextBtnClickListeners) {
        this.listener = listener
    }

    companion object {
        const val TAG = "AddToDOPopupFragment"

        @JvmStatic
        fun newInstance(taskId: String, task: String) = AddToDoPopUpFragment().apply {
            arguments = Bundle().apply {
                putString("taskId", taskId)
                putString("task", task)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddToDoPopUpBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (arguments != null) {
            toDoData = ToDoData(
                arguments?.getString("taskId").toString(),
                arguments?.getString("task").toString()
            )

            binding.todoEt.setText(toDoData?.task)
        }
        registerEvents()
    }

    private fun registerEvents() {
        binding.BtnAddTask.setOnClickListener {
            val todoTask = binding.todoEt.text.toString()
            if (todoTask.isNotEmpty()) {
                if (toDoData == null){
                    listener.onSaveTask(todoTask, binding.todoEt)
                }else{
                    toDoData?.task = todoTask
                    listener.onUpdateTask(toDoData!!, binding.todoEt)
                }
            } else {
                Toast.makeText(context, "Введіть свій таск!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.todoClose.setOnClickListener {
            dismiss()
        }
    }

    interface DialogNextBtnClickListeners {
        fun onSaveTask(todo: String, todoEt: TextInputEditText)
        fun onUpdateTask(toDoData: ToDoData, todoEt: TextInputEditText)
    }

}