# UI Components

All reusable composable components live in `app/src/main/java/mx/dev1/pomodoro/ui/components/`.

Every component has an `@Preview` function for visual development in Android Studio.

---

## Data Models

These data classes are used as parameters by the components below.

### `TaskDayItem`
Represents a single cell in the day strip.

```kotlin
data class TaskDayItem(
    val weekDay: String,   // e.g. "Mon"
    val dayNumber: Int     // e.g. 14
)
```

### `HistoryTaskItem`
Represents one completed task entry in the history list.

```kotlin
data class HistoryTaskItem(
    val id: Int,
    val title: String,
    val category: String,
    val duration: String,
    val timeRange: String,
    val icon: ImageVector,
    val iconContainerColor: Color
)
```

### `HistorySection`
Groups `HistoryTaskItem` entries under a date header.

```kotlin
data class HistorySection(
    val title: String,
    val dateLabel: String,
    val items: List<HistoryTaskItem>
)
```

### `CalendarDayIndicator`
A colored dot rendered inside a calendar day cell.

```kotlin
data class CalendarDayIndicator(
    val day: Int,
    val color: Color
)
```

### `CalendarTagChipData`
A filterable chip on the calendar screen.

```kotlin
data class CalendarTagChipData(
    val text: String,
    val containerColor: Color
)
```

### `TrackerCategoryItem`
A category displayed in the My Tracker grid.

```kotlin
data class TrackerCategoryItem(
    val title: String,
    val icon: ImageVector
)
```

---

## Component Catalogue

---

### TaskCard

**File:** `components/TaskCard.kt`

Displays summary information about a task: its icon, name, session progress, and estimated time.

#### Parameters

| Parameter | Type | Description |
|---|---|---|
| `title` | `String` | Task name |
| `completedSessions` | `Int` | Sessions done so far |
| `totalSessions` | `Int` | Total planned sessions |
| `totalTime` | `String` | Total estimated duration |
| `sessionTime` | `String` | Duration per session |
| `icon` | `ImageVector` | Task icon |

#### Layout

```
┌──────────────────────────────┐
│ [Icon]  Title                │
│         Total time           │
│         X/Y sessions · Zmin  │
└──────────────────────────────┘
```

---

### CircularProgressBar

**File:** `components/CircularProgressBar.kt`

Custom circular progress indicator drawn on a `Canvas`. Used in the timer screen to visualize the current Pomodoro session.

#### Parameters

| Parameter | Type | Description |
|---|---|---|
| `initialValue` | `Int` | Starting progress value |
| `primaryColor` | `Color` | Arc fill color |
| `secondaryColor` | `Color` | Background arc color |
| `circleRadius` | `Float` | Radius of the circle in pixels |
| `sessions` | `Int` | Number of tick marks to draw |
| `onPositionChange` | `(Int) -> Unit` | Callback on value change |

#### Features

- Radial gradient background fill.
- Arc-based progress indicator from top (270°).
- Evenly distributed tick marks around the perimeter.
- Center overlay showing session count and time remaining (`"24:59"` hardcoded).
- Accessibility: exposes progress as a semantic `progressBarRangeInfo`.

---

### TasksDayStrip

**File:** `components/TasksDayStrip.kt`

Horizontally scrollable week-day selector. Displays abbreviated day names and date numbers.

#### Parameters

| Parameter | Type | Description |
|---|---|---|
| `days` | `List<TaskDayItem>` | All days to display (typically 7) |
| `selectedDay` | `TaskDayItem?` | Currently selected day |
| `onDaySelected` | `(TaskDayItem) -> Unit` | Callback when a day is tapped |

#### Dimensions

Each day cell is `66dp × 96dp` with rounded corners. The selected cell uses the primary color background; unselected cells use the surface color.

---

### TasksEmptyState

**File:** `components/TasksEmptyState.kt`

Placeholder shown when no tasks exist for the selected day.

#### Parameters

| Parameter | Type | Description |
|---|---|---|
| `title` | `String` | Main heading text |
| `description` | `String` | Supporting explanation text |

Centered vertically and horizontally. Includes a semantic `contentDescription` for accessibility.

---

### HistoryTaskCard

**File:** `components/HistoryTaskCard.kt`

Swipeable card showing a completed task entry. Dragging left reveals a delete action.

#### Parameters

| Parameter | Type | Description |
|---|---|---|
| `item` | `HistoryTaskItem` | Task data to display |
| `onDelete` | `(HistoryTaskItem) -> Unit` | Called when the delete button is confirmed |

#### Interaction Model

