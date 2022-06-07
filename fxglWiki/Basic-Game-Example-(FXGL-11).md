In this tutorial we will create a very basic game.
I assume that you have completed [Get FXGL](https://github.com/AlmasB/FXGL/wiki/Get-FXGL-%28Maven%2C-Gradle%2C-Uber%29) and have a Java project in your IDE with access to the latest version of FXGL.
Please note this tutorial is for **FXGL 11+** and **Java 11+**.
If you get lost at any point, there is full source code at the bottom of the page.

## Game Requirements

Works with ![fxgl](https://img.shields.io/badge/fxgl-11.11-blue.svg)

First, let's define some requirements for our simple game:

1. A 600x600 window.
2. A player on the screen, represented by a blue rectangle.
3. The player can be moved by pressing W, S, A or D on the keyboard.
4. UI is represented by a single line of text.
5. When the player moves, the UI text updates to show how many pixels the player has moved during its lifetime.

At the end of this tutorial you should have something like this (maybe slightly different):
<img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/tutorials/basic_game_example.jpg" width="600" height="600" />

Although it may not look like a game, it will help you understand the basic features of FXGL.
After you have finished this tutorial, you can build a variety of simple games.

## Preparation

Now that we have a rough idea of what we are expecting from the game, we can go back to the IDE and create a package for our game.

**Note**: the directory structure is similar to the Maven directory structure, however, if you don't know what this is, don't worry.
We will cover the structure at a later stage. At this point having "src" as the main source directory is sufficient. 

I'm going to use "**tutorial**" as the package name.

1. Create package "**tutorial**" in your IDE.
2. Create a Java class with the name **BasicGameApp** in that package.

It is common to append "App" to the class where your `main()` is.
This allows other developers to easily identify where the main entry point to your game is.
To make your next steps a lot easier I suggest that you open your `BasicGameApp` class and add these imports:

```java
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.util.Map;
```

We are now ready to do some coding.

## Coding Stage

In order to use FXGL your `App` class needs to extend `GameApplication` and override the `initSettings()` method:

```java
public class BasicGameApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {}
}
```

Most IDEs will generate the overridden method automatically, as soon as you extend `GameApplication`.
Now we want to be able to start the game.
To do that simply add the following:

```java
public static void main(String[] args) {
    launch(args);
}
```

If you've done any JavaFX programming before, then you'll notice that it is the exact same signature that we use to start a JavaFX application.
In a nutshell, FXGL is a JavaFX application with game development features, nothing more.

## Requirement 1 (Window)

At this point you should already be able to run your game, but first let's tweak some settings.

```java
@Override
protected void initSettings(GameSettings settings) {
    settings.setWidth(600);
    settings.setHeight(600);
    settings.setTitle("Basic Game App");
    settings.setVersion("0.1");
}
```

As you can see, all the settings are changed within `initSettings()`.
Once they are set, the settings cannot be changed during runtime.
You can now click "run" in your IDE, which should start the game with a 600x600 window and "Basic Game App" as a title.

We now achieved our requirement 1. Was easy, right?

## Requirement 2 (Player)

The next step is to add a player on the screen.
We are going to do this in `initGame()`.
In short, this is where you set up all the stuff that needs to be ready before the game starts.

```java
private Entity player;

@Override
protected void initGame() {
    player = FXGL.entityBuilder()
            .at(300, 300)
            .view(new Rectangle(25, 25, Color.BLUE))
            .buildAndAttach();
}
```

(**Note**: it is important for saving/loading systems that we don't initialize instance level fields on declaration but do it in 'initGame()'.)

If you are not familiar with fluent API, then this is a lot to take in one go. So we'll start slowly. There is an instance level field named `player` of type `Entity`. An entity is basically a game object.
This is everything you need to know about it for now.
`FXGL.entityBuilder()` is a preferred way to build entities.
By calling `.at()` we position the entity where we want.
In this case it's `x = 300`, `y = 300`.

(**Note**: a position of an entity in FXGL is its top-left point, like in JavaFX.)

We then tell the builder to create the view of the entity by using the UI node we pass in as the parameter.
Here it's a standard JavaFX `Rectangle` with `width = 25`, `height = 25` and color blue.

(**Note**: you can use any JavaFX node based object, which is pretty cool 😄)

Finally, we call `.buildAndAttach()`.
By calling build we can obtain the reference to the entity that we were building.
As for the "attach" part, it conveniently allows to attach the built entity straight to the game world.
If you run the game you should now see a blue rectangle near the center of the screen.

Great, we just hit requirement number 2!

## Requirement 3 (Input)

We will now proceed with the requirement related to user input. We put the input handling code in `initInput()`. The below code shows the "full" API for adding an input action. After considering it, we will see how to use simpler API.

```java
@Override
protected void initInput() {
    Input input = FXGL.getInput();

    input.addAction(new UserAction("Move Right") {
        @Override
        protected void onAction() {
            player.translateX(5); // move right 5 pixels
        }
    }, KeyCode.D);
}
```

Let's go through this snippet line by line.
We first get the input object.
As you've noticed to use most of the FXGL functionality all you need to do is call `FXGL.***` and your IDE will show you all the functions you can call.
Next, we add an action, followed by a key code.
Again, if you've used JavaFX before then you'll know that these are exactly the same key codes used in event handlers.
We are saying: when 'D' is pressed, do the action we've created.
Now let's look at the action itself.
When we create an action, we also give it a name - "Move Right".
It is important, as this is fed directly to the controls and menu systems where the user can change them anytime.
So the name must be meaningful to the user and also unique.
Once we've created the action, we override one of its methods (`onAction()` this time), and supply some code.
That code will be called when the action happens, i.e. when 'D' is pressed.
Recall from the requirements that we want to move the player.
So when 'D' is pressed we want to move the player to the right.
We call `player.translateX(5)`, which translates its X coordinate by 5 pixels.

(**Note**: translate is a terminology used in computer graphics and means move.)

Now let's shorten it:

```
@Override
protected void initInput() {
    FXGL.onKey(KeyCode.D, () -> {
        player.translateX(5); // move right 5 pixels
    });
}
```

This also results in the player entity moving 5 pixels to the right and is (almost) equivalent to the code we wrote earlier. However, I think you'll agree that this API (known as DSL) is much more concise. This can be shortened further if you statically import `FXGL.*` calls, i.e.:

```
import static com.almasb.fxgl.dsl.FXGL.*;
```

However, to avoid introducing too many new concepts, we won't do that just yet. You can probably guess what the rest of the input code will look like, but just in case, here it is in full.

```java
@Override
protected void initInput() {
    FXGL.onKey(KeyCode.D, () -> {
        player.translateX(5); // move right 5 pixels
    });

    FXGL.onKey(KeyCode.A, () -> {
        player.translateX(-5); // move left 5 pixels
    });

    FXGL.onKey(KeyCode.W, () -> {
        player.translateY(-5); // move up 5 pixels
    });

    FXGL.onKey(KeyCode.S, () -> {
        player.translateY(5); // move down 5 pixels
    });
}
```

Requirement 3 - done and dusted.
We are more than halfway through, well done!

## Requirement 4 (UI)

We now move on to the next bit - UI, which we handle in, you've guessed it, `initUI()`.

```java
@Override
protected void initUI() {
    Text textPixels = new Text();
    textPixels.setTranslateX(50); // x = 50
    textPixels.setTranslateY(100); // y = 100

    FXGL.getGameScene().addUINode(textPixels); // add to the scene graph
}
```

For most UI objects we simply use JavaFX objects, since there is no need to re-invent the wheel.
You should note that when we added an entity to the world, the game scene
picked up the fact that the entity had a view associated with it.
Hence, the game scene _magically_ added the entity to the scene graph.
With UI objects **we** are responsible for their additions to the scene graph and we can do so by calling `getGameScene().addUINode()`.

That's it for the requirement 4.
Keep it up!

## Requirement 5 (Gameplay)

In order to complete the last requirement we are going to use a *game variable*.
In FXGL, a game variable can be accessed and modified from any part of the game.
In a sense it's a global variable with the scope being tied to the FXGL game instance.
In addition, such variables can be bound _to_ (similar to JavaFX properties).
We start by creating such a variable:

```java
@Override
protected void initGameVars(Map<String, Object> vars) {
    vars.put("pixelsMoved", 0);
}
```

Then we need to update the variable when the player moves.
We can do this in the input handling section.

```java
FXGL.onKey(KeyCode.D, () -> {
    player.translateX(5); // move right 5 pixels
    FXGL.inc("pixelsMoved", +5);
});
```

I'll let you do the same with the rest of movement actions (left, up and down).
The last step (both for the requirement and the tutorial) is to bind our UI text object to the variable `pixelsMoved`.
In `initUI()` once we've created the `textPixels` object, we can do the following:

```java
textPixels.textProperty().bind(FXGL.getWorldProperties().intProperty("pixelsMoved").asString());
```

After that the UI text will show how many pixels the player has moved automatically.

You now have a basic FXGL game.
Hopefully you've had fun.
Here's the full source code of this tutorial with all `FXGL.*` calls statically imported.

```
package tutorial;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

public class BasicGameApp extends GameApplication {
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(600);
        settings.setHeight(600);
        settings.setTitle("Basic Game App");
        settings.setVersion("0.1");
    }

    @Override
    protected void initInput() {
        onKey(KeyCode.D, () -> {
            player.translateX(5); // move right 5 pixels
            inc("pixelsMoved", +5);
        });

        onKey(KeyCode.A, () -> {
            player.translateX(-5); // move left 5 pixels
            inc("pixelsMoved", -5);
        });

        onKey(KeyCode.W, () -> {
            player.translateY(-5); // move up 5 pixels
            inc("pixelsMoved", +5);
        });

        onKey(KeyCode.S, () -> {
            player.translateY(5); // move down 5 pixels
            inc("pixelsMoved", +5);
        });
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pixelsMoved", 0);
    }

    private Entity player;

    @Override
    protected void initGame() {
        player = entityBuilder()
                .at(300, 300)
                .view(new Rectangle(25, 25, Color.BLUE))
                .buildAndAttach();
    }

    @Override
    protected void initUI() {
        Text textPixels = new Text();
        textPixels.setTranslateX(50); // x = 50
        textPixels.setTranslateY(100); // y = 100

        textPixels.textProperty().bind(getWorldProperties().intProperty("pixelsMoved").asString());

        getGameScene().addUINode(textPixels); // add to the scene graph
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

Want some more?
Follow [[Adding Images and Sounds (FXGL 11)]], a quick tutorial that builds on this one.
Enjoy!