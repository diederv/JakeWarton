
# Jake Wharton

This Android project is a tribute to Jake Wharton 

# The challenge

 make an Android application that shows a list and a detail screen
 
 Assignment: Using the https://developer.github.com/v3/old rest api from github create an app which list the repositories of user “JakeWharton” and “infinum”.
 
 When the user taps a repository it should open a detail screen which shows the following information 
 - the repository owner (owner:login, owner:url)
 - selected repo (name, url) 
 - the last event the repository (type, actor: display_login, actor:url)
 
# What we are looking at in the code you submit?
 
 * Overall structure
 * Clarity of the code
 * Libraries used
 * Testability
 * Code correctness
 * UI
 
 # The solution
 
The solution uses the following libraries that are created by or inspired by Jake's work

- Hilt (build on top of Dagger)
- Retrofit (Pure Jake) [using the coroutine option]
- Jetpack View Binding (from butterknive -> to: synthetics -> to: view bindings)
- Kotlin Parcelable implementation

The Building blocks

- It uses the coroutine support for Retrofit
- It uses a repository to hide the Retrofit Service interface and to add a flow zip function to combine two or more retrofit coroutines
- It uses the (currently) popular MVVM architectural pattern and the ViewModel is the perfect place to execute the coroutine, exposed by the repository  
- It uses Hilt support for ViewModels to manage the lifecycle of the ViewModel that outlives configuration changes
- It uses Hilt to implement Dependency Injection (Inversion Of Control)
- It uses Http interception for caching friendly requests
- It uses Http interception for local caching
- It uses Http interception for login (Very useful during development)


# What could be added / refactored

I rushed this first version and I'm eager to:

- Add UI Tests that use the FakeGitHubRepository. Somehow it is still tricky to overwrite the Hilt modules for tests
- Add Unit tests
- Add JavaDoc
- Enhance the UI with a proper design
- Extract or Add more styling elements
- Cache the content using Room, remove the Http Cache layers
- Refactor the package structure 
- Replace the deprecated: *NetworkInfo*, *connectivityManager.activeNetworkInfo* & *activeNetwork.isConnected* in the RetrofitModule