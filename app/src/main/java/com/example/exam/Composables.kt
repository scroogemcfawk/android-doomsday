package com.example.exam

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.exam.ui.theme.CLR_BLACK
import com.example.exam.ui.theme.CLR_BLUE
import com.example.exam.ui.theme.CLR_DARK
import com.example.exam.ui.theme.CLR_GRAY
import com.example.exam.ui.theme.CLR_GREEN
import com.example.exam.ui.theme.CLR_LIGHT
import com.example.exam.ui.theme.CLR_RED
import com.example.exam.ui.theme.CLR_WHITE
import com.example.exam.ui.theme.CLR_YELLOW
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun textFieldColors(): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedContainerColor = CLR_WHITE,
        unfocusedIndicatorColor = CLR_GRAY,
        focusedIndicatorColor = CLR_BLUE,
        focusedTextColor = CLR_BLACK,
        unfocusedContainerColor = CLR_WHITE,
        unfocusedTextColor = CLR_DARK
    )
}

@Composable
fun MainScreen(viewModel: CoolestViewModel, onCreateTask: () -> Unit = {}) {
    Box(modifier = Modifier.fillMaxSize()) {
        PlusButton(viewModel = viewModel)
        val shape = RoundedCornerShape(24.dp)
        val buttonSize = 64.dp
        val paddingSize = 16.dp
        Button(
            onClick = onCreateTask,
            shape = shape,
            modifier = Modifier
                .width(buttonSize + paddingSize)
                .height(buttonSize + paddingSize)
                .shadow(24.dp, shape = shape, ambientColor = Color.Black)
                .align(Alignment.BottomEnd)
                .padding(0.dp, 0.dp, paddingSize, paddingSize)
        ) {
            Text(
                text = "+",
                fontSize = 32.sp
            )
        }
    }
}

@Composable
fun EmptyMainPlaceholder() {
    val img = R.drawable.empty_main_placeholder
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(1.0f))

        Image(
            contentScale = ContentScale.Crop,
            painter = painterResource(id = img),
            contentDescription = "Happy bald woman",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Text(
            text = "You've got nothing to do...",
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = "Chill vibes!",
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1.0f))

    }
}

@Composable
fun TaskList(viewModel: CoolestViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Tasks",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left
        )
        LazyColumn {
            val t = viewModel.taskSupplier.tasks.toList()
            items(t.sortedBy { if (it.second.complete.value) 1 else 0  }) {
                TaskCard(it.second) {
                    viewModel.select(it.first)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCard(task: Task, onClick: () -> Unit = {}) {
    val col = if (task.complete.value) CLR_GRAY else priorityColorMapper.get(task.priority) ?: CLR_BLACK
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        onClick = { onClick() }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .background(col)
                .padding(16.dp, 8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = task.header,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = CLR_WHITE
                )
                Text(
                    text = task.description,
                    fontSize = 16.sp,
                    color = CLR_WHITE
                )
            }
            Checkbox(
                checked = task.complete.value,
                onCheckedChange = {
                    task.toggle()
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = CLR_WHITE,
                    checkmarkColor = col,
                    uncheckedColor = CLR_WHITE
                )
            )
        }
    }
}

