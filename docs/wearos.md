# WearOS Module

The `wearos` module is a standalone smartwatch application that runs independently on Wear OS devices. It does not require the companion phone app to be installed.

---

## Module Identity

| Property | Value |
|---|---|
| Module path | `:wearos` |
| Namespace | `mx.dev1.pomodoro.wearos` |
| Min SDK | 30 (WearOS minimum) |
| Target / Compile SDK | 36 |
| Java Compatibility | 11 |

---

## Manifest

**File:** `wearos/src/main/AndroidManifest.xml`

### Permissions

```xml
<uses-permission android:name="android.permission.WAKE_LOCK" />
```

Required to keep the display awake during an active Pomodoro session.

### Hardware Feature

```xml
<uses-feature android:name="android.hardware.type.watch" />
```

Restricts this app to watch-form-factor devices only.

### Wearable Metadata

```xml
<meta-data
    android:name="com.google.android.wearable.standalone"
    android:value="true" />
```

Marks the app as a **standalone app** — it runs fully on the watch without requiring the phone app.

---

## Entry Point

**File:** `wearos/src/main/java/mx/dev1/pomodoro/wearos/presentation/MainActivity.kt`

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            WearApp("Android")
        }
    }
}
```

- Uses AndroidX `SplashScreen` for the launch screen.
- Delegates all UI to the `WearApp` composable.
- Theme is set to `android:style/Theme.DeviceDefault` in the manifest (required for WearOS).

---

## Composables

### `WearApp`

Top-level composable wrapping all watch UI.

```kotlin
@Composable
fun WearApp(greetingName: String) {
    PomodoroTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            TimeText()
            Greeting(greetingName = greetingName)
        }
    }
}
```

- `TimeText` — Wear Compose system component that renders the current time at the top of the screen.
- `Greeting` — Centered text composable.
- Background color comes from the Wear `MaterialTheme`.

### `Greeting`

```kotlin
@Composable
fun Greeting(greetingName: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary,
        text = stringResource(R.string.hello_world, greetingName)
    )
}
```

Displays a localized greeting string. Currently a placeholder.

---

## Theme

**File:** `wearos/src/main/java/mx/dev1/pomodoro/wearos/presentation/theme/Theme.kt`

```kotlin
@Composable
fun PomodoroTheme(content: @Composable () -> Unit) {
    MaterialTheme(content = content)
}
```

Uses `androidx.wear.compose.material.MaterialTheme` — a Wear-specific variant of the Material Design theme. Custom colors and typography can be added here as the watch UI matures.

> Note: Wear Compose uses its own `MaterialTheme` from `androidx.wear.compose.material`, which is different from the phone app's `androidx.compose.material3.MaterialTheme`.

---

## Dependencies

Defined in the `wearos/build.gradle.kts` using the version catalog:

| Library | Purpose |
|---|---|
| `androidx.wear.compose.material` | Wear-specific Material Design components |
| `androidx.wear.compose.foundation` | Layout and foundation for Wear |
| `androidx.wear.tooling.preview` | Preview support in Android Studio |
| `com.google.android.gms:play-services-wearable` | Communication with the phone app |
| `androidx.core:core-splashscreen` | SplashScreen API |

---

## Current Status

The WearOS module currently contains:

- A functional scaffolding with splash screen, theme, and system time display.
- A placeholder greeting screen.

### Planned Features

- Active Pomodoro timer display on the watch face.
- Session start/stop controls via watch hardware buttons or taps.
- Notifications and haptic feedback at session boundaries.
- Data sync with the phone app via the Wearable Data Layer API (`play-services-wearable`).

---

## Running the WearOS Module

1. Open Android Studio.
2. Select the `wearos` run configuration from the configuration dropdown.
3. Target a **Wear OS emulator** (API 30+) or a physical Wear OS device.
4. Click **Run**.

For data sync testing between phone and watch, both the `app` and `wearos` modules must be installed on the same paired device pair.
