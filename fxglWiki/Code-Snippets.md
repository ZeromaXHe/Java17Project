This page contains various useful code snippets. Note that if you are calling `FXGL.` methods within your `GameApplication` class, then in most cases `FXGL.` can be omitted (`getAssetLoader()` instead of `FXGL.getAssetLoader()`).

## Custom App Icon

1. place your `icon.png` in `/assets/textures`
2. `settings.setAppIcon("icon.png");`

## Scrolling background image

```java
Texture texture = FXGL.getAssetLoader().loadTexture("background.png");
ScrollingBackgroundView bg = new ScrollingBackgroundView(texture, Orientation.HORIZONTAL);

getGameScene().addGameView(bg);
```

## Looping background music

```java
FXGL.getAudioPlayer().loopBGM("music.mp3");
```

## Passing references around in your game

Say you have an A* grid object in your MyApp that you want to access from some AIControl. Have a public accessor to return your grid object in MyApp:

`public AStarGrid getGrid() {}`

Then, in AIControl, you can obtain the reference to your app and typecast it to your app type:
```java
MyApp app = (MyApp) FXGL.getApp();
app.getGrid() ... // do stuff with the grid
```

## Listening for entity events

You can listen for entity added / removed events directly from the entity itself.
This might be convenient for setting up simple callbacks.

```java
Entity e = ...

// will be called when entity is actually added to the world
e.setOnActive(() -> ...);

// will be called when entity is actually removed from the world
e.setOnNotActive(() -> ...); 
```

## Adding mouse event handlers

An entity view is a JavaFX node and that's everything you need to know to get going.
```java
Entity e = ...
EntityView view = e.getView();
view.setOnMouseClicked(e -> ...);
```

## Locking Screen Orientation on Mobiles

#### Android

As per [Android dev guide](https://developer.android.com/guide/topics/manifest/activity-element#screen) in AndroidManifest.xml update `android:screenOrientation=""` as necessary - [example](https://github.com/AlmasB/FXGL-MobileApp/blob/master/src/android/AndroidManifest.xml#L11)

#### iOS

In Default-info.plist update `<key>UISupportedInterfaceOrientations</key>` as necessary - [example](https://github.com/AlmasB/FXGL-MobileApp/blob/master/src/ios/Default-Info.plist#L32)