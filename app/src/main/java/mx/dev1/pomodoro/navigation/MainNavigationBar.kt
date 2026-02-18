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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination

@Composable
fun MainNavigationBar(
    navController: NavController,
    currentDestination: NavDestination?
) {
    NavigationBar {
        NavigationItems.list.forEach { item ->
            if(item.isVisibleOnBottomBar) {
                NavigationBarItem(
                    label = {
                        Text(
                            text = item.title
                        )
                    },
                    selected = currentDestination?.route == item.route,
                    onClick = {
                        if (currentDestination?.route != item.route) {
                            navController.navigate(item.route) {
                                popUpTo(Routes.HomeScreen)
                            }
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
}

@Preview
@Composable
fun MainNavigationBarPreview() {
    MainNavigationBar(
        navController = NavController(LocalContext.current),
        currentDestination = null
    )
}
