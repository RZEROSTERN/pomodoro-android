package mx.dev1.pomodoro

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import mx.dev1.pomodoro.navigation.MainNavigationBar
import mx.dev1.pomodoro.navigation.Routes
import mx.dev1.pomodoro.ui.screens.MainScreen
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

                Scaffold (
                    topBar = { CenterAlignedTopAppBar(title = { Text(currentDestination?.route ?: "") }) },
                    bottomBar = {
                        AnimatedVisibility(
                            visible = true
                        ) {
                            MainNavigationBar(
                                navController = navController,
                                currentDestination = currentDestination
                            )
                        }
                    }
                ) { _ ->
                    NavHost(
                        navController = navController,
                        startDestination = Routes.MyTrackerScreen,
                    ) {
                        composable(Routes.HomeScreen) {}
                        composable(Routes.HistoryScreen) {}
                        composable(Routes.CalendarScreen) {}
                        composable(Routes.MyTrackerScreen) {
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