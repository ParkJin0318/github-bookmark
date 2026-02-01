# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Github Bookmark - Android app for searching GitHub users and managing bookmarks. Written in Kotlin 2.0.21 targeting SDK 24-35.

## Build Commands

```bash
./gradlew assembleDebug      # Build debug APK
./gradlew assembleRelease    # Build release APK
./gradlew installDebug       # Build and install to connected device
./gradlew test               # Run unit tests
./gradlew ktlintCheck        # Check code style
./gradlew ktlintFormat       # Auto-fix code style
./gradlew clean              # Clean build artifacts
```

## Architecture

**Clean Architecture + MVVM** with 7 modules:

```
app                    # Entry point, Hilt DI modules
├── presentation       # Compose Screens, ViewModels (Android Library)
├── component          # Reusable Compose components (Android Library)
├── domain             # Use cases, repository interfaces, domain models (Pure Kotlin)
├── data               # Repository implementations, data source interfaces (Pure Kotlin)
├── remote             # Ktor HTTP client, GitHub API (Android Library)
└── local              # Room database, DAOs (Android Library)
```

**Module dependency flow:** `app → presentation/remote/local → data → domain`

**Data flow:** `Composable Screen → ViewModel → UseCase → Repository → DataSource`

## Key Technologies

- **Build:** Gradle 8.10.2, AGP 8.7.3, Kotlin 2.0.21, Java 17
- **DI:** Hilt 2.54 (with KSP)
- **Async:** Kotlin Coroutines 1.9.0 + StateFlow
- **Network:** Ktor Client 3.0.3 (CIO engine) with kotlinx.serialization
- **Database:** Room 2.6.1
- **UI:** Jetpack Compose (BOM 2024.12.01), Material3, Coil 2.7.0
- **Code Style:** ktlint 1.5.0

## Key Entry Points

- `GithubBookmarkApplication` - Hilt application class
- `MainActivity` - ComponentActivity with Compose setContent
- `MainScreen` - Main Composable with HorizontalPager tabs
- `GithubUserListScreen` - GitHub user search tab
- `BookmarkUserListScreen` - Saved bookmarks tab

## DI Modules Location

All Hilt modules in `/app/src/main/java/com/parkjin/github_bookmark/di/`:
- `HttpClientModule` - Ktor client config (base URL: api.github.com)
- `DatabaseModule` - Room database
- `RepositoryModule`, `UseCaseModule`, `DataSourceModule`, `DaoModule`, `ApiModule`

## State Management Pattern

ViewModels use sealed classes for Actions/State with MutableStateFlow. UI observes via `collectAsStateWithLifecycle()`.

## Version Catalog

Dependencies are managed centrally in `gradle/libs.versions.toml`.