```
Normal state     →  [Drag left ≥50%]  →  Revealed state (delete visible)
Revealed state   →  [Tap delete]       →  onDelete() called
Revealed state   →  [Tap elsewhere]    →  Back to Normal state
Any state        →  [Parent scrolls]   →  Back to Normal state
```

#### Layout

```
┌─────────────────────────────────────────┐
│ [Colored Icon]  Title        Duration   │
│                 Category     HH:MM–HH   │
└─────────────────────────────────────────┘
           ← slide left reveals →
                              [  Delete  ]
```

---

### HistorySectionHeader

**File:** `components/HistorySectionHeader.kt`

A row used as a date-group separator in the history list.

#### Parameters

| Parameter | Type | Description |
|---|---|---|
| `title` | `String` | Section name (e.g. "Today") |
| `dateLabel` | `String` | Formatted date string |

Title uses `HeadlineSmall`, date uses `TitleSmall`. They are placed at opposite ends of the row.

---

### CalendarMonthCard

**File:** `components/CalendarMonthCard.kt`

Full month calendar rendered as a 7-column grid inside a `Card`.

#### Parameters

| Parameter | Type | Description |
|---|---|---|
| `monthName` | `String` | Display name of the month |
| `daysInMonth` | `Int` | Total days (28–31) |
| `startDayOffset` | `Int` | Day of week for the 1st (0 = Mon, 6 = Sun) |
| `indicators` | `List<CalendarDayIndicator>` | Days with colored dots |
| `selectedDay` | `Int?` | Currently selected day number |
| `onDaySelected` | `(Int) -> Unit` | Callback when a day cell is tapped |

#### Features

- Month name + left/right navigation arrows in the header.
- Day-of-week column labels (Mon–Sun).
- Empty cells padded before the first day using `buildMonthGrid()` helper.
- Each cell delegates rendering to `DayCell`.

---

### DayCell

**File:** `components/CalendarMonthCard.kt` (nested)

Individual tappable cell within the calendar grid.

#### States

| State | Appearance |
|---|---|
| Selected | Filled primary color background |
| Has indicator | Small colored dot at bottom |
| Outlined | Border only, no fill |
| Default | No special styling |

Size: `36dp × 36dp`, corner radius `8dp`. Color indicator dot is `6dp`.

---

### CalendarTagChips

**File:** `components/CalendarTagChips.kt`

A `FlowRow` of filterable category chips.

#### Parameters

| Parameter | Type | Description |
|---|---|---|
| `chips` | `List<CalendarTagChipData>` | Chip entries to display |
| `onChipRemoved` | `(CalendarTagChipData) -> Unit` | Called when the "×" on a chip is tapped |

Each chip is a `Surface` with customizable `containerColor` (displayed at 50% alpha) and a small "×" indicator on the right.

---

### MyTrackerProgressCard

**File:** `components/MyTrackerProgressCard.kt`

Circular donut chart showing overall task completion.

#### Parameters

| Parameter | Type | Description |
|---|---|---|
| `percentage` | `Float` | Completion ratio (0.0–1.0) |
| `completedTasks` | `Int` | Number of completed tasks |
| `totalTasks` | `Int` | Total tasks |

The outer ring is `190dp` in diameter with a `72dp` stroke. The center displays the percentage and a "To do list" label with the task count.

---

### MyTrackerCategoryRow

**File:** `components/MyTrackerCategoryRow.kt`

A horizontal row of equal-width category cards.

#### Parameters

| Parameter | Type | Description |
|---|---|---|
| `categories` | `List<TrackerCategoryItem>` | Category entries to display |

Each card contains an icon in a colored container (`8dp` radius) and a title below it. Cards share available width equally with `12dp` gaps.

---

### WeeklyCourseChartCard

**File:** `components/WeeklyCourseChartCard.kt`

A simple custom bar chart showing weekly activity.

#### Layout

- 7 bars representing Mon–Sun.
- One bar is highlighted (Tuesday by default).
- Bars have variable heights (50–84dp range).
- A label badge shows the activity count (e.g. "5 course").
- No external charting library is used; bars are plain `Box` composables.

> Note: `MPAndroidChart` is declared as a dependency but is not currently used by this component.

---

## Accessibility Standards

All components follow these requirements (as specified in `AGENTS.md`):

- Every interactive element has a `contentDescription` or semantic role.
- Progress indicators expose `progressBarRangeInfo`.
- Buttons and tappable areas have `Role.Button` semantics.
- Selected/unselected states are communicated via `selected` semantic properties.
- Components support large font sizes and zoom without clipping.
