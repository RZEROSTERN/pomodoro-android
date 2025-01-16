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
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavDestination

@Composable
fun MainNavigationBar(
    navController: NavController,
    currentDestination: NavDestination?
) {
    val navigationItems = listOf(
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
        )
    )

    NavigationBar {
        navigationItems.forEach { item ->
            NavigationBarItem(
                label = {
                    Text(
                        text = item.title
                    )
                },
                selected = currentDestination?.route == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(Routes.HomeScreen)
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (currentDestination?.route == item.route) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title
                    )
                }
            )
        }
    }
}