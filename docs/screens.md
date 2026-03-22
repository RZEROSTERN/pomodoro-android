# Screens

All screens are Jetpack Compose composable functions located in `app/src/main/java/mx/dev1/pomodoro/ui/screens/`.

Each screen is a top-level route in the `NavHost`. All screens use mock/hardcoded data in the current implementation.

---

## TasksScreen

**File:** `ui/screens/TasksScreen.kt`
**Route:** `tasks`
**Purpose:** Home screen showing the list of tasks for a selected day.

### Layout

1. **TasksDayStrip** — Horizontal week selector (Sun–Sat). Tapping a day updates the selected date.
2. **Task list** — Filtered by the selected day.
3. **TasksEmptyState** — Shown when no tasks exist for the selected day.

### State

| State | Type | Description |
|---|---|---|
| `selectedDate` | `LocalDate` | Currently selected day; defaults to `LocalDate.now()` |

### Notes

- Uses a 220.dp top spacing to account for the app bar and day strip.
- The actual task data is currently mocked (no data layer yet).

---

## TimerScreen

**File:** `ui/screens/TimerScreen.kt`
**Route:** `timer`
**Purpose:** Active Pomodoro timer session interface.

### Layout

1. **TaskCard** — Shows the active task being tracked.
2. **CircularProgressBar** — Displays elapsed/remaining time and session count.
3. **Session counter** — e.g. "1 of 6 sessions".
4. **Play button** — Starts or resumes the timer.
5. **Break notification** — "Take a break!" indicator.

### Mock Data (Hardcoded)

- Task: "Mobile app design"
- Sessions completed: 4 out of 6
- Timer displayed: 24:59

### Notes

- Timer logic is not yet implemented. The screen is purely a UI scaffold.
- The bottom navigation bar and FAB are hidden when this screen is active.

---

## HistoryScreen

**File:** `ui/screens/HistoryScreen.kt`
**Route:** `favorites`
**Purpose:** Scrollable list of completed task sessions, grouped by date.

### Layout

1. **Date section headers** (`HistorySectionHeader`) — Group items by date label (e.g., "Today", "Yesterday").
2. **HistoryTaskCards** — Swipeable cards showing task details. Dragging left reveals a delete action.

### Interaction

| Gesture | Behavior |
|---|---|
| Drag left on card | Reveals red delete button |
| Tap delete button | Removes item from list |
| Tap anywhere (collapsed) | No action |
| Tap anywhere (expanded) | Collapses the card |
| Scroll | Collapses any open card |

### Mock Data

Two sections: **Today** and **Yesterday**, each with several `HistoryTaskItem` entries containing title, category, duration, time range, icon, and icon background color.

---

## CalendarScreen

**File:** `ui/screens/CalendarScreen.kt`
**Route:** `calendar`
**Purpose:** Monthly calendar view with analytics and category filtering.

### Layout

1. **CalendarMonthCard** — Displays a full month grid with:
   - Month navigation arrows.
   - Day of week headers (Mon–Sun).
   - Tappable day cells with selection state and color indicators.
2. **CalendarTagChips** — Horizontal flow of filterable category chips.
3. **WeeklyCourseChartCard** — Bar chart showing weekly course activity.

### State

| State | Type | Description |
|---|---|---|
| `selectedDay` | `Int?` | Currently selected calendar day |

### Mock Data

- Month: August, 31 days, starts on day offset 2 (Wednesday).
- Color indicators highlight specific days.
- Tags: Course, Yoga, Meeting, Running, Swimming.

---

## MyTrackerScreen

**File:** `ui/screens/MyTrackerScreen.kt`
**Route:** `my_tracker`
**Purpose:** Progress tracking and statistics dashboard.

### Layout

1. **MyTrackerProgressCard** — Circular donut chart with overall completion percentage.
2. **MyTrackerCategoryRow** — Grid of category cards (Productive, Meditation, Education).
3. **VIEW DETAILS button** — Placeholder for a detail navigation action.

### Mock Data

- Overall progress: 72%, 78 tasks.
- Categories: Productive, Meditation, Education (each with an icon).

---

## MainScreen *(Unused)*

**File:** `ui/screens/MainScreen.kt`
**Purpose:** Early-stage mockup showing a basic "25:00" timer with Start and Reset buttons.

> This screen is **not registered** in the `NavHost` and is not accessible in the current app. It predates `TimerScreen` and can be removed or repurposed.

---

## Screen Comparison Table

| Screen | Route | Has Mock Data | State | Gestures |
|---|---|---|---|---|
| TasksScreen | `tasks` | Yes | Selected date | Tap day |
| TimerScreen | `timer` | Yes | None | Tap play |
| HistoryScreen | `favorites` | Yes | Expanded card | Swipe, tap, scroll |
| CalendarScreen | `calendar` | Yes | Selected day | Tap day, chip |
| MyTrackerScreen | `my_tracker` | Yes | None | Tap button |
