# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Github Bookmark - Android app for searching GitHub users and managing bookmarks. Written in Kotlin targeting SDK 23-33.

## Build Commands

```bash
./gradlew assembleDebug      # Build debug APK
./gradlew assembleRelease    # Build release APK
./gradlew installDebug       # Build and install to connected device
./gradlew test               # Run unit tests
./gradlew clean              # Clean build artifacts
```

## Architecture

**Clean Architecture + MVVM** with 7 modules:

```
app                    # Entry point, Hilt DI modules
├── presentation       # Fragments, ViewModels, UI adapters (Android Library)
├── component          # Reusable custom views (Android Library)
├── domain             # Use cases, repository interfaces, domain models (Pure Kotlin)
├── data               # Repository implementations, data source interfaces (Pure Kotlin)
├── remote             # Ktor HTTP client, GitHub API (Android Library)
└── local              # Room database, DAOs (Android Library)
```

**Module dependency flow:** `app → presentation/remote/local → data → domain`

**Data flow:** `Fragment → ViewModel → UseCase → Repository → DataSource`

## Key Technologies

- **DI:** Hilt 2.44.1
- **Async:** Kotlin Coroutines + StateFlow
- **Network:** Ktor Client (CIO engine) with kotlinx.serialization
- **Database:** Room 2.4.3
- **UI:** ViewBinding, Material Design, Glide for images

## Key Entry Points

- `GithubBookmarkApplication` - Hilt application class
- `MainActivity` - Single activity with ViewPager2 tabs
- `GithubUserListFragment` - GitHub user search tab
- `BookmarkUserListFragment` - Saved bookmarks tab

## DI Modules Location

All Hilt modules in `/app/src/main/java/com/parkjin/github_bookmark/di/`:
- `HttpClientModule` - Ktor client config (base URL: api.github.com)
- `DatabaseModule` - Room database
- `RepositoryModule`, `UseCaseModule`, `DataSourceModule`, `DaoModule`, `ApiModule`

## State Management Pattern

ViewModels use sealed classes for Actions/State with MutableStateFlow. UI observes via `repeatOnStarted` lifecycle extension.
