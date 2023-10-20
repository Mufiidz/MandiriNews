<div align="center">
    <h1>Mandiri News</h1>
    <p>An Android app for showing news</p>
</div>

---

<p align="center">
  <img src="screenshots/home.png" width="30%" />
  <img src="screenshots/search.png" width="30%" />
  <img src="screenshots/search_result.png" width="30%" />
</p>

## Features
- Headline News
- All News
- Search News
- Read News

## Tech Stack & Library
- [Gradle Version Catalog](gradle/libs.versions.toml)
- [Hilt](https://dagger.dev/hilt/) for Dependency Injection
- [Landscapist](https://github.com/skydoves/landscapist) for Image Loader
- [Ktorfit](https://github.com/Foso/Ktorfit) for HTTP Client
- [Room](https://developer.android.com/training/data-storage/room?hl=id) for Local DB
- [Compose Destinations](https://github.com/raamcosta/compose-destinations) for Compose Navigation
- [KSP](https://github.com/google/ksp) for Kotlin Symbol Processing API
- [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview?hl=id) for Android Pagination
- [Timber](https://github.com/JakeWharton/timber) for A logger
- Used AndroidX, Jetpack Compose, Material Design Components 3, ViewModel, and any more libraries

## Data Source
Mandiri News using the [NewsAPI](https://newsapi.org/) for constructing RESTful API.<br>
NewsAPI provides a RESTful API interface to highly detailed objects built from thousands of lines of data related to news.