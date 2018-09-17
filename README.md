# Ricklantis - MVVM
This version of the app is called mvvm, and it uses some Architecture Components like ViewModel, 
LiveData, and Room.

## Design Pattern
[MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) - Model–view–viewmodel

## Libraries Used
* Architecture components
  * [ViewModel][0] - The ViewModel class is designed to hold and manage UI-related data in a 
  life-cycle conscious way.
  * [LiveData][1] - An observable data holder class.

* Database
  * [Room][2] - A SQL object mapping library.

* View bindings
  * [Butterknife][3] - Field and method binding for Android views which uses annotation processing 
  to generate boilerplate code.
  
* Network
  * [Retrofit][4] - Type-safe HTTP client for Android and Java by Square, Inc.
  * [Gson][5] - A Java library that can be used to convert Java Objects into their JSON 
  representation.
  * [OkHttp][6] - An HTTP & HTTP/2 client for Android and Java applications.

* Image loader
  * [Picasso][7] - A powerful image downloading and caching library for Android.

## Look and feel
[Adaptive icons][8] - An application icon format to make all icons on a device more coherent by 
unifying the shape of all app icons and opening the door to interesting visual effects.

[ConstraintLayout][9] - ConstraintLayout allows you to create large and complex layouts with a flat 
view hierarchy (no nested view groups)


[0]: https://developer.android.com/topic/libraries/architecture/viewmodel
[1]: https://developer.android.com/topic/libraries/architecture/livedata
[2]: https://developer.android.com/topic/libraries/architecture/room
[3]: http://jakewharton.github.io/butterknife/
[4]: https://square.github.io/retrofit/
[5]: https://github.com/google/gson
[6]: http://square.github.io/okhttp/
[7]: http://square.github.io/picasso/
[8]: https://developer.android.com/guide/practices/ui_guidelines/icon_design_adaptive
[9]: https://developer.android.com/training/constraint-layout/
