package com.example.todoapp

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddTask(
    id: Long,
    viewModel: ToDoAppViewModel,
    navController: NavController

) {
    val context = LocalContext.current


   val toDoAppData = viewModel.getTaskById(id).collectAsState(initial = ToDoAppData(0L, "", "", Date(), Date()
   ))

    var selectedDate by remember { mutableStateOf<Long?>(null) }

    LaunchedEffect(toDoAppData.value) {
        if (id != 0L) {
            // If we have a valid ID, update the task and category from the ViewModel
            viewModel.ontoDoAppDataTaskChanged(toDoAppData.value.task)
            viewModel.ontoDoAppDataCategoryChanged(toDoAppData.value.category)
        } else {
            // Reset the state when ID is 0 (for new task and category creation)
            viewModel.ontoDoAppDataTaskChanged("")  // Reset task
            viewModel.ontoDoAppDataCategoryChanged("")  // Reset category
        }
    }

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
        ){
        val texttitle : String = "Add a task"

        Spacer(modifier = Modifier.height(90.dp))
       Text(text = texttitle,
           fontWeight = FontWeight.ExtraBold,
           fontSize = 30.sp
           )
        Spacer(modifier = Modifier.height(40.dp))
        AddTaskField( textVal =viewModel.toDoAppDataTaskState,label = "Name your task", onValueChanged = {

            viewModel.ontoDoAppDataTaskChanged(it)

        }  )
        Spacer(modifier = Modifier.height(10.dp))

        AddTaskArrow(text2 = viewModel.toDoAppDataCategoryState, label ="Select your category" , onValueChanged ={
            viewModel.ontoDoAppDataCategoryChanged(it)
        } )
        Spacer(modifier = Modifier.height(10.dp))

        DatePickerScreen(onDateSelected =  {   date ->
            selectedDate = date // Update the selected date when the user selects a date
        }, viewModel)




        Spacer(modifier = Modifier.height(100.dp))

        Button(
            onClick = {
                Log.d("123", viewModel.toDoAppDataTaskState )
                Log.d("12345",viewModel.toDoAppDataCategoryState)
                // Check if task and category states are not empty
                if (viewModel.toDoAppDataTaskState.isNotEmpty() && viewModel.toDoAppDataCategoryState.isNotEmpty()) {
                    // If the ID is not zero, update the existing task
                    if (id != 0L) {
                        viewModel.updateTask(
                            ToDoAppData(
                                id = id,
                                task = viewModel.toDoAppDataTaskState.trim(),
                                category = viewModel.toDoAppDataCategoryState.trim(),
                                date = viewModel.toDoAppDateState,
                                time = Date()
                                // Ensure to set a valid date if necessary
                            )
                        )
                        navController.navigateUp()
                        // Optionally, show a success message
                        Toast.makeText(context, "Task updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        // If the ID is zero, add a new task
                        viewModel.addTask(
                            ToDoAppData(
                                task = viewModel.toDoAppDataTaskState.trim(),
                                category = viewModel.toDoAppDataCategoryState.trim(),
                                date = viewModel.toDoAppDateState, // Ensure to set a valid date if necessary
                                time = Date()
                                )


                        )
                        // Show a success message
                        Toast.makeText(context, "Task added successfully", Toast.LENGTH_SHORT).show()
                        // Navigate back to the previous screen
                        navController.navigateUp()
                    }
                } else {
                    // Show an error message if task or category is empty
                    Toast.makeText(context, "Task and category cannot be empty", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor= Color.Black,
                contentColor = Color.White,
            ),
            modifier = Modifier.width(250.dp),
            shape = RoundedCornerShape(10.dp)
            ) {
            Text(text = if (id != 0L) "Update Task" else "Save")

        }
    }
}






@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskField(
    textVal:String,
    label:String,
    onValueChanged: (String) -> Unit

){
    TextField(
        value = textVal,
        onValueChange = onValueChanged,
        label = { Text(text = label, color = Color.Black) },
        modifier = Modifier
            .padding(10.dp)
            .widthIn(max = 250.dp),
        maxLines = 3,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Black, // Focused underline color
            unfocusedIndicatorColor = Color.Black // Unfocused underline color
        )

    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskArrow(
    text2: String,
    label: String,
    onValueChanged: (String) -> Unit
) {
    var iExpanded by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf(text2) } // Initialize with the current category value
    val context = LocalContext.current
    // TextField to show the category
    
    

    TextField(
        modifier = Modifier
            .padding(10.dp)
            .widthIn(max = 250.dp),
        value = inputText,
        onValueChange = {},
        label = { Text(text = label, color = Color.Black) },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Black, // Focused underline color
            unfocusedIndicatorColor = Color.Black // Unfocused underline color
        ),
        trailingIcon = {
            IconButton(
                onClick = { iExpanded = true }){
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }
            // Dropdown menu for selecting categories
            DropdownMenu(
                expanded = iExpanded,
                onDismissRequest = { iExpanded = false },
                modifier = Modifier.background(Color.Transparent)
            ) {
                // Menu items for different categories
                listOf("Person", "Work", "Health", "Family", "Learning").forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category, color = getCategoryColor(category)) },
                        leadingIcon = {
                            Icon(
                                imageVector = getCategoryIcon(category),
                                contentDescription = null,
                                tint = getCategoryColor(category)
                            )
                        },
                        onClick = {
                            inputText = category
                            onValueChanged(category)
                            iExpanded = false
                        }
                    )
                }
            }
        }
    )
}

// Helper function to get category color
@Composable
fun getCategoryColor(category: String): Color {
    return when (category) {
       "Person" -> colorResource(id = R.color.teal)
        "Work" -> colorResource(id = R.color.blue)
        "Health" -> colorResource(id = R.color.red)
        "Family" -> colorResource(id = R.color.bblack)
        "Learning" -> colorResource(id = R.color.yyellow)
        else -> Color.Gray
    }
}

// Helper function to get category icon
fun getCategoryIcon(category: String): ImageVector {
    return when (category) {
        "Person" -> Icons.Default.Person
        "Work" -> Icons.Default.Work
        "Health" -> Icons.Default.FitnessCenter
        "Family" -> Icons.Default.Home
        "Learning" -> Icons.Default.Add
        else -> Icons.Default.Help
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerScreen(onDateSelected: (Long?) -> Unit, viewModel: ToDoAppViewModel) {
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = selectedDate?.let { convertMillisToDate(it) } ?: "",
            onValueChange = {

            },
            label = { Text("Selected Date") },
            readOnly = true,
            modifier = Modifier
                .padding(10.dp)
                .widthIn(max = 250.dp),
            maxLines = 3,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Black, // Focused underline color
                unfocusedIndicatorColor = Color.Black // Unfocused underline color
            ),
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Select date")
                }
            }
        )

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        selectedDate = System.currentTimeMillis()
                        showDatePicker = false
                    }) {
                        Text("OK", modifier = Modifier,
                            color = Color.Black
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel", modifier = Modifier,
                        color = Color.Black
                        )
                    }
                }
            ) {
                val datePickerState = rememberDatePickerState()
                DatePicker(state = datePickerState)

                LaunchedEffect(viewModel.toDoAppDateState) {
                    selectedDate = viewModel.toDoAppDateState.time// Update selectedDate with the new date from ViewModel
                }

            }
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}