@Composable
fun PlusButton(viewModel: CoolestViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Not Forgot",
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            if (viewModel.isMainEmpty()) {
                EmptyMainPlaceholder()
            } else {
                TaskList(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(viewModel: CoolestViewModel) {
    var headerState by remember{ mutableStateOf("") }
    var descriptionState by remember{ mutableStateOf("") }
    var expanded = remember {
        mutableStateOf(false)
    }
    var priorityState = remember {
        mutableStateOf(Priority.LOW)
    }
    var deadLineDaysState = remember {
        mutableStateOf("7")
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxSize()
    ) {
        NavigationBar(
            text = "Add Task",
            backwardNavigation = { viewModel.navController.navigate(TopLevelScreen.MAIN.name) }
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
//                .background(CLR_YELLOW)
        ) {
            Text(
                text = "Header",
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
//                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp)
            )
            TextField(
                value = headerState,
                onValueChange = { headerState = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 16.dp),
                colors = textFieldColors()
            )
            OutlinedTextField(
                value = descriptionState,
                onValueChange = {
                    descriptionState = if (it.length > 50) it.slice(0..49) else it
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = textFieldColors(),
                label = {
                    Text(text = "Description")
                },
                minLines = 3,
                maxLines = 5
            )
            Text(
                text = "${descriptionState.length}/50",
                textAlign = TextAlign.Right,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 16.dp),
            )
            DeadlinePicker(
                deadLineDaysState,
                modifier = Modifier.padding(0.dp,0.dp,0.dp,16.dp)
            )
            PriorityDropDown(
                expandedState = expanded,
                priorityState = priorityState,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.height(40.dp)
            ) {
//                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        if (headerState.isNotBlank() && deadLineDaysState.value.toIntOrNull() != null) {
                            viewModel.addTask(Task(
                                headerState,
                                descriptionState,
                                LocalDateTime.now().plusDays(deadLineDaysState.value.toLong()),
                                priorityState.value
                            ))
                            viewModel.navController.navigate(TopLevelScreen.MAIN.name)
                        }
                    },
                    modifier = Modifier.weight(3f),
                ) {
                    Text(
                        text = "Save",
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentHeight(),
                        textAlign = TextAlign.Center
                    )
                }
//                Spacer(modifier = Modifier.weight(1f))
            }

        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun DeadlinePicker(deadlineState: MutableState<String>, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            label = {
                Text(text = "Days until a deadline")
            },
            value = if ((deadlineState.value.toIntOrNull() ?: 0) > 0) deadlineState.value else "",
            onValueChange = ovc@{
                val num = it.toIntOrNull() ?: it.dropLast(1).toIntOrNull() ?: "" // 😭😭😭
                deadlineState.value = num.toString()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriorityDropDown(
    expandedState: MutableState<Boolean>,
    priorityState: MutableState<Priority>,
    modifier: Modifier = Modifier
) {
    ExposedDropdownMenuBox(
        expanded = expandedState.value,
        onExpandedChange = {
            expandedState.value = !expandedState.value
        },
        modifier = modifier
            .fillMaxWidth(),
    ) {
        TextField(
            value = priorityState.value.name,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedState.value) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = CLR_BLACK,
                focusedContainerColor = CLR_LIGHT,
                unfocusedTextColor = CLR_DARK,
                unfocusedContainerColor = CLR_LIGHT,
                focusedIndicatorColor = priorityColorMapper[priorityState.value] ?: CLR_GRAY
            )
        )
        ExposedDropdownMenu(
            expanded = expandedState.value,
            onDismissRequest = {
                expandedState.value = !expandedState.value
            },
            modifier = Modifier.background(CLR_LIGHT)
        ) {
            for (p in Priority.entries) {
                DropdownMenuItem(
                    text = { Text(text = p.name) },
                    onClick = {
                        priorityState.value = p
                        expandedState.value = false
                    },
                    modifier = Modifier.background(CLR_LIGHT),
                    colors = MenuDefaults.itemColors(
                        textColor = CLR_BLACK,
                    )
                )
            }
        }
    }
}

@Composable
fun NavigationBar(
    text: String,
    backwardNavigation: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
//            .background(CLR_RED)
    ) {
        Button(
            onClick = backwardNavigation,
            colors = ButtonDefaults.buttonColors(
                containerColor = CLR_WHITE,
                contentColor = CLR_BLACK
            )
        ) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
        }
        Text(
            text = text,
            textAlign = TextAlign.Left,
            fontSize = 32.sp,
            modifier = Modifier
                .fillMaxSize()
//                .background(CLR_YELLOW)
                .wrapContentHeight(align = Alignment.CenterVertically)
        )
    }
}

@Composable
fun ViewTaskScreen(viewModel: CoolestViewModel) {
    val task = viewModel.getSelected()
    Column(
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        NavigationBar(text = "Note Info") {
            viewModel.navController.navigate(TopLevelScreen.MAIN.name)
        }
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
//                    .background(CLR_RED)
                    .height(40.dp)
//                    .fillMaxWidth()
            ) {
                Text(
                    text = task.header,
                    fontWeight = FontWeight.Medium,
                    fontSize = 32.sp,
                    modifier = Modifier
//                    .background(CLR_RED)

                )
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = CLR_BLACK,
                        containerColor = CLR_WHITE
                    ),
//                    modifier = Modifier.width(64.dp)
                ) {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = "Edit",
//                        modifier = Modifier.padding((0).dp)
                    )
                }
            }
            Text(
                text = if (task.complete.value) "Complete" else "In progress",
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = if (task.complete.value) CLR_GREEN else CLR_GRAY,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp)
            )
            Text(
                text = task.description,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                color = CLR_BLACK,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.DateRange, contentDescription = "Deadline Icon")
                Text(
                    text = "Deadline: " + task.deadline.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    color = CLR_BLACK,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(4.dp, 0.dp, 4.dp, 0.dp)
                )
                Text(
                    text = task.priority.name,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(
                            priorityColorMapper[task.priority] ?: CLR_BLACK,
                            shape = RoundedCornerShape(8.dp)
                        ).height(32.dp).wrapContentHeight().padding(4.dp)
                )
            }
        }
    }
}