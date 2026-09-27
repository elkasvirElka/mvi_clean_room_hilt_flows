# MVI + Clean Architecture + Room + Hilt + Flows (movie browser)

The most complete of three takes on the same movie-browsing domain — this one adds an offline-first cache and pagination on top of the Clean/MVI shape from [`mvi_plus_compose_plus_clean`](https://github.com/elkasvirElka/mvi_plus_compose_plus_clean).

## Architecture

- **domain**: `MovieRepository` interface and a `GetMovieInfo` use case wrapping it, returning `Flow<Resource<List<MovieInfo>>>` (`Resource` = `Loading` / `Success` / `Error`)
- **data**: `MovieRepositoryImpl` always reads from the Room database and treats it as the single source of truth — a network call refreshes the database, and the UI observes the database, never the network response directly
- **Paging 3 + `RemoteMediator`**: `MovieRemoteMediator` merges network pages into Room as the user scrolls, so paginated results are cached and Room stays the one place the UI reads from
- **Hilt DI** (`AppModule`): provides the Retrofit service, Room database, `Pager`, and repository as singletons
- **presentation**: `MovieListViewModel` (`@HiltViewModel`) exposes a `StateFlow<MovieListState>` driven by a sealed `MovieListEvent`, plus a separate cached `Pager` flow for the paginated list
- **`CryptoManager`**: wraps Android Keystore–backed AES/CBC encryption for data that needs to be encrypted at rest, independent of the movie-browsing feature itself

## Tests

`MovieDatabaseTest` and `CryptoManagerTest` cover the Room DAO and the encryption round-trip respectively.

## See also

- [`mvi-pattern`](https://github.com/elkasvirElka/mvi-pattern) — the initial MVI sketch
- [`mvi_plus_compose_plus_clean`](https://github.com/elkasvirElka/mvi_plus_compose_plus_clean) — the leaner sibling without Room or Paging

## Stack

Kotlin, Jetpack Compose, Coroutines/Flow, Room, Paging 3, Retrofit, Hilt, Android Keystore
