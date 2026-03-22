package mx.dev1.pomodoro

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import mx.dev1.pomodoro.navigation.MainNavigationBar
import mx.dev1.pomodoro.navigation.NavigationItems
import mx.dev1.pomodoro.navigation.Routes
import mx.dev1.pomodoro.ui.components.PremiumBadge
import mx.dev1.pomodoro.ui.screens.AboutScreen
import mx.dev1.pomodoro.ui.screens.SaveSessionScreen
import mx.dev1.pomodoro.ui.screens.PremiumScreen
import mx.dev1.pomodoro.ui.screens.CalendarScreen
import mx.dev1.pomodoro.ui.screens.ExportDataScreen
import mx.dev1.pomodoro.ui.screens.ImportDataScreen
import mx.dev1.pomodoro.ui.screens.HistoryScreen
import mx.dev1.pomodoro.ui.screens.LoginScreen
import mx.dev1.pomodoro.ui.screens.MyTrackerScreen
import mx.dev1.pomodoro.ui.screens.NotificationsScreen
import mx.dev1.pomodoro.ui.screens.RegisterScreen
import mx.dev1.pomodoro.ui.screens.SettingsScreen
import mx.dev1.pomodoro.ui.screens.TagsScreen
import mx.dev1.pomodoro.ui.screens.TasksScreen
import mx.dev1.pomodoro.ui.screens.TimerScreen
import mx.dev1.pomodoro.ui.theme.PomodoroTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val systemDarkTheme = isSystemInDarkTheme()
            var isDarkTheme by rememberSaveable { mutableStateOf(systemDarkTheme) }

            PomodoroTheme(darkTheme = isDarkTheme) {
                UpdateSystemBarsForTheme(isDarkTheme = isDarkTheme)

                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = backStackEntry?.destination

                val currentNavigationItem = NavigationItems.list.find { it.route == currentDestination?.route }
                val drawerScreenTitle = when (currentDestination?.route) {
                    Routes.SettingsScreen -> "Settings"
                    Routes.TagsScreen -> "Tags"
                    Routes.NotificationsScreen -> "Notifications"
                    Routes.ExportDataScreen -> "Export Data"
                    Routes.ImportDataScreen -> "Import Data"
                    Routes.AboutScreen -> "About"
                    Routes.PremiumScreen -> "Go Premium"
                    Routes.SaveSessionScreen -> "Save Session"
                    else -> null
                }
                val isAuthScreen = currentDestination?.route == Routes.LoginScreen ||
                                   currentDestination?.route == Routes.RegisterScreen ||
                                   currentDestination?.route == Routes.PremiumScreen

                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            val context = LocalContext.current
                            val iconBitmap = remember {
                                ContextCompat.getDrawable(context, R.mipmap.ic_launcher_round)
                                    ?.toBitmap()
                                    ?.asImageBitmap()
                            }
                            Column(modifier = Modifier.fillMaxHeight()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.primary)
                                        .padding(24.dp)
                                ) {
                                    Column(horizontalAlignment = Alignment.Start) {
                                        iconBitmap?.let {
                                            Image(
                                                bitmap = it,
                                                contentDescription = null,
                                                modifier = Modifier.size(72.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(12.dp))
                                        Text(
                                            text = stringResource(id = R.string.app_name),
                                            style = MaterialTheme.typography.titleLarge,
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                }
                                HorizontalDivider()
                                NavigationDrawerItem(
                                    icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                                    label = { Text("Settings") },
                                    selected = currentDestination?.route == Routes.SettingsScreen,
                                    onClick = {
                                        scope.launch { drawerState.close() }
                                        navController.navigate(Routes.SettingsScreen) { popUpTo(Routes.HomeScreen) }
                                    }
                                )
                                NavigationDrawerItem(
                                    icon = { Icon(Icons.Default.Label, contentDescription = null) },
                                    label = {
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                            Text("Tags"); PremiumBadge()
                                        }
                                    },
                                    selected = currentDestination?.route == Routes.TagsScreen,
                                    onClick = {
                                        scope.launch { drawerState.close() }
                                        navController.navigate(Routes.TagsScreen) { popUpTo(Routes.HomeScreen) }
                                    }
                                )
                                NavigationDrawerItem(
                                    icon = { Icon(Icons.Default.NotificationsNone, contentDescription = null) },
                                    label = {
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                            Text("Notifications"); PremiumBadge()
                                        }
                                    },
                                    selected = currentDestination?.route == Routes.NotificationsScreen,
                                    onClick = {
                                        scope.launch { drawerState.close() }
                                        navController.navigate(Routes.NotificationsScreen) { popUpTo(Routes.HomeScreen) }
                                    }
                                )
                                NavigationDrawerItem(
                                    icon = { Icon(Icons.Default.FileDownload, contentDescription = null) },
                                    label = {
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                            Text("Export Data"); PremiumBadge()
                                        }
                                    },
                                    selected = currentDestination?.route == Routes.ExportDataScreen,
                                    onClick = {
                                        scope.launch { drawerState.close() }
                                        navController.navigate(Routes.ExportDataScreen) { popUpTo(Routes.HomeScreen) }
                                    }
                                )
                                NavigationDrawerItem(
                                    icon = { Icon(Icons.Default.FileUpload, contentDescription = null) },
                                    label = {
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                            Text("Import Data"); PremiumBadge()
                                        }
                                    },
                                    selected = currentDestination?.route == Routes.ImportDataScreen,
                                    onClick = {
                                        scope.launch { drawerState.close() }
                                        navController.navigate(Routes.ImportDataScreen) { popUpTo(Routes.HomeScreen) }
                                    }
                                )
                                NavigationDrawerItem(
                                    icon = { Icon(Icons.Default.Bookmark, contentDescription = null) },
                                    label = { Text("Save Session") },
                                    selected = currentDestination?.route == Routes.SaveSessionScreen,
                                    onClick = {
                                        scope.launch { drawerState.close() }
                                        navController.navigate(Routes.SaveSessionScreen) { popUpTo(Routes.HomeScreen) }
                                    }
                                )
                                NavigationDrawerItem(
                                    icon = { Icon(Icons.Default.Info, contentDescription = null) },
                                    label = { Text("About") },
                                    selected = currentDestination?.route == Routes.AboutScreen,
                                    onClick = {
                                        scope.launch { drawerState.close() }
                                        navController.navigate(Routes.AboutScreen) { popUpTo(Routes.HomeScreen) }
                                    }
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                // Upgrade banner
                                Card(
                                    onClick = {
                                        scope.launch { drawerState.close() }
                                        navController.navigate(Routes.PremiumScreen) { popUpTo(Routes.HomeScreen) }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp)
                                        .padding(bottom = 8.dp),
                                    colors = androidx.compose.material3.CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        Icon(Icons.Default.Star, contentDescription = null, tint = MaterialTheme.colorScheme.onTertiaryContainer, modifier = Modifier.size(20.dp))
                                        Column {
                                            Text("Go Premium", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onTertiaryContainer)
                                            Text("Unlock all features", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onTertiaryContainer)
                                        }
                                    }
                                }
                                HorizontalDivider()
                                NavigationDrawerItem(
                                    icon = {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier
                                                .size(32.dp)
                                                .border(
                                                    width = 1.5.dp,
                                                    color = MaterialTheme.colorScheme.outline,
                                                    shape = CircleShape
                                                )
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Person,
                                                contentDescription = null,
                                                modifier = Modifier.size(20.dp),
                                                tint = MaterialTheme.colorScheme.outline
                                            )
                                        }
                                    },
                                    label = { Text("Login | Register") },
                                    selected = currentDestination?.route == Routes.LoginScreen ||
                                               currentDestination?.route == Routes.RegisterScreen,
                                    onClick = {
                                        scope.launch { drawerState.close() }
                                        navController.navigate(Routes.LoginScreen) { popUpTo(Routes.HomeScreen) }
                                    }
                                )
                            }
                        }
                    }
                ) {
                    Scaffold(
                        topBar = {
                            CenterAlignedTopAppBar(
                                title = { Text(drawerScreenTitle ?: currentNavigationItem?.title ?: "") },
                                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.surface,
                                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                                    actionIconContentColor = MaterialTheme.colorScheme.onSurface,
                                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                                ),
                                navigationIcon = {
                                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                        Icon(
                                            imageVector = Icons.Default.Menu,
                                            contentDescription = "Open menu"
                                        )
                                    }
                                },
                                actions = {
                                    IconButton(onClick = { isDarkTheme = !isDarkTheme }) {
                                        Icon(
                                            imageVector = if (isDarkTheme) Icons.Default.NightsStay else Icons.Default.WbSunny,
                                            contentDescription = if (isDarkTheme) "Dark mode" else "Light mode"
                                        )
                                    }
                                }
                            )
                        },
                        bottomBar = {
                            AnimatedVisibility(
                                visible = !isAuthScreen
                            ) {
                                MainNavigationBar(
                                    navController = navController,
                                    currentDestination = currentDestination
                                )
                            }
                        },
                        floatingActionButton = {
                            AnimatedVisibility(visible = !isAuthScreen) {
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
                            }
                        },
                        floatingActionButtonPosition = FabPosition.Center
                    ) { innerPadding ->
                        NavHost(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                            startDestination = Routes.HomeScreen,
                        ) {
                            composable(Routes.HomeScreen) {
                                TasksScreen()
                            }
                            composable(Routes.HistoryScreen) {
                                HistoryScreen(onNavigateToPremium = {
                                    navController.navigate(Routes.PremiumScreen) { popUpTo(Routes.HomeScreen) }
                                })
                            }
                            composable(Routes.CalendarScreen) {
                                CalendarScreen(onNavigateToPremium = {
                                    navController.navigate(Routes.PremiumScreen) { popUpTo(Routes.HomeScreen) }
                                })
                            }
                            composable(Routes.MyTrackerScreen) {
                                MyTrackerScreen(onNavigateToPremium = {
                                    navController.navigate(Routes.PremiumScreen) { popUpTo(Routes.HomeScreen) }
                                })
                            }
                            composable(Routes.TimerScreen) {
                                TimerScreen(
                                    onSaveSession = {
                                        navController.navigate(Routes.SaveSessionScreen)
                                    }
                                )
                            }
                            composable(Routes.SettingsScreen) {
                                SettingsScreen()
                            }
                            composable(Routes.TagsScreen) {
                                TagsScreen(onNavigateToPremium = {
                                    navController.navigate(Routes.PremiumScreen) { popUpTo(Routes.HomeScreen) }
                                })
                            }
                            composable(Routes.NotificationsScreen) {
                                NotificationsScreen(onNavigateToPremium = {
                                    navController.navigate(Routes.PremiumScreen) { popUpTo(Routes.HomeScreen) }
                                })
                            }
                            composable(Routes.ExportDataScreen) {
                                ExportDataScreen(onNavigateToPremium = {
                                    navController.navigate(Routes.PremiumScreen) { popUpTo(Routes.HomeScreen) }
                                })
                            }
                            composable(Routes.ImportDataScreen) {
                                ImportDataScreen(onNavigateToPremium = {
                                    navController.navigate(Routes.PremiumScreen) { popUpTo(Routes.HomeScreen) }
                                })
                            }
                            composable(Routes.SaveSessionScreen) {
                                SaveSessionScreen(
                                    onNavigateToLogin = {
                                        navController.navigate(Routes.LoginScreen) { popUpTo(Routes.HomeScreen) }
                                    },
                                    onNavigateToPremium = {
                                        navController.navigate(Routes.PremiumScreen) { popUpTo(Routes.HomeScreen) }
                                    }
                                )
                            }
                            composable(Routes.AboutScreen) {
                                AboutScreen()
                            }
                            composable(Routes.PremiumScreen) {
                                PremiumScreen(onNavigateBack = { navController.popBackStack() })
                            }
                            composable(Routes.LoginScreen) {
                                LoginScreen(
                                    onNavigateToRegister = {
                                        navController.navigate(Routes.RegisterScreen)
                                    }
                                )
                            }
                            composable(Routes.RegisterScreen) {
                                RegisterScreen(
                                    onNavigateToLogin = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun UpdateSystemBarsForTheme(isDarkTheme: Boolean) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !isDarkTheme
                isAppearanceLightNavigationBars = !isDarkTheme
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
