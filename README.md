# Albums
Display all the albums

# 1. Core
This can be accessed by all modules. Includes utils and abstractions.

# 2. app
With main application and injections and access to all modules. 

# 3. albums
Provides linear view of album titles. Tests including viewModel tests and database tests

## Gradle

# 1. config-android.gradle
    - Contains android configuration. Enabled viewbinding and buildvariants. Flavors needs to be added here and this will be accessed by all libraries build.gradles

# 2. config-properties.gradle
    - Contains properties that needs to be secured. BASE_URL in this code.

# 3. dependencies-all.gradle
    - Dependencies.


# Libraries
1. Koin(DI)
2. Android Architecture (AndroidX,ViewModel,navigation, Lifecycle, LiveData, Coroutines)
4. Glide 4.12.0
5. GlideVectorYou
6. Retrofit2, OkHttp, Gson
7. Mockito
8. Junit
9. Ktlint

# Improvements
1. Data base can extended functions (CURD)
2. Albums can be removed or added and updated with UI interfaces
3. Albums can be organised in different approaches such as text length