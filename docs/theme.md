# Theme & Styling

The theming system is defined in `app/src/main/java/mx/dev1/pomodoro/ui/theme/` and is based on **Material Design 3**.

---

## Files

| File | Responsibility |
|---|---|
| `Color.kt` | Brand color palette |
| `Theme.kt` | `PomodoroTheme` composable, dark/light mode, dynamic color |
| `Type.kt` | Typography configuration |

---

## Colors

**File:** `ui/theme/Color.kt`

The app defines a small palette of brand colors used as seeds for Material3's color scheme generation.

### Light Theme Seed Colors

| Name | Hex | Usage |
|---|---|---|
| `Purple40` | `#6650a4` | Primary |
| `PurpleGrey40` | `#625b71` | Secondary |
| `Pink40` | `#7d5260` | Tertiary |

### Dark Theme Seed Colors

| Name | Hex | Usage |
|---|---|---|
| `Purple80` | `#d0bcff` | Primary |
| `PurpleGrey80` | `#ccc2dc` | Secondary |
| `Pink80` | `#efb8c8` | Tertiary |

> These seed colors are passed to `darkColorScheme()` and `lightColorScheme()`. Material3 automatically generates all remaining tonal palette values (surface, background, on-colors, containers, etc.) from these seeds.

---

## Theme Composable

**File:** `ui/theme/Theme.kt`

```kotlin
@Composable
fun PomodoroTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
)
```

### Behavior

| Condition | Color Source |
|---|---|
| `dynamicColor = true` AND Android 12+ | Material You (wallpaper-based dynamic colors) |
| `dynamicColor = false` OR Android < 12 AND `darkTheme = true` | `DarkColorScheme` (brand colors) |
| `dynamicColor = false` OR Android < 12 AND `darkTheme = false` | `LightColorScheme` (brand colors) |

### Usage in MainActivity

`MainActivity` exposes a toggle to switch between light and dark mode at runtime. The `darkTheme` boolean state is stored with `rememberSaveable` and passed down to `PomodoroTheme`.

```kotlin
var isDarkTheme by rememberSaveable { mutableStateOf(false) }

PomodoroTheme(darkTheme = isDarkTheme) {
    // App content
}
```

The system bar colors are updated reactively whenever `isDarkTheme` changes using `SideEffect` + `WindowCompat`.

---

## Typography

**File:** `ui/theme/Type.kt`

The project uses the default **Material3 Typography** with one customization:

```kotlin
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    // All other styles inherit Material3 defaults
)
```

### Material3 Text Roles Used in Components

| Role | Example Usage |
|---|---|
| `headlineSmall` | Section headers in HistoryScreen |
| `titleSmall` | Date labels in section headers |
| `bodyLarge` | Default body text |
| `bodyMedium` | Card descriptions |
| `labelSmall` | Chip labels, secondary info |

---

## Edge-to-Edge Layout

`MainActivity` calls `enableEdgeToEdge()` from AndroidX, allowing the app to draw behind the system status and navigation bars. The scaffold respects `WindowInsets.safeDrawing` so that content is not obscured by system bars.

---

## Dark / Light Mode Toggle

A theme toggle icon button is placed in the `TopAppBar` of `MainActivity`. Tapping it flips the `isDarkTheme` state, which propagates to `PomodoroTheme` and triggers a full recomposition of all themed content.

This is a runtime toggle independent of the system setting — useful during development and for users who prefer to override the system theme.
