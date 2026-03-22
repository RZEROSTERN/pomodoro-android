# Navigation

The app uses **Jetpack Navigation Compose** with a single `NavHost` defined in `MainActivity`. Navigation is driven by a bottom navigation bar and a Floating Action Button (FAB).

---

## Routes

Defined in `navigation/Routes.kt`:

| Constant | Route String | Screen |
|---|---|---|
| `Routes.HomeScreen` | `"tasks"` | Tasks (Home) |
| `Routes.HistoryScreen` | `"favorites"` | History |
| `Routes.CalendarScreen` | `"calendar"` | Calendar |
| `Routes.MyTrackerScreen` | `"my_tracker"` | My Tracker |
| `Routes.TimerScreen` | `"timer"` | Pomodoro Timer |

---

## Navigation Items

Defined in `navigation/NavigationItems.kt`. Each item is a `MainNavigationItem`:

```kotlin
data class MainNavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val isVisibleOnBottomBar: Boolean = true
)
```

| Title | Route | Visible on Bottom Bar |
|---|---|---|
| Tasks | `tasks` | Yes |
| History | `favorites` | Yes |
| Calendar | `calendar` | Yes |
| My Tracker | `my_tracker` | Yes |
| Pomodoro Timer | `timer` | **No** — accessed via FAB |

---

## Bottom Navigation Bar

Composable: `navigation/MainNavigationBar.kt`

- Renders only items where `isVisibleOnBottomBar = true`.
- Tracks selected state against the current back stack entry destination.
- On item click: navigates to the target route and calls `popUpTo(Routes.HomeScreen)` to prevent deep back stacks.
- The bar itself is **hidden** when the timer screen is active (controlled in `MainActivity`).

---

## FAB — Timer Entry Point

The Pomodoro Timer screen is not accessible from the bottom bar. It is reached via a centered **Floating Action Button** rendered in the scaffold, positioned with a `FabPosition.Center` offset. The FAB is hidden on the timer screen itself.

---

## NavHost Setup (MainActivity)

```kotlin
NavHost(
    navController = navController,
    startDestination = Routes.HomeScreen
) {
    composable(Routes.HomeScreen)      { TasksScreen() }
    composable(Routes.HistoryScreen)   { HistoryScreen() }
    composable(Routes.CalendarScreen)  { CalendarScreen() }
    composable(Routes.MyTrackerScreen) { MyTrackerScreen() }
    composable(Routes.TimerScreen)     { TimerScreen() }
}
```

---

## Navigation Flow Diagram

```
Bottom Bar
├── Tasks          → TasksScreen
├── History        → HistoryScreen
├── Calendar       → CalendarScreen
└── My Tracker     → MyTrackerScreen

FAB (Center)
└── Timer          → TimerScreen
```

---

## Adding a New Screen

1. Add a new route constant to `Routes.kt`:
   ```kotlin
   const val NewScreen = "new_screen"
   ```

2. Add a `MainNavigationItem` to `NavigationItems.kt` (set `isVisibleOnBottomBar = false` if it should not appear in the bar).

3. Register the composable in the `NavHost` inside `MainActivity`.

4. Navigate to it from any composable with:
   ```kotlin
   navController.navigate(Routes.NewScreen)
   ```
