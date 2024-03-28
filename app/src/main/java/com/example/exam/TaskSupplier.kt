package com.example.exam

import java.time.LocalDateTime
import kotlin.random.Random

class TaskSupplier(val tasks: HashMap<Int, Task>) {

    private var id = 0;

    private fun nextId(): Int {
        return id++
    }


    constructor() : this(HashMap()) {
        init()
//        init()
    }

    fun add(task: Task) {
        tasks[nextId()] = task
    }

    private fun init() {
        val rand = Random(0)
        val list = mapOf(
            nextId() to Task(
                "Анжуманя",
                "10 подход по 10 повтор",
                LocalDateTime.of(2024, 4, 6, 20, 49),
                Priority.URGENT
            ),
            nextId() to Task(
                "Бегит",
                "1 подход по 100 повтор",
                LocalDateTime.of(2024, 4, 6, 20, 49),
                Priority.LOW
            ),
            nextId() to Task(
                "Прес качат",
                "100 подход по 1 повтор",
                LocalDateTime.of(2024, 4, 6, 20, 49),
                Priority.HIGH
            ),
            nextId() to Task(
                "Ест протеины",
                "1 стакан с молоком и корицей",
                LocalDateTime.of(2024, 4, 6, 20, 49),
                Priority.MEDIUM
            ),
        )
        for (e in list) {
            tasks[e.key] = e.value
        }
    }
}