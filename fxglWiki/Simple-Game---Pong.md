In this tutorial we will make a clone of the classic Pong game.
To complete this tutorial you first need to [get FXGL](https://github.com/AlmasB/FXGL/wiki/Get-FXGL-%28Maven%2C-Gradle%2C-Uber%29) either via Maven / Gradle, or as an uber-jar.
This tutorial is mostly standalone, but completing previous basic tutorials will be very helpful for general understanding.
The full source code is available at the end of this page.
Please note that the code used here is, for simplicity, deliberately monolithic and repetitive.
Many advanced FXGL features are not used, as they require understanding of more complex concepts covered elsewhere in the Wiki.

The game will look like this:

<img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/fxgl_pong.png" width="400" height="300" />

## Imports

Create a file `PongApp.java` and
let's import all of these and forget about them for the rest of the tutorial:

```
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.util.Map;
```

## Code

This section will go through each method and explain the major parts of the code.
By default FXGL sets the game size to 800x600, which works for our game.
You can change these and various other settings via `settings.setXXX()`.
For now, we will just set the title and add the entry point - `main()`.

```
public class PongApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Pong");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

Next, we will define some constants, which are self-explanatory.

```
private static final int PADDLE_WIDTH = 30;
private static final int PADDLE_HEIGHT = 100;
private static final int BALL_SIZE = 20;
private static final int PADDLE_SPEED = 5;
private static final int BALL_SPEED = 5;
```

We have three game objects, in particular, two paddles and a ball.
A game object in FXGL is called an `Entity`.
So, let's define our entities:

```
private Entity paddle1;
private Entity paddle2;
private Entity ball;
```

Next, we will look at input.
Unlike in some frameworks, there is no need to manually query the input state.
In FXGL we handle input by defining actions (what the game should do) and binding them to input triggers (when something is pressed).
For example:

```
@Override
protected void initInput() {
    getInput().addAction(new UserAction("Up 1") {
        @Override
        protected void onAction() {
            paddle1.translateY(-PADDLE_SPEED);
        }
    }, KeyCode.W);

    // ...
}
```

The above means, when `W` is pressed, move `paddle` in Y axis by `-PADDLE_SPEED`,
which essentially means move the paddle up.
The remaining input:

```
getInput().addAction(new UserAction("Down 1") {
    @Override
    protected void onAction() {
        paddle1.translateY(PADDLE_SPEED);
    }
}, KeyCode.S);

getInput().addAction(new UserAction("Up 2") {
    @Override
    protected void onAction() {
        paddle2.translateY(-PADDLE_SPEED);
    }
}, KeyCode.UP);

getInput().addAction(new UserAction("Down 2") {
    @Override
    protected void onAction() {
        paddle2.translateY(PADDLE_SPEED);
    }
}, KeyCode.DOWN);
```

We will now add game variables to keep score for player 1 and player 2.
We could just use `int score1;`.
However, FXGL provides a powerful concept of properties, which builds on JavaFX properties.
To clarify, each variable in FXGL is internally stored as a JavaFX property and is, therefore, observable
and bindable.
We declare variables as follows:

```
@Override
protected void initGameVars(Map<String, Object> vars) {
    vars.put("score1", 0);
    vars.put("score2", 0);
}
```

FXGL will infer the type of each variable based on the default value.
In this case 0 is of type `int`, so `score1` will be assigned `int` type.
We will later see how powerful these variables are compared to primitive Java types.

We now consider creation of our entities.
If you completed previous tutorials, this should be straightforward.

```
@Override
protected void initGame() {
    paddle1 = spawnBat(0, getHeight() / 2 - PADDLE_HEIGHT / 2);
    paddle2 = spawnBat(getWidth() - PADDLE_WIDTH, getHeight() / 2 - PADDLE_HEIGHT / 2);

    ball = spawnBall(getWidth() / 2 - BALL_SIZE / 2, getHeight() / 2 - BALL_SIZE / 2);
}

private Entity spawnBat(double x, double y) {
    return Entities.builder()
            .at(x, y)
            .viewFromNodeWithBBox(new Rectangle(PADDLE_WIDTH, PADDLE_HEIGHT))
            .buildAndAttach();
}

private Entity spawnBall(double x, double y) {
    return Entities.builder()
            .at(x, y)
            .viewFromNodeWithBBox(new Rectangle(BALL_SIZE, BALL_SIZE))
            .with("velocity", new Point2D(BALL_SPEED, BALL_SPEED))
            .buildAndAttach();
}
```
We ask the `Entities` builder to:

1. create a new entity at given x, y
2. use the view we provide
3. generate a bounding box from the view
4. add the created entity to the game world.
5. (in the case of `ball`) we also add a new entity property called "velocity" of type Point2D

Thereafter, we design our UI, which consists of two `Text` objects.
Importantly, we bind the text property of these objects to the two variables we created earlier.
This is one of the powerful features that FXGL variables provide.
More specifically, when `score1` is updated, the `textScore1` UI object's text will be updated automatically.

```
@Override
protected void initUI() {
    Text textScore1 = getUIFactory().newText("", Color.BLACK, 22);
    Text textScore2 = getUIFactory().newText("", Color.BLACK, 22);

    textScore1.setTranslateX(10);
    textScore1.setTranslateY(50);

    textScore2.setTranslateX(getWidth() - 30);
    textScore2.setTranslateY(50);

    textScore1.textProperty().bind(getGameState().intProperty("score1").asString());
    textScore2.textProperty().bind(getGameState().intProperty("score2").asString());

    getGameScene().addUINodes(textScore1, textScore2);
}
```

The last piece of this game is the update tick.
Typically, FXGL games will use `Component`s to provide functionality for entities on each frame.
So the update code may not be required at all.
In this case, being a simple example, we will use the traditional update method, seen below:

```
@Override
protected void onUpdate(double tpf) {
    Point2D velocity = ball.getObject("velocity");
    ball.translate(velocity);

    if (ball.getX() == paddle1.getRightX()
            && ball.getY() < paddle1.getBottomY()
            && ball.getBottomY() > paddle1.getY()) {
        ball.setProperty("velocity", new Point2D(-velocity.getX(), velocity.getY()));
    }

    if (ball.getRightX() == paddle2.getX()
            && ball.getY() < paddle2.getBottomY()
            && ball.getBottomY() > paddle2.getY()) {
        ball.setProperty("velocity", new Point2D(-velocity.getX(), velocity.getY()));
    }

    if (ball.getX() <= 0) {
        getGameState().increment("score2", +1);
        resetBall();
    }

    if (ball.getRightX() >= getWidth()) {
        getGameState().increment("score1", +1);
        resetBall();
    }

    if (ball.getY() <= 0) {
        ball.setY(0);
        ball.setProperty("velocity", new Point2D(velocity.getX(), -velocity.getY()));
    }

    if (ball.getBottomY() >= getHeight()) {
        ball.setY(getHeight() - BALL_SIZE);
        ball.setProperty("velocity", new Point2D(velocity.getX(), -velocity.getY()));
    }
}
```

We grab the "velocity" property of the ball and use it to translate (move) the ball on each frame.
We then do a variety of checks regarding the ball's position against the game window and the paddles.
If the ball hits the top or the bottom of the window, then we reverse in Y axis.
Similarly, if the ball hits a paddle, then we reverse the X axis.
Finally, if the ball misses the paddle and hits the side of the screen, then the opposite paddle scores and the ball is reset.
The reset method is as follows:

```
private void resetBall() {
    ball.setPosition(getWidth() / 2 - BALL_SIZE / 2, getHeight() / 2 - BALL_SIZE / 2);
    ball.setProperty("velocity", new Point2D(BALL_SPEED, BALL_SPEED));
}
```

That is all!
You've now got a simple clone of Pong.
You can get the full source code from the next section.

## Full source code

```
package tutorial;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.util.Map;

public class PongApp extends GameApplication {

    private static final int PADDLE_WIDTH = 30;
    private static final int PADDLE_HEIGHT = 100;
    private static final int BALL_SIZE = 20;
    private static final int PADDLE_SPEED = 5;
    private static final int BALL_SPEED = 5;

    private Entity paddle1;
    private Entity paddle2;
    private Entity ball;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Pong");
    }

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Up 1") {
            @Override
            protected void onAction() {
                paddle1.translateY(-PADDLE_SPEED);
            }
        }, KeyCode.W);

        getInput().addAction(new UserAction("Down 1") {
            @Override
            protected void onAction() {
                paddle1.translateY(PADDLE_SPEED);
            }
        }, KeyCode.S);

        getInput().addAction(new UserAction("Up 2") {
            @Override
            protected void onAction() {
                paddle2.translateY(-PADDLE_SPEED);
            }
        }, KeyCode.UP);

        getInput().addAction(new UserAction("Down 2") {
            @Override
            protected void onAction() {
                paddle2.translateY(PADDLE_SPEED);
            }
        }, KeyCode.DOWN);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score1", 0);
        vars.put("score2", 0);
    }

    @Override
    protected void initGame() {
        paddle1 = spawnBat(0, getHeight() / 2 - PADDLE_HEIGHT / 2);
        paddle2 = spawnBat(getWidth() - PADDLE_WIDTH, getHeight() / 2 - PADDLE_HEIGHT / 2);

        ball = spawnBall(getWidth() / 2 - BALL_SIZE / 2, getHeight() / 2 - BALL_SIZE / 2);
    }

    @Override
    protected void initUI() {
        Text textScore1 = getUIFactory().newText("", Color.BLACK, 22);
        Text textScore2 = getUIFactory().newText("", Color.BLACK, 22);

        textScore1.setTranslateX(10);
        textScore1.setTranslateY(50);

        textScore2.setTranslateX(getWidth() - 30);
        textScore2.setTranslateY(50);

        textScore1.textProperty().bind(getGameState().intProperty("score1").asString());
        textScore2.textProperty().bind(getGameState().intProperty("score2").asString());

        getGameScene().addUINodes(textScore1, textScore2);
    }

    @Override
    protected void onUpdate(double tpf) {
        Point2D velocity = ball.getObject("velocity");
        ball.translate(velocity);

        if (ball.getX() == paddle1.getRightX()
                && ball.getY() < paddle1.getBottomY()
                && ball.getBottomY() > paddle1.getY()) {
            ball.setProperty("velocity", new Point2D(-velocity.getX(), velocity.getY()));
        }

        if (ball.getRightX() == paddle2.getX()
                && ball.getY() < paddle2.getBottomY()
                && ball.getBottomY() > paddle2.getY()) {
            ball.setProperty("velocity", new Point2D(-velocity.getX(), velocity.getY()));
        }

        if (ball.getX() <= 0) {
            getGameState().increment("score2", +1);
            resetBall();
        }

        if (ball.getRightX() >= getWidth()) {
            getGameState().increment("score1", +1);
            resetBall();
        }

        if (ball.getY() <= 0) {
            ball.setY(0);
            ball.setProperty("velocity", new Point2D(velocity.getX(), -velocity.getY()));
        }

        if (ball.getBottomY() >= getHeight()) {
            ball.setY(getHeight() - BALL_SIZE);
            ball.setProperty("velocity", new Point2D(velocity.getX(), -velocity.getY()));
        }
    }

    private Entity spawnBat(double x, double y) {
        return Entities.builder()
                .at(x, y)
                .viewFromNodeWithBBox(new Rectangle(PADDLE_WIDTH, PADDLE_HEIGHT))
                .buildAndAttach();
    }

    private Entity spawnBall(double x, double y) {
        return Entities.builder()
                .at(x, y)
                .viewFromNodeWithBBox(new Rectangle(BALL_SIZE, BALL_SIZE))
                .with("velocity", new Point2D(BALL_SPEED, BALL_SPEED))
                .buildAndAttach();
    }

    private void resetBall() {
        ball.setPosition(getWidth() / 2 - BALL_SIZE / 2, getHeight() / 2 - BALL_SIZE / 2);
        ball.setProperty("velocity", new Point2D(BALL_SPEED, BALL_SPEED));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```