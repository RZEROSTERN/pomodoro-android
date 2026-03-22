# Architecture Overview

## Module Structure

The project uses a **multi-module Gradle setup** with three modules:

```
Pomodoro/
├── app/        → Main mobile application
├── core/       → Shared library (WIP)
├── wearos/     → WearOS smartwatch application
└── gradle/     → Version catalog (libs.versions.toml)
```

---

## Module Details

### `app` — Mobile Application

- **Namespace:** `mx.dev1.pomodoro`
- **Min SDK:** 28 (Android 9)
- **Target / Compile SDK:** 36
- **Java Compatibility:** 17
- **Build Features:** Jetpack Compose enabled

This is the main user-facing module. It owns all screens, navigation, UI components, and the application theme.

### `core` — Shared Library

- **Namespace:** `mx.dev1.pomodoro.core`
- **Type:** Android Library
- **Java Compatibility:** 11
- **Status:** Work in progress — no source files yet

Intended to house utilities, data models, and logic shared between `app` and `wearos`.

### `wearos` — Smartwatch Application

- **Namespace:** `mx.dev1.pomodoro.wearos`
- **Min SDK:** 30 (WearOS minimum)
- **Target / Compile SDK:** 36
- **Status:** Scaffolding complete

See [WearOS Module](./wearos.md) for details.

---

## Architecture Pattern

The project follows a **single-activity, Compose-first** approach with lightweight MVVM patterns currently applied only at the UI state level.

### Single Activity

`MainActivity` is the sole Android `Activity`. It hosts a `NavHost` that renders all screens as composable functions, eliminating the need for multiple activity or fragment transactions.

### Navigation

Screen routing uses string-based routes with Jetpack Navigation Compose. See [Navigation](./navigation.md) for the full routing map.

### State Management (Current)

State is currently managed at the composable level using:

| API | Usage |
|---|---|
| `mutableStateOf` | General observable UI state |
| `mutableIntStateOf` | Integer-specific state (optimized allocation) |
| `rememberSaveable` | State that survives recomposition and configuration changes |

No `ViewModel` or `Repository` layer exists yet. All data shown is hardcoded mock data.

### Planned Architecture (Next Steps)

```
UI Layer (Compose)
    ↕ observes state
ViewModel Layer (StateFlow / LiveData)
    ↕ calls use cases
Domain / Use Case Layer
    ↕ reads/writes data
Repository Layer
    ↕ abstracts sources
Data Layer (Room DB, DataStore, Remote API)
```

---

## Directory Structure — `app` Module

```
app/src/main/java/mx/dev1/pomodoro/
├── MainActivity.kt               → Entry point, scaffold, theme switcher
├── navigation/
│   ├── Routes.kt                 → Route constants
│   ├── MainNavigationItem.kt     → Navigation item data model
│   ├── NavigationItems.kt        → Registered navigation destinations
│   └── MainNavigationBar.kt      → Bottom navigation bar composable
└── ui/
    ├── screens/
    │   ├── TasksScreen.kt
    │   ├── TimerScreen.kt
    │   ├── HistoryScreen.kt
    │   ├── CalendarScreen.kt
    │   ├── MyTrackerScreen.kt
    │   └── MainScreen.kt         → Unused mockup
    ├── components/
    │   ├── TaskCard.kt
    │   ├── CircularProgressBar.kt
    │   ├── TasksDayStrip.kt
    │   ├── TasksEmptyState.kt
    │   ├── HistoryTaskCard.kt
    │   ├── HistorySectionHeader.kt
    │   ├── CalendarMonthCard.kt
    │   ├── DayCell.kt
    │   ├── CalendarTagChips.kt
    │   ├── MyTrackerProgressCard.kt
    │   ├── MyTrackerCategoryRow.kt
    │   └── WeeklyCourseChartCard.kt
    └── theme/
        ├── Color.kt
        ├── Theme.kt
        └── Type.kt
```

---

## Key Technical Decisions

| Decision | Rationale |
|---|---|
| Jetpack Compose (100% UI) | Modern declarative UI, no XML layouts |
| Material Design 3 | Latest Material spec with dynamic color support |
| Single Activity | Simplifies navigation, back stack, and deep linking |
| String-based routes | Simple to read; can be migrated to typed routes (Nav 2.8+) |
| Multi-module | Isolates WearOS and future features; enables faster incremental builds |
| Compose BOM | Ensures all Compose library versions are aligned |
