package mx.dev1.pomodoro.wearos.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import mx.dev1.pomodoro.wearos.presentation.screens.PremiumGateScreen
import mx.dev1.pomodoro.wearos.presentation.screens.QuickStatsScreen
import mx.dev1.pomodoro.wearos.presentation.screens.SessionCompleteScreen
import mx.dev1.pomodoro.wearos.presentation.screens.TaskPickerScreen
import mx.dev1.pomodoro.wearos.presentation.screens.TimerScreen
import mx.dev1.pomodoro.wearos.presentation.theme.PomodoroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            PomodoroTheme {
                // TODO: replace with real premium check (e.g. DataStore / shared state with phone)
                val isPremium = false

                var currentScreen by rememberSaveable { mutableStateOf(
                    if (isPremium) WearScreen.Timer else WearScreen.PremiumGate
                ) }
                var completedSessions by rememberSaveable { mutableStateOf(0) }

                when (currentScreen) {
                    WearScreen.PremiumGate -> PremiumGateScreen()

                    WearScreen.Timer -> TimerScreen(
                        onSessionComplete = {
                            completedSessions++
                            currentScreen = WearScreen.SessionComplete
                        },
                        onNavigateToStats = {
                            currentScreen = WearScreen.QuickStats
                        }
                    )

                    WearScreen.TaskPicker -> TaskPickerScreen(
                        onTaskSelected = {
                            currentScreen = WearScreen.Timer
                        }
                    )

                    WearScreen.SessionComplete -> SessionCompleteScreen(
                        completedSessions = completedSessions,
                        onStartBreak = {
                            currentScreen = WearScreen.Timer
                        },
                        onStartNext = {
                            currentScreen = WearScreen.Timer
                        }
                    )

                    WearScreen.QuickStats -> QuickStatsScreen()
                }
            }
        }
    }
}
