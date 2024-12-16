package com.example.todoapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.IconButton
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    navController: NavController,
    viewModel: ToDoAppViewModel,
) {
    val context = LocalContext.current
         var selectedItem by remember { mutableStateOf(0) }
    val scrollState = rememberLazyListState()
   //var currentDateFormatted by remember { mutableStateOf(LocalDate.now()) }

    // Function to update the date based on scroll position
  /*  LaunchedEffect(scrollState.firstVisibleItemIndex) {
        // Example logic: Change date based on the first visible item index
        // You can customize this logic based on your requirements
        currentDateFormatted = when (scrollState.firstVisibleItemIndex) {
            0 -> LocalDate.now() // Current date
            1 -> LocalDate.now().minusDays(1) // Yesterday
            2 -> LocalDate.now().minusDays(2) // Two days ago
            else -> LocalDate.now().minusDays(scrollState.firstVisibleItemIndex.toLong()) // N days ago
        }
    }  */





    Scaffold(

        bottomBar = {
            // Track selected item

            val selectedModifier = Modifier
                .background(Color.Black, shape = RoundedCornerShape(50))
                .size(30.dp)
                .then(Modifier)

            val unselectedModifier = Modifier.size(30.dp) // Normal size for unselected items

            NavigationBar(
                containerColor = Color.White,
                modifier = Modifier
                    .padding(10.dp)
                    .height(55.dp)
                    .clip(RoundedCornerShape(200.dp))
            ) {

                NavigationBarItem(
                    modifier = if (selectedItem == 0) selectedModifier else unselectedModifier,
                    selected = selectedItem == 0,
                    onClick = { selectedItem = 0 },
                    icon = {
                        Text(
                            text = "All",
                            color = if (selectedItem == 0) Color.White else Color.Gray,
                            fontSize = 12.sp
                        ) // Set custom color)


                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = if (selectedItem == 0) Color.Transparent else Color.Black
                    )
                )
                NavigationBarItem(
                    modifier = if (selectedItem == 1) selectedModifier else unselectedModifier,
                    selected = selectedItem == 1,
                    onClick = {
                        selectedItem = 1
                        navController.navigate(Screen.pendingScreen.route)
                    },
                    icon = {
                        Text(
                            text = "Pending",
                            color = if (selectedItem == 1) Color.White else Color.Gray,
                            fontSize = 12.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = if (selectedItem == 1) Color.Transparent else Color.Black
                    )
                )

                NavigationBarItem(
                    modifier = if (selectedItem == 2) selectedModifier else unselectedModifier,
                    selected = selectedItem == 2,
                    onClick = {
                        selectedItem = 2
                        navController.navigate(Screen.completedScreen.route)
                    },
                    icon = {
                        Text(
                            text = "Complete",
                            color = if (selectedItem == 2) Color.White else Color.Gray,
                            fontSize = 12.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = if (selectedItem == 2) Color.Transparent else Color.Black
                    )
                )
            }


        },

    ) {

       upperUi(navController, "Task",  floatingIcon = Icons.Default.Add, viewModel )



    Row (
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp)
        ){


            val todolist = viewModel.getAllTask.collectAsState(initial = listOf())
            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 60.dp, start = 2.dp, end = 10.dp)
                    .padding(bottom = 60.dp)
            ) {
                items(todolist.value, key = { item -> item.id }) { toDoAppData ->
                    todoItem(
                        toDoAppData = toDoAppData,
                        viewModel = viewModel,
                        navController = navController
                    ) {

                    }
                }

            }
        }

    }
}


