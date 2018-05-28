# Tradur

Tradur is a text translation Android library which allows your application's users to easily translate any text content in the app to their device's language.

Since Tradur is built on top of the Google Translate API, it supports translating text **from and to more than 100 languages**.

This library can be very useful for social media apps, or in apps where users post content in different languages. In fact, it was inspired by Instagram's "See translation" feature.


## Usage

Three easy steps:

1. Call `Tradur`'s `init` method with your Google Translate API key in the `onCreate` of your application class.
```kotlin
class YourApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Tradur.init(<API_KEY>)
    }
}
```

2. Create a textView and assign it an id

```xml
<TextView
     android:id="@+id/textview"
     android:layout_width="match_parent"
     android:layout_height="wrap_content"
     android:text="Text you want the user to be able to translate" />
```

3. Define a `TradurTextView` element in the same layout and link it to the textView you just created

```xml
<io.husaynhakeem.tradur.TradurTextView
     android:layout_width="match_parent"
     android:layout_height="wrap_content"
     app:translate_field="@+id/textview"
     ... />
```

That's it! Check out the sample app in the `sample/` folder to see it in action.

For further custumization, you can choose to change one of the following:

- The pre-translation message: The text displayed in the `TradurTextView` element before the user decides to translate (by default, it's set to "See translation")
- the loading message: The text displayed in the `TradurTextView` element while the translation is being done (by default, it's set to "Loading…")
- The post-translation message: The text displayed in the `TradurTextView` element after the translation is done (by default, it's set to "See original")


## Demo
![alt text](https://github.com/husaynhakeem/android-tradur/blob/master/art/tradur_demo.gif)


## Download
1. Add below code in your root build.gradle at the end of repositories
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
2. Add the dependency
```groovy
implementation 'com.github.husaynhakeem:android-tradur:v1.0'
```
