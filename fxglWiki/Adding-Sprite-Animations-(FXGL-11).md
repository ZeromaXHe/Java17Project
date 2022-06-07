This tutorial is standalone, but it is expected that you've completed previous tutorials, hence we won't talk about the things we already discussed. If you get lost, the full source code is at the very end.

## Tutorial Brief

Works with ![fxgl](https://img.shields.io/badge/fxgl-11-blue.svg)

1. Add a player whose view is an animated sprite loaded from a sprite sheet.

## Preparation

I will use [this simple sprite sheet](https://github.com/AlmasB/FXGL/blob/master/fxgl-samples/src/main/resources/assets/textures/newdude.png) that is adapted from the Phaser game engine.
Just drop the file under `assets/textures`.

## Basics

Let's quickly get our game app going:

```
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

public class SpriteSheetAnimationApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) { }

    public static void main(String[] args) {
        launch(args);
    }
}
```

Run the app and you should have a basic 800x600 window.

## Add player

Let's add a player at 200, 200 without any view but with an `AnimationComponent`, which we will create further below.

```
private Entity player;

@Override
protected void initGame() {
    player = FXGL.entityBuilder()
            .at(200, 200)
            .with(new AnimationComponent())
            .buildAndAttach();
}
```

You can read about `Component` in FXGL wiki but in short, a component holds data and adds a behavior to an entity.

## Add AnimationComponent

Here's the full source of AnimationComponent, which we will discuss line by line.

```
public class AnimationComponent extends Component {

    private int speed = 0;

    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk;

    public AnimationComponent() {
        animIdle = new AnimationChannel(FXGL.image("newdude.png"), 4, 32, 42, Duration.seconds(1), 1, 1);
        animWalk = new AnimationChannel(FXGL.image("newdude.png"), 4, 32, 42, Duration.seconds(1), 0, 3);

        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        entity.translateX(speed * tpf);

        if (speed != 0) {

            if (texture.getAnimationChannel() == animIdle) {
                texture.loopAnimationChannel(animWalk);
            }

            speed = (int) (speed * 0.9);

            if (FXGLMath.abs(speed) < 1) {
                speed = 0;
                texture.loopAnimationChannel(animIdle);
            }
        }
    }

    public void moveRight() {
        speed = 150;

        getEntity().setScaleX(1);
    }

    public void moveLeft() {
        speed = -150;

        getEntity().setScaleX(-1);
    }
}
```

First, `class AnimationComponent extends Component`, any component extends `Component`.

```
private int speed = 0;
```

We will use this variable to check if the player character is moving.
The following is the core of this tutorial.
First, we declare an animated texture, which is the same as a normal texture, except it can have animation channels.
An animation channel defines a single animation, such as "walk", "stand", "attack", "jump", etc.
In this example there are two channels: idle and walk, shown in the snippet below.
In the AnimationComponent constructor we set up the channels with the following parameters: `sprite sheet image`, `number of frames per row`, `single frame width`, `single frame height`, `duration of the animation channel`, `start frame` and `end frame`.

```
private AnimatedTexture texture;
private AnimationChannel animIdle, animWalk;

public AnimationComponent() {
    animIdle = new AnimationChannel(FXGL.image("newdude.png"), 4, 32, 42, Duration.seconds(1), 1, 1);
    animWalk = new AnimationChannel(FXGL.image("newdude.png"), 4, 32, 42, Duration.seconds(1), 0, 3);

    texture = new AnimatedTexture(animIdle);
}

@Override
public void onAdded() {
    entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
    entity.getViewComponent().addChild(texture);
}
```

We set the scale origin so that when we flip the player view, it perfectly mirrors the image.
Note that `entity.getViewComponent().addChild(texture);` is called in `onAdded()` and not in the constructor.
In the constructor we don't know what entity the component will be attached to,
whereas in `onAdded()` we do.

Let's consider the update method:

```
@Override
public void onUpdate(double tpf) {
    entity.translateX(speed * tpf);

    if (speed != 0) {

        if (texture.getAnimationChannel() == animIdle) {
            texture.loopAnimationChannel(animWalk);
        }

        speed = (int) (speed * 0.9);

        if (FXGLMath.abs(speed) < 1) {
            speed = 0;
            texture.loopAnimationChannel(animIdle);
        }
    }
}
```

We translate (move) the entity by given speed, then we check what animation to use. If we are moving (`speed != 0`) we check if the current animation is idle, if it is, then we switch to the "walk" animation. We reduce the speed by 10% and then check if the speed is small enough for us to stop. To stop, we set the speed to 0 and loop the "idle" animation.

Move left and right methods allow us to make the entity face the right direction when moving:

```
public void moveRight() {
    speed = 150;

    getEntity().setScaleX(1);
}

public void moveLeft() {
    speed = -150;

    getEntity().setScaleX(-1);
}
```

## Adding Input

The following is straightforward: we bind "A" and "D" to call AnimationComponent's left and right movement methods.

```
@Override
protected void initInput() {
    FXGL.getInput().addAction(new UserAction("Right") {
        @Override
        protected void onAction() {
            player.getComponent(AnimationComponent.class).moveRight();
        }
    }, KeyCode.D);

    FXGL.getInput().addAction(new UserAction("Left") {
        @Override
        protected void onAction() {
            player.getComponent(AnimationComponent.class).moveLeft();
        }
    }, KeyCode.A);
}
```

You should now be able to use a simple sprite sheet animation in your games!

### Full Source Code

```
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;

public class SpriteSheetAnimationApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) { }

    private Entity player;

    @Override
    protected void initInput() {
        FXGL.getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(AnimationComponent.class).moveRight();
            }
        }, KeyCode.D);

        FXGL.getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(AnimationComponent.class).moveLeft();
            }
        }, KeyCode.A);
    }

    @Override
    protected void initGame() {
        player = FXGL.entityBuilder()
                .at(200, 200)
                .with(new AnimationComponent())
                .buildAndAttach();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

```
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class AnimationComponent extends Component {

    private int speed = 0;

    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk;

    public AnimationComponent() {
        animIdle = new AnimationChannel(FXGL.image("newdude.png"), 4, 32, 42, Duration.seconds(1), 1, 1);
        animWalk = new AnimationChannel(FXGL.image("newdude.png"), 4, 32, 42, Duration.seconds(1), 0, 3);

        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        entity.translateX(speed * tpf);

        if (speed != 0) {

            if (texture.getAnimationChannel() == animIdle) {
                texture.loopAnimationChannel(animWalk);
            }

            speed = (int) (speed * 0.9);

            if (FXGLMath.abs(speed) < 1) {
                speed = 0;
                texture.loopAnimationChannel(animIdle);
            }
        }
    }

    public void moveRight() {
        speed = 150;

        getEntity().setScaleX(1);
    }

    public void moveLeft() {
        speed = -150;

        getEntity().setScaleX(-1);
    }
}
```


Want some more?
Explore [[Core Features]], or experiment with pre-made [games](https://github.com/AlmasB/FXGLGames).
Alternatively, try another [tutorial](https://github.com/AlmasB/FXGL/wiki/Simple-Game---Pong) on making a clone of Pong