<h1 align="center">Recipes</h1>

<p align="center">
Recipes app makes on modern Android development with Hilt, Coroutines, Flow, Jetpack (Room, ViewModel, Datastore, etc.) based on MVVM architecture.
</p>

<br/>
<p align="center">TheMealDB Api: <a href="https://www.themealdb.com/api.php">Click here</a>
</p>

### App Preview

|                                                       Home screen                                                       |                                                     Details screen                                                     |                                                     Saved screen                                                     |
|:-----------------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------:|
|   <img src="https://github.com/MikeDubovikov/Recipes/blob/master/previews/HomeScreen.png" alt="drawing" width="250"/>   | <img src="https://github.com/MikeDubovikov/Recipes/blob/master/previews/DetailsScreen.png" alt="drawing" width="250"/> | <img src="https://github.com/MikeDubovikov/Recipes/blob/master/previews/SavedScreen.png" alt="drawing" width="250"/> |
|                                                     Settings screen                                                     |                                                        App Gif                                                         |                                                                                                                      |
| <img src="https://github.com/MikeDubovikov/Recipes/blob/master/previews/SettingsScreen.png" alt="drawing" width="250"/> |          <img src="https://github.com/MikeDubovikov/Recipes/blob/master/previews/Preview.gif"  width="250"/>           |                                                                                                                      |

## Tech stack

- [Kotlin](https://kotlinlang.org/)
  based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)
  for asynchronous.
- Jetpack
    - [Lifecycle](https://developer.android.com/guide/components/activities/activity-lifecycle):
      Observe Android lifecycles and handle UI states upon the lifecycle changes.
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel): Manages
      UI-related data holder and lifecycle aware. Allows data to survive configuration changes such
      as screen rotations.
    - [DataBinding](https://developer.android.com/topic/libraries/data-binding): Binds UI components
      in your layouts to data sources in your app using a declarative format rather than
      programmatically.
    - [Room](https://developer.android.com/training/data-storage/room): Constructs Database by
      providing an abstraction layer over SQLite to allow fluent database access.
    - [Datastore](https://developer.android.com/topic/libraries/architecture/datastore): Saving
      application settings.
    - [Navigation](https://developer.android.com/guide/navigation): Navigation that provides
      transitions between screens.
    - [Hilt](https://dagger.dev/hilt/): Dependency injection.
- Architecture
    - MVVM Architecture (View - DataBinding - ViewModel - Model)
    - Repository Pattern
    - Clean architecture
- [Retrofit](https://github.com/square/retrofit): Construct the REST APIs and paging network data.
- [Glide](https://github.com/bumptech/glide): Loading images from network.