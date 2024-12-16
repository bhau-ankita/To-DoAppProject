import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todoapp.R
import com.example.todoapp.Screen
import com.example.todoapp.ToDoAppViewModel
import com.example.todoapp.todoItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CompletedTask(
    navController: NavController,
    viewModel: ToDoAppViewModel
) {

    val context = LocalContext.current
    var selectedItem by remember { mutableStateOf(2) }

    val dateNow = LocalDateTime.now().toLocalDate()
    val formatter = DateTimeFormatter.ofPattern("MMMM dd")
    val formattedDate = dateNow.format(formatter)



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
                    onClick = { selectedItem =0
                        navController.navigate(Screen.taskScreen.route)},
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
                    onClick = { selectedItem = 2 },
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


        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
               // .fillMaxHeight(0.50f)
                .padding(top = 50.dp, end = 20.dp, start = 7.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Completed",
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = formattedDate.toString(),
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
                containerColor = colorResource(id = R.color.teal),
                contentColor = Color.White,
                onClick = {
                    Toast.makeText(context, "FAB clicked", Toast.LENGTH_LONG).show()
                    navController.navigate(Screen.addscreen.route + "/0L")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp)
        ) {
            val todolist = viewModel.getAllTask.collectAsState(initial = listOf())
            val completedTasks = todolist.value.filter { it.status == "Completed" } // Filter only completed tasks

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 60.dp, start = 2.dp, end = 10.dp)
            ) {
                items(completedTasks, key = { item -> item.id }) { toDoAppData ->
                    // Display the task without swipe-to-delete functionality
                    todoItem(
                        toDoAppData = toDoAppData,
                        viewModel,
                        navController = navController
                    ) {
                       // val id = toDoAppData.id
                       // navController.navigate(Screen.addscreen.route + "/${id}")
                    }
                }
            }
        }

    }
}