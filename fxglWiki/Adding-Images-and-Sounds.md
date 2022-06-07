This tutorial builds on previous [[Basic Game Example]].
Please complete it first if you haven't done so.

## Tutorial Brief

Works with ![fxgl](https://img.shields.io/badge/fxgl-0.3.8+-blue.svg)

1. Replace player view by an image.
2. Add an image to the game UI.
3. Play a sound when pixels moved is divisible by 100.

By end of this tutorial you will have:

<img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/fxgl_tut2.jpg" width="600" height="600" />

## Preparation

Using the previous tutorial's project, create the `assets` directory in:

* If you use Maven/Gradle: `src/main/resources/`
* If you use uber-jar (as external library): `src/`

**Note**: you can read more on the standard [[Directory Structure]].

Create `textures` and `sounds` directories in `assets`.
I will use this [image](https://github.com/AlmasB/FXGL/blob/master/fxgl-samples/src/main/resources/assets/textures/brick.png) and this [sound](https://github.com/AlmasB/FXGL/blob/master/fxgl-samples/src/main/resources/assets/sounds/drop.wav).
To download, click on the link and then click "Download".
You can use your own or download these ones.
Then place the image and the sound you want to use in your game in `textures` and `sounds` respectively.

## Feature 1 (Player Image)

First we will replace the blue rectangle that is the player with an image of your choice.
It is very easy to do with the fluent API.
In fact, there is only one line of code (commented out below) we will replace from the previous tutorial:

```java
@Override
protected void initGame() {
    player = Entities.builder()
            .at(300, 300)
            //.viewFromNode(new Rectangle(25, 25, Color.BLUE))
            .viewFromTexture("brick.png")
            .buildAndAttach(getGameWorld());
}
```

The image file that I've used is called `brick.png` and is located in `assets/textures/`.
Hence, FXGL knows that it is a texture and so we can simply use just the name.
If your file is named differently, then you will use that name instead.

You should now be able to move the player (who is now represented by a brick image, rather than a blue rectangle).
Wasn't too hard, was it? 👍 

## Feature 2 (UI Image)

Remember where we put UI code?
Yes, it's in `initUI()`!
Let's add the following:

```java
Texture brickTexture = getAssetLoader().loadTexture("brick.png");
brickTexture.setTranslateX(50);
brickTexture.setTranslateY(450);

getGameScene().addUINode(brickTexture);
```

It should pretty straightforward just by reading the code.
We can load any asset by calling `getAssetLoader().load ...`.
We are loading from `textures/`, so it's `loadTexture()`.
You can find the full list of asset types that FXGL supports in [[Assets]].

**Note**: a `Texture` is also a JavaFX `Node`, so you can easily use in UI and call UI methods, such as `setTranslate()`.
Almost done 😃 

## Feature 3 (Sound)

We want to play a sound.
We can do that by calling `getAudioPlayer().playSound("drop.wav")`.
To keep things interesting in `initInput()` we can add:

```java
input.addAction(new UserAction("Play Sound") {
    @Override
    protected void onActionBegin() {
        getAudioPlayer().playSound("drop.wav");
    }
}, KeyCode.F);      
```

You should be able to guess what it does.
When `F` is pressed the sound will be played.
Note the `onActionBegin` as opposed to `onAction`.
The former means to execute the action only once, in the same frame when the key is pressed.

We could, as with `Texture`, call `getAssetLoader().loadSound("drop.wav")`, apply some settings and then play it.
However, if you just want to play a sound once with default settings, then `getAudioPlayer().playSound("drop.wav")` is much more concise.

You now know how to add images and sounds to your game. Yay!

[Link](https://github.com/AlmasB/FXGL/blob/master/fxgl-samples/src/main/java/tutorial/BasicGameApp2.java) to the full source code of this tutorial. Enjoy!

Want some more?
Follow a more complex video [tutorial](https://www.youtube.com/watch?v=yrRfma4a_7U).
Explore [[Core Features]], or experiment with pre-made [games](https://github.com/AlmasB/FXGLGames).