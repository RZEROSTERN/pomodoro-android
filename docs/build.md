# Build & Dependencies

The project uses **Gradle with Kotlin DSL** (`*.gradle.kts`) and a centralized **version catalog** (`gradle/libs.versions.toml`) to manage all dependencies and plugin versions.

---

## Version Catalog

**File:** `gradle/libs.versions.toml`

The version catalog is the single source of truth for all dependency versions. Avoid hardcoding versions directly in `build.gradle.kts` files.

### Key Versions

| Alias | Version | Notes |
|---|---|---|
| `agp` | `8.9.3` | Android Gradle Plugin |
| `kotlin` | `2.0.0` | Kotlin compiler and stdlib |
| `composeBom` | `2025.01.00` | Compose Bill of Materials |
| `coreKtx` | `1.15.0` | AndroidX Core KTX |
| `lifecycleRuntimeKtx` | `2.8.7` | Lifecycle runtime |
| `activityCompose` | `1.10.0` | Activity Compose integration |
| `navigationCompose` | `2.8.5` | Navigation Compose |
| `appcompat` | `1.7.1` | AppCompat |
| `wearComposeMaterial` | `1.2.1` | Wear Compose Material |
| `coreSplashscreen` | `1.0.1` | SplashScreen API |
| `playServicesWearable` | `19.0.0` | Play Services Wearable |

---

## Gradle Plugins

| Alias | Plugin ID | Applied In |
|---|---|---|
| `android-application` | `com.android.application` | `app/`, `wearos/` |
| `android-library` | `com.android.library` | `core/` |
| `jetbrains-kotlin-android` | `org.jetbrains.kotlin.android` | All modules |
| `kotlin-compose` | `org.jetbrains.kotlin.plugin.compose` | `app/`, `wearos/` |

---

## Module Build Configurations

### `app/build.gradle.kts`

```
compileSdk = 36
minSdk     = 28
targetSdk  = 36
jvmTarget  = "17"
```

**Features enabled:**
- `buildFeatures { compose = true }`

### `core/build.gradle.kts`

```
compileSdk = 36
minSdk     = 28
jvmTarget  = "11"
```

Library module — no `targetSdk`.

### `wearos/build.gradle.kts`

```
compileSdk = 36
minSdk     = 30
targetSdk  = 36
jvmTarget  = "11"
```

**Features enabled:**
- `buildFeatures { compose = true }`

---

## Dependencies by Module

### `app` Module

#### Core Android

| Dependency | Purpose |
|---|---|
| `androidx.core:core-ktx` | Kotlin extensions for Android APIs |
| `androidx.lifecycle:lifecycle-runtime-ktx` | Lifecycle-aware coroutines |
| `androidx.activity:activity-compose` | `setContent {}` support in Activity |
| `androidx.appcompat:appcompat` | Backwards compatibility |
| `com.google.android.material:material` | Material components (View-based, for legacy) |

#### Jetpack Compose

| Dependency | Purpose |
|---|---|
| `androidx.compose:compose-bom` | Aligns all Compose library versions |
| `androidx.compose.ui:ui` | Core Compose UI |
| `androidx.compose.ui:ui-graphics` | Drawing and graphics primitives |
| `androidx.compose.ui:ui-tooling-preview` | `@Preview` support |
| `androidx.compose.material3:material3` | Material Design 3 components |
| `androidx.navigation:navigation-compose` | NavHost and NavController |

#### Third-Party

| Dependency | Purpose |
|---|---|
| `com.github.PhilJay:MPAndroidChart:v3.1.0` | Chart rendering (declared, not actively used yet) |

#### Testing

| Dependency | Scope | Purpose |
|---|---|---|
| `junit:junit:4.13.2` | testImplementation | Unit tests |
| `androidx.test.ext:junit:1.2.1` | androidTestImplementation | Android instrumented tests |
| `androidx.test.espresso:espresso-core:3.6.1` | androidTestImplementation | UI automation |
| `androidx.compose.ui:ui-test-junit4` | androidTestImplementation | Compose UI tests |
| `androidx.compose.ui:ui-tooling` | debugImplementation | Compose layout inspector |
| `androidx.compose.ui:ui-test-manifest` | debugImplementation | Test manifest |

### `core` Module

No additional dependencies beyond the Android framework.

### `wearos` Module

| Dependency | Purpose |
|---|---|
| `com.google.android.gms:play-services-wearable` | Phone↔Watch data sync |
| `androidx.percentlayout:percentlayout` | Percent-based layouts |
| `androidx.legacy:legacy-support-v4` | Legacy support |
| `androidx.recyclerview:recyclerview` | RecyclerView (unused in Compose context) |
| `androidx.wear.compose:compose-material` | Wear-specific Material components |
| `androidx.wear.compose:compose-foundation` | Wear layout primitives |
| `androidx.wear:compose-tooling` | Wear `@Preview` support |
| `androidx.core:core-splashscreen` | SplashScreen API |

---

## Repositories

Configured in `settings.gradle.kts`:

```kotlin
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }  // for MPAndroidChart
    }
}
```

---

## Gradle Properties

**File:** `gradle.properties`

```properties
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
android.useAndroidX=true
kotlin.code.style=official
android.nonTransitiveRClass=true
```

| Property | Effect |
|---|---|
| `org.gradle.jvmargs=-Xmx2048m` | Allocates 2GB heap to the Gradle daemon |
| `android.useAndroidX=true` | Enforces AndroidX over legacy support libraries |
| `kotlin.code.style=official` | Uses Kotlin official code style conventions |
| `android.nonTransitiveRClass=true` | Each module only sees its own R class (faster builds, avoids ambiguity) |

---

## Adding a New Dependency

1. Add the version to `gradle/libs.versions.toml` under `[versions]`:
   ```toml
   myLibrary = "1.0.0"
   ```

2. Add the library reference under `[libraries]`:
   ```toml
   my-library = { group = "com.example", name = "my-library", version.ref = "myLibrary" }
   ```

3. Add the dependency to the target module's `build.gradle.kts`:
   ```kotlin
   implementation(libs.my.library)
   ```

4. Sync Gradle.
