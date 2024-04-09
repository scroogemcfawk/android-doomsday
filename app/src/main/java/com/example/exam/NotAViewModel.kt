package com.example.exam

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class NotAViewModel(
    val navController: NavController
) : ViewModel() {

    var mainScreenState by mutableStateOf(MainScreen.EMPTY)
    val taskSupplier = TaskSupplier()
    var taskInit = false
    var selectedTaskState by mutableStateOf(0)

    fun isMainEmpty(): Boolean {
        return mainScreenState == MainScreen.EMPTY
    }

    fun select(id: Int) {
        selectedTaskState = id
        navController.navigate(TopLevelScreen.VIEW_TASK.name)
    }

    fun delete(id: Int) {
        taskSupplier.delete(id)
    }

    fun getSelected(): Task {
        return taskSupplier.tasks[selectedTaskState]!! // yeah, right?
    }

    fun updateSelected(task: Task) {
        taskSupplier[selectedTaskState] = task
    }

    fun createTask() {
        if (!taskInit) {
            mainScreenState = MainScreen.NOT_EMPTY
            taskInit = true
            return
        }
        navController.navigate(TopLevelScreen.CREATE_TASK.name)
    }

    fun addTask(task: Task) {
        taskSupplier.add(task)
    }

    fun getTasks(): Map<Int, Task> {
        return taskSupplier.tasks
    }

    fun toggleTask(id: Int) {
        val task = taskSupplier.tasks[id] ?: return
        task.complete.value = !task.complete.value
    }

    fun getTask(id: Int): Task {
        return taskSupplier.tasks[id]!!
    }

}