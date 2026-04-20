# OfflineEngine

> A production-grade, offline-first news aggregator for Android — built to demonstrate real-world Clean Architecture, reactive data pipelines, and scalable engineering practices.

<br>

![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?style=flat-square&logo=android&logoColor=white)
![Language](https://img.shields.io/badge/Language-Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white)
![UI](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white)
![Architecture](https://img.shields.io/badge/Architecture-Clean%20Architecture-FF6F00?style=flat-square)
![Min SDK](https://img.shields.io/badge/Min%20SDK-26-informational?style=flat-square)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)

---

## Table of Contents

- [Overview](#overview)
- [Why This Project Stands Out](#why-this-project-stands-out)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Key Technical Highlights](#key-technical-highlights)
- [Screenshots](#screenshots)
- [Getting Started](#getting-started)
- [Future Improvements](#future-improvements)
- [What I Learned](#what-i-learned)

---

## Overview

NewsFlow is a fully offline-capable news aggregator that fetches, caches, and paginates articles from the [NewsAPI](https://newsapi.org). It is designed around the principle that **the database is always the single source of truth** — the UI never reads directly from the network.

This project was built to demonstrate production-grade Android engineering: not just "it works" but "it works correctly under every condition" — poor connectivity, process death, concurrent operations, and large datasets.

**Real-world use case:** A user in an area with intermittent connectivity opens the app, browses the morning news, bookmarks articles, and reads them later — all without a single network request being required after the initial sync.

---

## Why This Project Stands Out

### Offline-First by Design
The entire data layer is built around Room as the single source of truth. The network is a *supply mechanism* for the database, not a direct data provider for the UI. This means the app remains fully functional with no connectivity — not degraded, not broken — fully functional.

### Real-World Architecture, Not Tutorial Architecture
Most portfolio projects label themselves "Clean Architecture" while mixing concerns across layers. In NewsFlow, the domain layer contains zero Android or third-party imports. A `UseCase` cannot accidentally reference a `Context`. A `Repository` interface cannot reference a `Dao`. These constraints are structural, not voluntary.

### Scalable Pagination Strategy
Pagination is implemented with `RemoteMediator`, which positions Room as the paging source and treats the network as a background loader. This approach scales to millions of rows without UI involvement in page management — the same pattern used in production apps at Google scale.

---

## Features

- **Offline-first reading** — articles are readable with no internet connection after first load
- **Category-filtered feed** — browse news by Technology, Business, Sports, Health, and Science
- **Infinite scroll pagination** — powered by Paging 3 with `RemoteMediator`, Room as the paging source
- **Persistent bookmarks** — saved articles survive cache invalidation and app restarts
- **Real-time search** — debounced search with live results and its own pagination flow
- **Smart cache invalidation** — cache is considered stale after 30 minutes; background refresh is triggered automatically
- **Background sync** — `WorkManager` refreshes the default category feed hourly on WiFi
- **Connectivity awareness** — a live banner indicates offline state; the app degrades gracefully
- **Shimmer loading states** — skeleton placeholders during initial load, no spinners
- **In-app article reader** — full content rendering with Chrome Custom Tabs fallback for paywalled articles
- **Dark / light theme** — Material 3 dynamic theming with full system integration

---

## Tech Stack

| Category | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Architecture | Clean Architecture (data / domain / presentation) |
| Dependency Injection | Hilt |
| Local Database | Room (with migrations) |
| Networking | Retrofit 2, OkHttp 3, Gson |
| Pagination | Paging 3 (`RemoteMediator`) |
| Reactive Streams | Kotlin Flow, StateFlow, SharedFlow |
| Image Loading | Coil |
| Background Work | WorkManager |
| Navigation | Navigation Compose |
| Lifecycle | ViewModel, `collectAsStateWithLifecycle` |
| Build | Gradle Version Catalogs, KSP |

---

## Architecture

### Clean Architecture

NewsFlow enforces a strict three-layer separation. Dependencies point inward — the domain layer has no knowledge of any framework.

```
┌──────────────────────────────────────────────────┐
│                 Presentation Layer                │
│   Jetpack Compose UI + ViewModel + UiState        │
│   Depends on: Domain only                         │
├──────────────────────────────────────────────────┤
│                   Domain Layer                    │
│   UseCases + Repository Interfaces + Models       │
│   Depends on: Nothing (pure Kotlin)               │
├──────────────────────────────────────────────────┤
│                    Data Layer                     │
│   RepositoryImpl + Room DAOs + Retrofit API       │
│   Depends on: Domain (implements its interfaces)  │
└──────────────────────────────────────────────────┘
```

**Enforcement rule:** The `:domain` module has no `android.*`, `retrofit2.*`, or `androidx.room.*` imports. This is verified at build time if extracted to a separate Gradle module (see [Future Improvements](#future-improvements)).

---

### Data Flow

```
Network (Retrofit)
      │
      ▼
 ArticleDto ──► ArticleDtoMapper ──► ArticleEntity
                                           │
                                           ▼
                                    Room Database
                                           │
                                           ▼
                               ArticleEntityMapper
                                           │
                                           ▼
                                   Article (domain)
                                           │
                                           ▼
                              Flow<Resource<List<Article>>>
                                           │
                                           ▼
                                     UseCase
                                           │
                                           ▼
                              StateFlow<HomeUiState>
                                           │
                                           ▼
                              Jetpack Compose UI
```

Three distinct model types prevent layer leakage:
- `ArticleDto` — raw network response shape (data layer only)
- `ArticleEntity` — Room persistence model (data layer only)
- `Article` — domain model consumed by the UI (domain layer, zero framework dependencies)

---

### Why Offline-First

A naive implementation calls the API, maps the response, and emits it to the UI. This approach breaks under: no connectivity, slow connections, process death mid-fetch, and background-to-foreground transitions.

The offline-first approach inverts this: **always read from the database, use the network only to populate it.** The UI subscribes to a Room `Flow` — which emits whenever the database changes. A network refresh is a database write, which automatically propagates to the UI. The UI does not know or care whether a refresh happened.

This means:
- App restart after process death: data is already in Room, UI renders immediately
- Network failure: old data is still shown, staleness is indicated
- Slow network: UI renders stale data instantly, updates when fresh data arrives

---

## Project Structure

```
com.newsflow/
├── data/
│   ├── local/
│   │   ├── db/
│   │   │   └── NewsDatabase.kt          # Room DB, migrations, type converters
│   │   ├── dao/
│   │   │   ├── ArticleDao.kt            # Article queries, paging source
│   │   │   └── RemoteKeyDao.kt          # Tracks pagination state per category
│   │   ├── entity/
│   │   │   ├── ArticleEntity.kt         # Room table definition
│   │   │   └── RemoteKeyEntity.kt       # Stores next/prev page per category
│   │   └── mapper/
│   │       └── ArticleEntityMapper.kt   # Entity ↔ Domain model conversions
│   ├── remote/
│   │   ├── api/
│   │   │   └── NewsApiService.kt        # Retrofit interface
│   │   ├── dto/
│   │   │   ├── ArticleDto.kt
│   │   │   ├── NewsResponseDto.kt
│   │   │   └── SourceDto.kt
│   │   └── mapper/
│   │       └── ArticleDtoMapper.kt      # DTO → Domain model conversions
│   ├── paging/
│   │   └── NewsRemoteMediator.kt        # Paging 3 RemoteMediator implementation
│   └── repository/
│       └── NewsRepositoryImpl.kt        # Implements domain interface, owns DB+API
├── domain/
│   ├── model/
│   │   └── Article.kt                   # Pure Kotlin domain model
│   ├── repository/
│   │   └── NewsRepository.kt            # Interface — no framework imports
│   └── usecase/
│       ├── GetTopHeadlinesUseCase.kt
│       ├── SearchNewsUseCase.kt
│       ├── BookmarkArticleUseCase.kt
│       └── GetBookmarksUseCase.kt
├── presentation/
│   ├── screens/
│   │   ├── home/
│   │   │   ├── HomeScreen.kt
│   │   │   ├── HomeViewModel.kt
│   │   │   └── HomeUiState.kt
│   │   ├── detail/
│   │   │   ├── ArticleDetailScreen.kt
│   │   │   └── ArticleDetailViewModel.kt
│   │   ├── search/
│   │   │   ├── SearchScreen.kt
│   │   │   └── SearchViewModel.kt
│   │   └── bookmarks/
│   │       ├── BookmarksScreen.kt
│   │       └── BookmarksViewModel.kt
│   ├── components/
│   │   ├── ArticleCard.kt
│   │   ├── ArticleCardShimmer.kt
│   │   ├── CategoryChip.kt
│   │   ├── ErrorView.kt
│   │   ├── EmptyStateView.kt
│   │   └── OfflineBanner.kt
│   ├── navigation/
│   │   ├── NavGraph.kt
│   │   └── BottomNavBar.kt
│   └── theme/
│       ├── Color.kt
│       ├── Typography.kt
│       └── Theme.kt
├── di/
│   ├── NetworkModule.kt                 # OkHttp, Retrofit, ApiService
│   ├── DatabaseModule.kt                # Room DB, DAOs
│   └── RepositoryModule.kt             # @Binds NewsRepository → NewsRepositoryImpl
├── worker/
│   └── NewsRefreshWorker.kt             # WorkManager coroutine worker
└── util/
    ├── Resource.kt                      # Sealed class: Loading / Success / Error
    ├── NetworkBoundResource.kt          # Cache-first fetch abstraction
    └── NetworkMonitor.kt                # Connectivity state as Flow<Boolean>
```

---

## Key Technical Highlights

### 1. RemoteMediator — The Core of Pagination

`NewsRemoteMediator` implements Paging 3's `RemoteMediator`, which separates the concern of "when to load the next page" from "what to show." Room is the `PagingSource`; the mediator is responsible only for keeping Room populated.

```kotlin
override suspend fun load(loadType: LoadType, state: PagingState<Int, ArticleEntity>): MediatorResult {
    val page = when (loadType) {
        LoadType.REFRESH -> 1
        LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
        LoadType.APPEND  -> remoteKeyDao.remoteKeyByCategory(category)?.nextPage
                            ?: return MediatorResult.Success(endOfPaginationReached = true)
    }

    return try {
        val response = apiService.getTopHeadlines(category = category, page = page)
        database.withTransaction {
            if (loadType == LoadType.REFRESH) {
                articleDao.deleteNonBookmarkedByCategory(category)
                remoteKeyDao.deleteByCategory(category)
            }
            remoteKeyDao.insertOrReplace(RemoteKeyEntity(category, prevPage = page - 1, nextPage = page + 1))
            articleDao.upsertArticles(response.articles.map { it.toEntity(category) })
        }
        MediatorResult.Success(endOfPaginationReached = response.articles.isEmpty())
    } catch (e: IOException)   { MediatorResult.Error(e) }
      catch (e: HttpException) { MediatorResult.Error(e) }
}
```

Key decisions:
- `PREPEND` immediately returns `endOfPaginationReached = true` — news feeds have no "previous pages"
- All DB writes happen inside `withTransaction` to guarantee atomicity
- Bookmarked articles are excluded from cache deletion — bookmarks survive any cache invalidation

---

### 2. NetworkBoundResource — The Offline-First Pattern

A generic `Flow`-based utility that applies the same cache-first logic across all list endpoints:

```kotlin
inline fun <T> networkBoundResource(
    crossinline query: () -> Flow<T>,
    crossinline fetch: suspend () -> Unit,
    crossinline shouldFetch: suspend () -> Boolean,
    crossinline onFetchFailed: (Throwable) -> Unit = {}
): Flow<Resource<T>> = flow {
    if (shouldFetch()) {
        emit(Resource.Loading)
        try { fetch() } catch (t: Throwable) { onFetchFailed(t) }
    }
    emitAll(query().map { Resource.Success(it) })
}
```

`shouldFetch` is driven by a timestamp comparison — the DB itself stores `cachedAt` per article. If the most recent cached article is older than 30 minutes, a refresh is triggered. The UI always receives data via `emitAll(query())`, whether or not the fetch succeeded. **The database is always the emitter. The network is only ever a writer.**

---

### 3. WorkManager Background Sync

A `CoroutineWorker` schedules hourly background refreshes for the default news category, constrained to connected network state:

```kotlin
WorkManager.getInstance(context).enqueueUniquePeriodicWork(
    "news_background_refresh",
    ExistingPeriodicWorkPolicy.KEEP,
    PeriodicWorkRequestBuilder<NewsRefreshWorker>(1, TimeUnit.HOURS)
        .setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        )
        .build()
)
```

`ExistingPeriodicWorkPolicy.KEEP` ensures that reinstalling or re-launching the app doesn't schedule duplicate workers. The worker is idempotent — running it 10 times has the same database state as running it once, because `@Upsert` is used for all inserts.

---

### 4. Reactive State Management with Flow

ViewModels expose `StateFlow<UiState>` derived from upstream `Flow` sources. Category switching uses `flatMapLatest`, which automatically cancels the previous category's flow before subscribing to the new one — preventing race conditions between rapid category selections:

```kotlin
val uiState: StateFlow<HomeUiState> = _selectedCategory
    .flatMapLatest { category -> getTopHeadlines(category) }
    .map { resource -> resource.toUiState() }
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState.Loading
    )
```

`SharingStarted.WhileSubscribed(5_000)` keeps the flow active for 5 seconds after the UI leaves — surviving configuration changes and brief backgrounding without restarting the upstream flow.

Search uses `debounce(400)` + `distinctUntilChanged()` before hitting the repository, eliminating redundant network calls on rapid keystroke input.

---

### 5. Bookmark Preservation During Cache Invalidation

A subtle but critical correctness requirement: when a category cache is cleared, bookmarked articles must not be deleted. Two mechanisms work together:

1. `deleteNonBookmarkedByCategory` SQL query filters on `isBookmarked = 0` — bookmarked articles are never deleted from the cache
2. Before upserting fresh API results, existing bookmarked IDs are fetched and restored onto the incoming entities — preventing a fresh API write from overwriting a bookmark flag

```kotlin
val bookmarkedIds = articleDao.getBookmarkedIds(category).toSet()
val entities = articles.map { dto ->
    dto.toEntity(category).copy(isBookmarked = dto.urlHash in bookmarkedIds)
}
```

---

## Screenshots

| Home Feed | Article Detail | Search | Bookmarks |
|---|---|---|---|
| ![Home](.github/screenshots/home.png) | ![Detail](.github/screenshots/detail.png) | ![Search](.github/screenshots/search.png) | ![Bookmarks](.github/screenshots/bookmarks.png) |

> *Screenshots will be added after first stable release.*

---

## Getting Started

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- JDK 17
- A free API key from [newsapi.org](https://newsapi.org)

### Setup

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/newsflow.git
cd newsflow
```

2. **Add your API key**

Create or open `local.properties` in the project root (this file is gitignored):
```properties
NEWS_API_KEY=your_api_key_here
BASE_URL=https://newsapi.org/v1/
```

3. **Build and run**
```bash
./gradlew assembleDebug
```

Or open in Android Studio and press **Run**.

### Running Tests

```bash
# Unit tests (domain + data layer)
./gradlew test

# Instrumented tests (Room, RemoteMediator integration)
./gradlew connectedAndroidTest

# Full test suite with coverage report
./gradlew testDebugUnitTestCoverage
```

---

## Future Improvements

These are deliberate exclusions — not oversights — kept out of scope to maintain focus during development. All are technically straightforward given the current architecture:

- **Multi-module Gradle structure** — extract `:core:domain`, `:core:data`, `:feature:home` into separate Gradle modules to enforce layer boundaries at compile time, not just by convention
- **Notification push for breaking news** — WorkManager worker already in place; extend it to fire a local notification when a high-priority article is detected
- **Full-text search via Room FTS4** — add an FTS virtual table with a database trigger to keep it in sync with the articles table; enables fast local search without network
- **Article reading progress** — persist scroll position in Room; resume reading from the same paragraph on reopen
- **Encrypted database** — integrate SQLCipher via the Room integration for locally sensitive bookmark data
- **CI/CD pipeline** — GitHub Actions workflow for automated lint, test, and APK artifact generation on pull requests

---

## What I Learned

**Offline-first is an architectural commitment, not a feature.** You cannot add it later. Every decision downstream — how models are structured, how mappers work, what the repository contract looks like — is shaped by the constraint that the database is always the emitter. I built this constraint in from day one, which is why bookmark preservation during cache clears, stale data fallback, and reconnection recovery all work correctly. They are consequences of the architecture, not special-cased solutions.

**The RemoteMediator boundary is precise.** Its only job is to populate Room when Paging 3 determines more data is needed. It has no opinion on what to display. This separation meant I could change pagination page sizes, network error retry logic, and cache clearing strategies without touching a single Compose file.

**`StateFlow` + `SharingStarted.WhileSubscribed` is the correct production pattern.** It took real debugging — watching flows restart unnecessarily on configuration changes — to understand why `WhileSubscribed(5_000)` exists. It keeps flows alive long enough to survive a rotation without restarting upstream work.

**Three model types is not over-engineering.** `ArticleDto` → `ArticleEntity` → `Article` felt like ceremony until I had to change the API response shape. The change touched exactly one mapper file and zero ViewModel, UseCase, or UI code. The domain model is a contract. Breaking changes to that contract are explicit and intentional.

**Correctness is harder than functionality.** The app "works" after Phase 2. Getting it to work *correctly* — under airplane mode, during rapid category switching, after background process death, during concurrent bookmark writes and cache invalidation — is what the remaining phases were about. Production quality is the gap between "it works on my machine" and "it works for all users, always."

---

## License

```
MIT License

Copyright (c) 2026 [Adham Mohamed]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including, without limitation, the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software.
```

---

<p align="center">Built with precision. Designed for production.</p>
