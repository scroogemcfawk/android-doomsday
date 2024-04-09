package com.example.exam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.exam.ui.theme.ExamTheme

//   ______________________________
// / \                             \.
//|   |                            |.
// \_ |                            |.
//    |         Of course          |.
//    |        this app is         |.
//    |           shit.            |.
//    |                            |.
//    |                            |.
//    |                            |.
//    |         I made it.         |.
//    |                            |.
//    |   _________________________|___
//    |  /                            /.
//    \_/____________________________/.

enum class TopLevelScreen {
    MAIN,
    CREATE_TASK,
    VIEW_TASK,
    EDIT_TASK
}

enum class MainScreen {
    EMPTY,
    NOT_EMPTY
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModel = NotAViewModel(navController)
            ExamTheme(
                dynamicColor = false,
                darkTheme = true
            ) {
                NavHost(
                    navController = navController,
                    startDestination = TopLevelScreen.MAIN.name
                ) {
                    composable(TopLevelScreen.MAIN.name) {
                        MainScreen(viewModel) {
                            viewModel.createTask()
                        }
                    }
                    composable(TopLevelScreen.CREATE_TASK.name) {
                        CreateTaskScreen(viewModel = viewModel)
                    }
                    composable(TopLevelScreen.VIEW_TASK.name) {
                        ViewTaskScreen(viewModel = viewModel)
                    }
                    composable(TopLevelScreen.EDIT_TASK.name) {
                        EditTaskScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}