// Function to convert Date to String
//@RequiresApi(Build.VERSION_CODES.O)
//fun setDataDate(dateVal: Date): String {
//    val dateNow = dateVal.
//    val formatter = DateTimeFormatter.ofPattern("MMMM dd")
//    val formattedDate = dateNow.format(formatter)
//    return formatter
//}
// FloatingActionButton on the righ side



    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun todoItem(
        toDoAppData: ToDoAppData,
        viewModel: ToDoAppViewModel,
        navController: NavController,
        function: () -> Unit
    ) {
        // var isChecked by remember { mutableStateOf(false) }
        var isChecked by remember { mutableStateOf(toDoAppData.isChecked) } // Remember the checkbox state

        val id = toDoAppData.id

        val formatter =
            SimpleDateFormat("HH:mm a", Locale.getDefault()) // Use SimpleDateFormat for Date
        val timeFormatted = formatter.format(toDoAppData.time)



        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp, start = 8.dp, end = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
            )

        )

        {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { },
                verticalAlignment = Alignment.CenterVertically // Align all items vertically center
            ) {


                // Checkbox for task completion
                Checkbox(


                    checked = isChecked, // Maintain the checked state based on the task's status
                    onCheckedChange = { checked ->
                        // Only update the status if the user manually interacts with the checkbox
                        if (isChecked != checked) {
                            isChecked = checked
                            val updateStatus = if (checked) "Completed" else "Pending"
                            viewModel.updateTaskStatus(
                                toDoAppData.copy(isChecked = checked, status = updateStatus)
                            )
                        }
                    },
                    modifier = Modifier.clip(RoundedCornerShape(40)),
                    colors = CheckboxDefaults.colors(
                        checkedColor = if (isChecked) colorResource(id = R.color.teal) else Color.Gray,
                        uncheckedColor = Color.Gray
                    )
                )


                // Task Text
                Text(
                    // this  code is used to display the data
                    text = toDoAppData.task + "\n" + " ${timeFormatted}",
                    modifier = Modifier
                        .weight(1f) // Occupy remaining spaceo;
                        .padding(horizontal = 8.dp)
                        .alpha(if (isChecked) 0.5f else 1f),
                    color = colorResource(id = R.color.textcolor),// Dim text if checked
                    textDecoration =
                    if (isChecked) TextDecoration.LineThrough else TextDecoration.None,
                )

                // Edit button on the right
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(36.dp) // Adjust Box size as needed
                        .background(
                            color = colorResource(id = R.color.bblack),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clip(RoundedCornerShape(8.dp)) // Apply rounded corners
                ) {
                    val context = LocalContext.current // Get the context for Toast

                    IconButton(
                        onClick = {
                            if (id != 0L) {
                                // Navigate if the id is valid (not 0L)
                                navController.navigate(Screen.addscreen.route + "/$id")
                            } else {
                                // Show a toast message if id is 0L
                                Toast.makeText(
                                    context,
                                    "No navigation - Invalid ID",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier.size(20.dp) // Adjust IconButton size within the Box
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Task",
                            tint = Color.White // Set icon color to contrast with teal background
                        )

                    }
                }
                 Spacer(modifier = Modifier.width(10.dp))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(36.dp) // Adjust Box size as needed
                        .background(
                            color = Red,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clip(RoundedCornerShape(8.dp)) // Apply rounded corners
                ) {
                    val context = LocalContext.current // Get the context for Toast

                    IconButton(
                        onClick = {
                            if (id != 0L) {
                                // Navigate if the id is valid (not 0L)
                                viewModel.deleteTask(toDoAppData)
                                Toast.makeText(
                                    context,
                                    "Task  deleted ",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                // Show a toast message if id is 0L
                                Toast.makeText(
                                    context,
                                    "Task not deleted ",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier.size(20.dp) // Adjust IconButton size within the Box
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Task",
                            tint = Color.White // Set icon color to contrast with teal background
                        )
                    }
                }

            }
        }

    }


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun upperUi(
    navController: NavController,
    title : String,
    floatingIcon: ImageVector,
    viewModel: ToDoAppViewModel,
   // currentDateFormatted: LocalDate
){
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    val toDoAppDateState = viewModel.toDoAppDateState
    val formattedDate = SimpleDateFormat("MMMM dd", Locale.getDefault()).format(toDoAppDateState)
    // Current date
  //  val dateNow = LocalDateTime.now().toLocalDate()
    //val currentDateFormatted = dateNow.format(DateTimeFormatter.ofPattern("MMMM dd"))



    Row(
        modifier = Modifier
            .fillMaxWidth()
            //.fillMaxHeight(0.25f)
            .padding(top = 50.dp, end = 20.dp, start = 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text =  formattedDate.format(DateTimeFormatter.ofPattern("MMMM dd")),
                fontSize = 17.sp,
                color = colorResource(id = R.color.lightgrey),
                modifier = Modifier.padding(start = 9.dp, top = 12.dp)

            )
        }

        FloatingActionButton(
            modifier = Modifier
                .padding(top = 15.dp)
                .size(50.dp),
            shape = CircleShape,
            containerColor = Color.Black,
            contentColor = Color.White,
            onClick = {
                //Toast.makeText(context, "FAB clicked", Toast.LENGTH_LONG).show()
                navController.navigate(Screen.addscreen.route + "/0L")
            }
        ) {
            Icon(
                imageVector = floatingIcon,
                contentDescription = null,
                modifier = Modifier.size(40.dp))
        }
    }

}




