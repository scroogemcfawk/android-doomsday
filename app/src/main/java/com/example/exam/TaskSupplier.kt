package com.example.exam

import java.time.LocalDate
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

    operator fun set(id: Int, task: Task) {
        tasks[id] = task
    }

    fun add(task: Task) {
        tasks[nextId()] = task
    }

    fun delete(id: Int) {
        tasks.remove(id)
    }

    private fun init() {
        val rand = Random(0)
        val list = mapOf(
            nextId() to Task(
                "Анжуманя",
                "10 подход по 10 повтор",
                LocalDate.of(2024, 4, 6),
                Priority.URGENT
            ),
            nextId() to Task(
                "Бегит",
                "1 подход по 100 повтор",
                LocalDate.of(2024, 4, 6),
                Priority.LOW
            ),
            nextId() to Task(
                "Прес качат",
                "100 подход по 1 повтор",
                LocalDate.of(2024, 4, 6),
                Priority.HIGH
            ),
            nextId() to Task(
                "Ест протеины",
                "1 стакан с молоком и корицей",
                LocalDate.of(2024, 4, 6),
                Priority.MEDIUM
            ),
        )
        for (e in list) {
            tasks[e.key] = e.value
        }
    }
}