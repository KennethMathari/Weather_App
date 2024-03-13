# Weather App


## Pre Requisites
- [A valid Open Weather API Key](https://openweathermap.org/appid)

## Setup
- Add your Open Weather `API_KEY` to your `local.properties` file
- Build and run the project

## App Features

* Display the current weather at the user's location
* Display the weather forecast for the next 5 days based on the user's location
* Change the background image & colour depending on the type of weather(Cloudy, Sunny and Rainy)
* Search for weather details by city name
* Save/Delete different weather locations as favourites
* View list of favourite weather locations

## Tech-stack
* [Kotlin](https://kotlinlang.org/) - a cross-platform, statically typed, general-purpose programming language with type inference.
* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operations.
* [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html) - handle the stream of data asynchronously that executes sequentially.
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Hilt is a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.
* [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android.

### Architecture
* MVI - Model-View-Intent

### Tests
* [Unit Tests](https://en.wikipedia.org/wiki/Unit_testing) 
* ([JUnit](https://junit.org/junit4/)) - a simple framework to write repeatable tests.
* [MockK](https://github.com/mockk) - mocking library for Kotlin
* [Truth](https://github.com/google/truth) - Truth makes your test assertions and failure messages more readable.

## App Screenshot!
> ![](video.mp4)



