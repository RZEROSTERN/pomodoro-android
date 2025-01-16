package mx.dev1.pomodoro.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class MainNavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
