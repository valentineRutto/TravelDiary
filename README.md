# TravelDiary
Travel Diary app that allows users to document their travel experiences, including photos, notes, and location information. The app should provide a platform for users to create and browse their travel memories.

## Features
### User Authentication:
Implement user registration and login using Firebase Authentication.
### Diary Creation:
Allow users to create travel diary entries.
Include fields for title, date, location, notes, and photos.
### Diary List:
Display a list of diary entries with their titles and dates.
Use RecyclerView to efficiently display the list.
### Diary Details:
Show the full details of a selected diary entry, including notes, photos, and location.
### Photo Handling:
Implement image selection from the gallery or camera.
Display selected images within the diary entry.
### Location Integration:
Integrate Google Maps API or other location services to allow users to tag their entries with a location.
### Search and Filters:
Add a search bar to filter diary entries by title or location.
Implement sorting or filtering options.
### Offline Support:
Implement offline mode with locally cached data for better user experience.

## Tech Stack

-[Kotlin](https://developer.android.com/kotlin?gclid=CjwKCAjw9r-DBhBxEiwA9qYUpWK_ANJvWx6zBkFk-4XeP5a0dCxwyFZv_EeeqAcUx1K_Mj3gGkpdxRoCW9IQAvD_BwE&gclsrc=aw.ds)- a cross-platform, statically typed, general-purpose programming language with type inference.<br/>
-Coroutines - perform background operations.<br/>
-[KOIN](https://insert-koin.io/) - a pragmatic lightweight dependency injection framework.<br/>
-[ROOM](https://developer.android.com/training/data-storage/room) - persistence library providing an abstraction over SQL .<br/>
[Jetpack](https://developer.android.com/jetpack) -<br/>

## Project Architecture
The app follows MVVM architecture and it contains these main packages: 

#### Data

- ```data-local```

Handles persistence of object with Room ORM from.This module is responsible for handling all local related
logic and serves up data to and from the presentation layer through domain objects.

With this separation we can easily swap in new or replace the database being used without causeing
major ripples across the codebase.

#### Repository
Gets data from api and room and distributes it to the rest of the app
#### DI
Koin handles dependency injection on components making it easier to reuse
#### util
This package contains utility functions like networkresult which are used throughtout the application 
#### ui
contains views that are shown to the user

## Tech Stack
-Git clone and run on emulator from android studio
