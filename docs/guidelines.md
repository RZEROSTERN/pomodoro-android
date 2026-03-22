# Development Guidelines

This document summarizes the coding standards and contribution rules for the Pomodoro project. The authoritative source is the `AGENTS.md` file at the project root.

---

## General Principles

- Write code that is readable, maintainable, and purposefully simple.
- Avoid over-engineering. Solve the problem at hand, not hypothetical future problems.
- Every feature and bug fix should be accompanied by tests.

---

## UI Development

### Compose-First

All UI must be written in **Jetpack Compose**. No XML layouts are used in the `app` or `wearos` modules.

### Component Granularity

Screens are composed of small, focused composable functions. When a composable grows large or is reused in more than one place, extract it to `ui/components/`.

**Rule:** One conceptual UI element = one composable file in `components/`.

### Previews

Every screen and every component **must** have at least one `@Preview` composable function.

```kotlin
@Preview(showBackground = true)
@Composable
private fun MyComponentPreview() {
    PomodoroTheme {
        MyComponent(/* sample data */)
    }
}
```

Provide both light and dark previews where visual differences are meaningful:

```kotlin
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MyComponentDarkPreview() { ... }
```

### Light / Dark Mode

Every UI component must look correct in **both light and dark themes**. Use `MaterialTheme.colorScheme` tokens instead of hardcoded hex colors.

```kotlin
// Correct
color = MaterialTheme.colorScheme.primary

// Wrong
color = Color(0xFF6650A4)
```

---

## Accessibility

All components must meet the following requirements:

### Content Descriptions

Every non-textual element (icons, images, custom indicators) must have a `contentDescription`:

```kotlin
Icon(
    imageVector = Icons.Default.Timer,
    contentDescription = "Timer icon"
)
```

For purely decorative elements, use `contentDescription = null`.

### Semantic Roles

Interactive elements must declare their role:

```kotlin
Modifier.semantics {
    role = Role.Button
    contentDescription = "Start timer"
}
```

### Selected State

Items in lists or selectors must communicate their selected state:

```kotlin
Modifier.semantics { selected = isSelected }
```

### Progress Indicators

Custom progress indicators must expose their range:

```kotlin
Modifier.semantics {
    progressBarRangeInfo = ProgressBarRangeInfo(
        current = progress,
        range = 0f..1f
    )
}
```

### Zoom Support

Layouts must not clip or overflow content when the system font scale is increased. Test with `Large` and `Largest` font size settings on a device or emulator.

---

## State Management

### Local State

Use `remember` / `rememberSaveable` for composable-scoped state. Prefer `rememberSaveable` for state that should survive configuration changes.

```kotlin
var selectedDay by rememberSaveable { mutableStateOf<Int?>(null) }
```

### Screen-Level State

As the architecture matures, screen-level state should be lifted into a `ViewModel` exposing `StateFlow` or `State<T>`. The composable should only read state and dispatch events — it should not contain business logic.

### Avoid Side Effects in Composition

Use `LaunchedEffect`, `SideEffect`, or `DisposableEffect` for side effects. Never call imperative APIs (navigation, logging, analytics) directly inside the composable body.

---

## Code Organization

```
ui/
├── screens/       → One file per screen (top-level routes)
├── components/    → Reusable composables (one concern per file)
└── theme/         → Color.kt, Theme.kt, Type.kt only
navigation/        → Routes, items, NavBar composable
```

- Do not place business logic inside composable functions.
- Do not place UI components inside screen files if they are reused elsewhere.
- Keep data models (data classes) in the same file as the component that primarily uses them, or move them to a dedicated `model/` package when shared across multiple components.

---

## Testing

All implemented features require tests. The following test types apply:

### Unit Tests

Location: `src/test/java/`

Use JUnit 4 for pure logic (formatters, calculators, state reducers).

### Instrumented / UI Tests

Location: `src/androidTest/java/`

Use Compose UI Testing (`androidx.compose.ui:ui-test-junit4`) to test composable behavior:

```kotlin
@get:Rule
val composeTestRule = createComposeRule()

@Test
fun timerScreen_showsSessionCount() {
    composeTestRule.setContent {
        PomodoroTheme { TimerScreen() }
    }
    composeTestRule.onNodeWithText("1 of 6 sessions").assertIsDisplayed()
}
```

---

## Core Module

The `core` module is a **work in progress**. When adding shared utilities, data models, or domain logic that must be consumed by both `app` and `wearos`, place them in `core`.

- Do not add `core` code that is only used by one module.
- Keep `core` free of Android UI dependencies — it should remain a pure library.

---

## Commit and Branch Conventions

- Use descriptive commit messages in imperative mood: `Add CircularProgressBar component`.
- Prefix commits by scope when helpful: `[navigation] Add CalendarScreen route`.
- Feature branches should be named `feature/<short-description>`.
- Bug fixes: `fix/<short-description>`.
