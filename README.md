# Marvel Pocket
### Android app challenge.

This app allows you to known and discover every Marvel comics/films universe character.

## Getting started
You must setup api keys in apps build gradle file before executing:
```kotlin
    buildConfigField("String", "API_BASE_URL", "\"https://gateway.marvel.com\"")
    buildConfigField("String", "API_PUB_KEY", "\"{YOUR_KEY}\"")
    buildConfigField("String", "API_PRIV_KEY", "\"{YOUR_KEY}\"")
```

Run:
```shell
./gradlew assembleDebug

##Install apk example_app
./gradlew installDebug

#Launch entry activity on example app
adb shell am start -n "com.santiihoyos.marvelpocket/com.santiihoyos.marvelpocket.ui.feature.home.HomeActivity" \
                   -a android.intent.action.MAIN \
                   -c android.intent.category.LAUNCHER
```

## Features
- Android Api > 21
- Google apps architecture (latest)
- 100% composable
- Versions catalog
- KTS for Gradle
- Material desing 3 initial support
- Koin as Dependency injector
- Coil as image manager
- Paging (android)
- etc...

## Architecture notes

There are 3 gradle modules 2 android (:app, :characters) and 1 kotlin standalone (:api) 

In app module is main app logic like entry points and HomeScreen it depends on `:characters` feature module (witch contains
all related code with Character feature) and `:api` (witch contains all related code to communicate with server by using Rest endpoints).

## layers

By using [Google's architecture definition](https://developer.android.com/topic/architecture) there are:

### UI
Only on :app and :characters module there are "Composables" each one has it ViewModel. (see ui packages).
They implement UDF(Unidirectional data flow) patter states as source of truth from view models and Event system
to notify user inputs from UI to ViewModels.

### Domain
Contains app internal business logic entities and UseCases to reuse login on different ViewModels.
Functions on UseCases are main thread safe. They speak with repositories and provides specific
business app logic error management.

### Data
All data business logic is in :api module, that module contains DataSources interfaces and implementations
to connect with server Rest endpoints, those data sources are used by Repository witch exposes to domain layer
all data functionality and manages data layer errors.

## Testing
Character list flow is tested from ViewModel to DataSource you can see unit test packages in every module.
Tests run by using mockito and coroutines test library.

# Screenshots
<img src="docs/img/example_list.png" width="300"> -> <img src="docs/img/example_detail.png" width="300">
