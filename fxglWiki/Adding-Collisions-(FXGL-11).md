This tutorial builds on previous [[Adding Images and Sounds (FXGL 11)]].
Please complete it first if you haven't done so.

## Tutorial Brief

Works with ![fxgl](https://img.shields.io/badge/fxgl-11.11-blue.svg)

1. Add a coin entity.
2. Add a collision handler between the player and the coin.

By end of this tutorial you will have:

<img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/fxgl11_tut3_1.jpg" width="600" height="600" />

## Preparation

For our collisions to work we first need to let FXGL know about entity types.
In our game, they are: player and coin.
It is recommended that a type is represented by an enum.
So, we create an enum `EntityType`:

```
public enum EntityType {
    PLAYER, COIN
}
```

## Add a coin

We are going to add a brand new entity - coin.
In `initGame()`:

```
FXGL.entityBuilder()
        .type(EntityType.COIN)
        .at(500, 200)
        .viewWithBBox(new Circle(15, 15, 15, Color.YELLOW))
        .with(new CollidableComponent(true))
        .buildAndAttach();
```

We set its type by calling `.type()`.
Then we use `viewWithBBox()` to ask FXGL to generate the entity bounding box from its view, which is a yellow circle. (Don't forget to `import javafx.scene.shape.Circle;`).
Finally, we attach `CollidableComponent` to mark the entity that it should participate in collision detection and handling.

## Add a collision handler

We are going to slightly modify our player entity creation.
You should now have enough knowledge to understand all following method calls.

```
player = FXGL.entityBuilder()
        .type(EntityType.PLAYER)
        .at(300, 300)
        .viewWithBBox("brick.png")
        .with(new CollidableComponent(true))
        .buildAndAttach();
```

Now, both player and coin are ready to participate in collision detection.
The last thing to add is the actual handler, i.e. the code that runs when player and coin collide.

```
@Override
protected void initPhysics() {
    FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.COIN) {

        // order of types is the same as passed into the constructor
        @Override
        protected void onCollisionBegin(Entity player, Entity coin) {
            coin.removeFromWorld();
        }
    });
}
```

We attach the handler to the physics world. (Don't forget to `import com.almasb.fxgl.physics.CollisionHandler;`).
The handler constructor takes two entity types.
We passed `PLAYER` first and then `COIN`.
Therefore, in `onCollisionBegin()` the order of entities will be `player` and then `coin`.
The only thing we call in this example is `coin.removeFromWorld()`, which removes the coin entity from the game world.

You now have the knowledge to make two entities collide with each other and listen for their collision events.
Well done!

## Summary

For collisions between two entities `e1` and `e2` to work four things need to be satisfied:
1. both `e1` and `e2` have types
2. both `e1` and `e2` are set to collidable (have a `CollidableComponent` with value `true`)
3. both `e1` and `e2` have at least one hit box each
4. there is a collision handler between type of `e1` and type of `e2`

## Full Source Code

Note the static import:

```
import static com.almasb.fxgl.dsl.FXGL.*;
```

```
package tutorial;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class BasicGameApp extends GameApplication {
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(600);
        settings.setHeight(600);
        settings.setTitle("Basic Game App");
        settings.setVersion("0.1");
    }

    public enum EntityType {
        PLAYER, COIN
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

        onKeyDown(KeyCode.F, () -> {
            play("drop.wav");
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
                .type(EntityType.PLAYER)
                .at(300, 300)
                .viewWithBBox("brick.png")
                .with(new CollidableComponent(true))
                .buildAndAttach();

        entityBuilder()
                .type(EntityType.COIN)
                .at(500, 200)
                .viewWithBBox(new Circle(15, 15, 15, Color.YELLOW))
                .with(new CollidableComponent(true))
                .buildAndAttach();
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.COIN) {

            // order of types is the same as passed into the constructor
            @Override
            protected void onCollisionBegin(Entity player, Entity coin) {
                coin.removeFromWorld();
            }
        });
    }

    @Override
    protected void initUI() {
        Text textPixels = new Text();
        textPixels.setTranslateX(50); // x = 50
        textPixels.setTranslateY(100); // y = 100

        textPixels.textProperty().bind(getWorldProperties().intProperty("pixelsMoved").asString());

        getGameScene().addUINode(textPixels); // add to the scene graph

        var brickTexture = getAssetLoader().loadTexture("brick.png");
        brickTexture.setTranslateX(50);
        brickTexture.setTranslateY(450);

        getGameScene().addUINode(brickTexture);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

Want some more? Feel free to play FXGL games [on itch.io](https://fxgl.itch.io/)
When ready, experiment with their [source code](https://github.com/AlmasB/FXGLGames).