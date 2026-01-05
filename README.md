# News Aggregator (KMP)

Compose Multiplatform news reader that fetches RSS feeds, persists them locally, and ships to Android and iOS from one Kotlin codebase.

## What it does
- Aggregates multiple news categories (tech, world, sports, etc.) from RSS sources defined in `RssFeedUrls`.
- Caches articles locally via SQLDelight so the feed works offline and refreshes gracefully.
- Presents a clean Compose UI with tabs, search, pull-to-refresh, and detail screens.
- Shares domain/presentation logic across platforms while using lightweight platform glue (Android Activity / iOS UIViewController).

## Demo

| Android | iOS |
|---------|-----|
| <video src="assets/demo-android.mp4" width="300"></video> | <video src="assets/demo-ios.mp4" width="300"></video> |

*Click to play the demo videos*

## Tech stack
- **Kotlin Multiplatform + Compose Multiplatform** for shared UI and logic.
- **Ktor** client for networking with JSON serialization.
- **SQLDelight** for type-safe local storage.
- **Koin** for dependency injection.
- **Coil 3** for image loading.
- **Kotlinx Serialization & Datetime** for models and time handling.

## Project layout
- `composeApp/` — shared Kotlin code and platform entry points.
  - `src/commonMain` — shared UI, DI, domain, data layers.
  - `src/androidMain` — Android launcher Activity/Application and Android drivers.
  - `src/iosMain` — iOS UIViewController factory and iOS drivers.
- `iosApp/` — Xcode wrapper that hosts the shared iOS framework.

## Getting started
### Prereqs
- Java 17+ toolchain.
- Android Studio Jellyfish+ with KMP support (for Android).<br>
- Xcode 15+ (for iOS).
- CocoaPods (if you plan to integrate pods).

### Android
```bash
./gradlew :composeApp:assembleDebug
./gradlew :composeApp:installDebug # device/emulator
```

### iOS
```bash
./gradlew :composeApp:syncFramework
open iosApp/iosApp.xcodeproj
# Build & run the iOS target from Xcode
```

## Demo notes
- Default feeds are defined in `composeApp/src/commonMain/kotlin/com/newsaggregator/data/remote/RssFeedUrls.kt`.
- Search filters by title/description locally and refreshes network data.
- The UI is intentionally minimal to showcase cross-platform Compose patterns, state hoisting, and DI.

## Tests
Add platform/unit tests under the relevant `src/*Test` source sets. (No tests are included yet.)

## Contributing
Issues and PRs are welcome. This repo is intended as a demo/reference for KMP + Compose Multiplatform apps.
