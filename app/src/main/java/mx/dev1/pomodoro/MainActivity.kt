package mx.dev1.pomodoro

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import mx.dev1.pomodoro.navigation.MainNavigationBar
import mx.dev1.pomodoro.navigation.NavigationItems
import mx.dev1.pomodoro.navigation.Routes
import mx.dev1.pomodoro.ui.screens.MyTrackerScreen
import mx.dev1.pomodoro.ui.screens.TimerScreen
import mx.dev1.pomodoro.ui.theme.PomodoroTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PomodoroTheme {
                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = backStackEntry?.destination

                val currentNavigationItem = NavigationItems.list.find { it.route == currentDestination?.route }

                Scaffold (
                    topBar = { CenterAlignedTopAppBar(title = { Text(currentNavigationItem?.title ?: "") }) },
                    bottomBar = {
                        AnimatedVisibility(
                            visible = true
                        ) {
                            MainNavigationBar(
                                navController = navController,
                                currentDestination = currentDestination
                            )
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                navController.navigate(Routes.TimerScreen) {
                                    popUpTo(Routes.HomeScreen)
                                }
                            },
                            shape = CircleShape,
                            modifier = Modifier.absoluteOffset(y = 40.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center
                ) { _ ->
                    NavHost(
                        navController = navController,
                        startDestination = Routes.MyTrackerScreen,
                    ) {
                        composable(Routes.HomeScreen) {}
                        composable(Routes.HistoryScreen) {}
                        composable(Routes.CalendarScreen) {}
                        composable(Routes.MyTrackerScreen) {
                            MyTrackerScreen()
                        }
                        composable(Routes.TimerScreen) {
                            TimerScreen()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PomodoroTheme {
        TimerScreen()
    }
}