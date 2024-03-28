package com.example.exam

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.exam.ui.theme.CLR_BLUE
import com.example.exam.ui.theme.CLR_GREEN
import com.example.exam.ui.theme.CLR_RED
import com.example.exam.ui.theme.CLR_YELLOW
import java.time.LocalDateTime

data class Task(
    val header: String,
    val description: String,
    val deadline: LocalDateTime,
    val priority: Priority,
    var complete: MutableState<Boolean> = mutableStateOf(false) // IDK HEEEELP 😭😭😭😭
) {
    fun toggle() {
        complete.value = !complete.value
    }
}

enum class Priority {
    URGENT,
    HIGH,
    MEDIUM,
    LOW
}

val priorityColorMapper = mapOf(
    Priority.URGENT to CLR_BLUE,
    Priority.HIGH to CLR_RED,
    Priority.MEDIUM to CLR_YELLOW,
    Priority.LOW to CLR_GREEN,
)
