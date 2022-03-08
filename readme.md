# Github user finder (Android-Kotlin)

## Getting Started
This project uses the Gradle build system. To build this project, use the `gradlew build` command or use "Import Project" in Android Studio.

## Screenshots:
<img src="docs/home_dark_theme.png" style="width: 20%"/>
<img src="docs/user_search.png" style="width: 20%"/>
<img src="docs/user_profile.png" style="width: 20%"/>

## Features
- Support Dark mode
- Searching users with job cancellation and debouncing
- See user details by using live data
- By using deep-link you can see user profile like this **https://github.com/husseinhj** in Android browser

## API
This sample project uses the Github API to find users and to get user details based on their usernames. For more information, please go to [Github API docs](https://docs.github.com/en/rest).

## Dependencies
For reviewing which dependencies are used and the reason explained [here](docs/dependencies.md).

## Architecture

- Single Activity
- MVVM Pattern

**View:** Renders UI and delegates user actions to ViewModel

**ViewModel:** Can have simple UI logic but most of the time just gets the data from Repository

**Repository:** Single source of data. Responsible to get data from one or more data sources

## Future works

- Using Dependency Injection (Koin, Dagger)
- Setup Github action as CI/CD
- Add more test cases