In this tutorial we will make a modified clone of the classic Breakout game. This is a small extension of the [Pong example game](https://github.com/AlmasB/FXGL/wiki/Simple-Game---Pong-%28FXGL-11%29), so please ensure you understand the code in it. To complete this tutorial you first need to [get FXGL](https://github.com/AlmasB/FXGL/wiki/Get-FXGL-%28Maven%2C-Gradle%2C-Uber%29) either via Maven / Gradle, or as an uber-jar. Ensure you use FXGL 11 (e.g. `11.3`). The full source code is available at the end of this page. Please note that the code used here is, for simplicity, deliberately monolithic and repetitive. Unlike the Pong tutorial, here you will be introduced to commonly used FXGL concepts. So the focus is on these concepts, rather than on the game.

The game will look like this:

<img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/breakout_tut11.png" width="400" height="300" />


## Imports

Create a file `BreakoutApp.java` and
let's import all of these and forget about them for the rest of the tutorial. **Note:** the last import (which is static) allows us to write `getInput()` instead of `FXGL.getInput()`, which makes the code concise.

```
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.input.UserAction;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.*;
```

## Code

This section will only explain code that is specific to this tutorial since the rest was explained in an earlier [Pong example](https://github.com/AlmasB/FXGL/wiki/Simple-Game---Pong-%28FXGL-11%29).

We added an enum:

```
private enum BreakoutType {
    BRICK, BALL
}
```

Most FXGL games will have a similar enum to define all entity types in the game. This is typically used by the physics engine to properly handle collisions. A major change in this tutorial is how we specify entity construction. Previously, we used methods, such as `spawnSomething()`. A recommended approach is to use a factory that `implements EntityFactory`:

```
// remove the "static" modifier if the class is in a separate file than BreakoutApp.java
public static class BreakoutFactory implements EntityFactory {

    @Spawns("bat")
    public Entity newBat(SpawnData data) {
        return entityBuilder(data)
                .viewWithBBox(new Rectangle(PADDLE_WIDTH, PADDLE_HEIGHT))
                .build();
    }

    @Spawns("ball")
    public Entity newBall(SpawnData data) {
        return entityBuilder(data)
                .type(BreakoutType.BALL)
                .viewWithBBox(new Rectangle(BALL_SIZE, BALL_SIZE, Color.BLUE))
                .collidable()
                .with("velocity", new Point2D(BALL_SPEED, BALL_SPEED))
                .build();
    }

    @Spawns("brick")
    public Entity newBrick(SpawnData data) {
        return entityBuilder(data)
                .type(BreakoutType.BRICK)
                .viewWithBBox(new Rectangle(BRICK_WIDTH, BRICK_HEIGHT, Color.RED))
                .collidable()
                .build();
    }
}
```

Usually, it's the only class responsible for creating entities in the game. Let's focus on the method that spawns a brick. The `@Spawns("brick")` annotation tells FXGL what informal entity name we are going to use when spawning this entity. For example, we can now use `spawn("brick")` to spawn an entity of type BRICK. The method signature has to match `public Entity anyMethodName(SpawnData data)`, so that extra spawning arguments can be passed in through the `SpawnData` object. The call to `.entityBuilder(data)` allows FXGL to automatically set X and Y that were used to spawn the entity. For example, `spawn("brick", 100, 150)` will spawn an entity of type BRICK at (100, 150). By using `.type(BreakoutType.BRICK)` we can assign a type to our entity. Finally, use `.collidable()` to mark the entity as collidable. Other entity spawn methods are of similar format.

The last addition in this tutorial is collision handling, which is straightforward:

```
@Override
protected void initPhysics() {
    onCollisionBegin(BreakoutType.BALL, BreakoutType.BRICK, (ball, brick) -> {
        brick.removeFromWorld();
        Point2D velocity = ball.getObject("velocity");

        if (FXGLMath.randomBoolean()) {
            ball.setProperty("velocity", new Point2D(-velocity.getX(), velocity.getY()));
        } else {
            ball.setProperty("velocity", new Point2D(velocity.getX(), -velocity.getY()));
        }
    });
}
```

The `onCollisionBegin()` method can be used to handle collisions during the first frame of their occurrence. There are other similar methods `onCollision()` and `onCollisionEnd()`, which are fired during and at the end of collisions respectively. We specify in order, what types collides with what type (`BreakoutType.BALL, BreakoutType.BRICK`) and we get a callback (`(ball, brick) -> {}`) when a collision occurs, where the order of entities passed in is the order of types we specified.


## Full source code

```
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.input.UserAction;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class BreakoutApp extends GameApplication {

    private static final int PADDLE_WIDTH = 30;
    private static final int PADDLE_HEIGHT = 100;
    private static final int BRICK_WIDTH = 50;
    private static final int BRICK_HEIGHT = 25;
    private static final int BALL_SIZE = 20;

    private static final int PADDLE_SPEED = 5;
    private static final int BALL_SPEED = 5;

    private Entity paddle1;
    private Entity paddle2;
    private Entity ball;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Breakout");
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
    protected void initGame() {
        getGameWorld().addEntityFactory(new BreakoutFactory());

        paddle1 = spawn("bat", 0, getAppHeight() / 2 - PADDLE_HEIGHT / 2);
        paddle2 = spawn("bat", getAppWidth() - PADDLE_WIDTH, getAppHeight() / 2 - PADDLE_HEIGHT / 2);

        ball = spawn("ball", getAppWidth() / 2 - BALL_SIZE / 2, getAppHeight() / 2 - BALL_SIZE / 2);

        for (int i = 0; i < 10; i++) {
            spawn("brick", getAppWidth() / 2 - 200 - BRICK_WIDTH, 100 + i*(BRICK_HEIGHT + 20));
            spawn("brick", getAppWidth() / 2 + 200, 100 + i*(BRICK_HEIGHT + 20));
        }
    }

    @Override
    protected void initPhysics() {
        onCollisionCollectible(BreakoutType.BALL, BreakoutType.BRICK, (brick) -> {
            Point2D velocity = ball.getObject("velocity");

            if (FXGLMath.randomBoolean()) {
                ball.setProperty("velocity", new Point2D(-velocity.getX(), velocity.getY()));
            } else {
                ball.setProperty("velocity", new Point2D(velocity.getX(), -velocity.getY()));
            }
        });
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
            resetBall();
        }

        if (ball.getRightX() >= getAppWidth()) {
            resetBall();
        }

        if (ball.getY() <= 0) {
            ball.setY(0);
            ball.setProperty("velocity", new Point2D(velocity.getX(), -velocity.getY()));
        }

        if (ball.getBottomY() >= getAppHeight()) {
            ball.setY(getAppHeight() - BALL_SIZE);
            ball.setProperty("velocity", new Point2D(velocity.getX(), -velocity.getY()));
        }
    }

    private void resetBall() {
        ball.setPosition(getAppWidth() / 2 - BALL_SIZE / 2, getAppHeight() / 2 - BALL_SIZE / 2);
        ball.setProperty("velocity", new Point2D(BALL_SPEED, BALL_SPEED));
    }

    private enum BreakoutType {
        BRICK, BALL
    }

    public static class BreakoutFactory implements EntityFactory {

        @Spawns("bat")
        public Entity newBat(SpawnData data) {
            return entityBuilder(data)
                    .viewWithBBox(new Rectangle(PADDLE_WIDTH, PADDLE_HEIGHT))
                    .build();
        }

        @Spawns("ball")
        public Entity newBall(SpawnData data) {
            return entityBuilder(data)
                    .type(BreakoutType.BALL)
                    .viewWithBBox(new Rectangle(BALL_SIZE, BALL_SIZE, Color.BLUE))
                    .collidable()
                    .with("velocity", new Point2D(BALL_SPEED, BALL_SPEED))
                    .build();
        }

        @Spawns("brick")
        public Entity newBrick(SpawnData data) {
            return entityBuilder(data)
                    .type(BreakoutType.BRICK)
                    .viewWithBBox(new Rectangle(BRICK_WIDTH, BRICK_HEIGHT, Color.RED))
                    .collidable()
                    .build();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```