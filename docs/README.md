# Pomodoro App — Developer Documentation

Welcome to the developer documentation for the **Pomodoro** Android application.

This project is a multi-module Android app implementing the [Pomodoro Technique](https://en.wikipedia.org/wiki/Pomodoro_Technique) — a time management methodology that breaks work into focused intervals separated by short breaks.

---

## Documentation Index

| Document | Description |
|---|---|
| [Architecture Overview](./architecture.md) | Project structure, modules, and design patterns |
| [Navigation](./navigation.md) | Screen routing and navigation system |
| [Screens](./screens.md) | All screens and their responsibilities |
| [UI Components](./components.md) | Reusable Compose components catalogue |
| [Theme & Styling](./theme.md) | Colors, typography, and theming system |
| [WearOS Module](./wearos.md) | Smartwatch application documentation |
| [Build & Dependencies](./build.md) | Gradle setup, version catalog, and dependencies |
| [Development Guidelines](./guidelines.md) | Coding standards and contribution rules |

---

## Quick Start

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Android SDK 36
- A device or emulator running Android 9 (API 28) or higher

### Running the App

1. Clone the repository.
2. Open the project root in Android Studio.
3. Sync Gradle (`File > Sync Project with Gradle Files`).
4. Select the `app` run configuration and run on a device or emulator.

For the WearOS module, select the `wearos` run configuration and deploy to a Wear OS emulator or device.

---

## Project Status

The project is in active **UI/UX development**. The current implementation focuses on composable screens and components with mock/hardcoded data. The data layer, ViewModels, and business logic are planned for upcoming iterations.

See [Architecture Overview](./architecture.md) for current status details.
