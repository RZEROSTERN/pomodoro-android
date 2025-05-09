package mx.dev1.pomodoro.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info

object NavigationItems {
    val list = listOf(
        MainNavigationItem(
            title = "Home",
            route = Routes.HomeScreen,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        MainNavigationItem(
            title = "History",
            route = Routes.HistoryScreen,
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info
        ),
        MainNavigationItem(
            title = "Calendar",
            route = Routes.CalendarScreen,
            selectedIcon = Icons.Filled.DateRange,
            unselectedIcon = Icons.Outlined.DateRange
        ),
        MainNavigationItem(
            title = "My Tracker",
            route = Routes.MyTrackerScreen,
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle
        ),
        MainNavigationItem(
            title = "Pomodoro Timer",
            route = Routes.TimerScreen,
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle,
            isVisibleOnBottomBar = false
        )
    )
}