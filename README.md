# Tradur

Tradur is a text translation Android library which allows your application's users to easily translate any text content in the app to their device's language.

Since Tradur is built on top of the [MLKit API](https://developers.google.com/ml-kit/language/translation), it supports translating text **from and to more than 50 languages**.

This library can be very useful for social media apps, or in apps where users post content in different languages. In fact, it was inspired by Instagram's "See translation" feature.


## Usage

Two easy steps:

1. Create a textView and assign it an id, e.g. `textview`.

```xml
<TextView
     android:id="@+id/textview"
     android:layout_width="match_parent"
     android:layout_height="wrap_content"
     android:text="Text you want the user to be able to translate" />
```

2. Define a `TradurTextView` element in the same layout and link it to the textView you just created using its id.

```xml
<io.husaynhakeem.tradur.TradurTextView
     android:layout_width="match_parent"
     android:layout_height="wrap_content"
     app:translate_field="@+id/textview"
     ... />
```

That's it! Check out the sample app in the [`sample/`](https://github.com/husaynhakeem/android-tradur/tree/master/sample) folder to see it in action.

For further custumization, you can choose to change one/all of the following:

- The pre-translation message: The text displayed in the `TradurTextView` element before the user chooses to translate text content (by default, it's set to "See translation")
- the loading message: The text displayed in the `TradurTextView` element while the translation is ongoing (by default, it's set to "Loading…")
- The post-translation message: The text displayed in the `TradurTextView` element after the translation is done (by default, it's set to "See original")


## Demo
![demo](https://github.com/husaynhakeem/android-tradur/blob/master/art/tradur_demo.gif)


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
implementation 'com.github.husaynhakeem:android-tradur:2.0'
```
