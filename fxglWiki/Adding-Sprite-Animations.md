This tutorial is standalone, but it is expected that you've completed previous tutorials, hence we won't talk about the things we already discussed.
If you get lost, there's a link to the full source code at the very end.

## Tutorial Brief

Works with ![fxgl](https://img.shields.io/badge/fxgl-0.5.0+-blue.svg)

1. Add a player whose view is an animated sprite loaded from a sprite sheet.

## Preparation

I will use [this simple sprite sheet](https://github.com/AlmasB/FXGL/blob/master/fxgl-samples/src/main/resources/assets/textures/newdude.png) that is adapted from the Phaser game engine.
Just drop the file under `assets/textures`.

## Basics

Let's quickly get our game app going:

```
public class BasicGameApp4 extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

Run the app and you should have a basic 800x600 window.

## Add player

Let's add a player at 200, 200 without any view but with a `DudeControl`, which we will create further below.

```
private Entity player;

@Override
protected void initGame() {
    player = Entities.builder()
            .at(200, 200)
            .with(new DudeControl())
            .buildAndAttach();
}
```

You can read about `Component` in FXGL wiki but in short, a component defines / adds a behavior to an entity.

## Add DudeControl

Here's the full source of DudeControl, which we will discuss line by line.

```
class DudeControl extends Component {

    private int speed = 0;

    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk;

    public DudeControl() {
        animIdle = new AnimationChannel("newdude.png", 4, 32, 42, Duration.seconds(1), 1, 1);
        animWalk = new AnimationChannel("newdude.png", 4, 32, 42, Duration.seconds(1), 0, 3);

        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.setView(texture);
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

First, `class DudeControl extends Component`, any component extends `Component`.

```
private int speed = 0;
```

We will use this variable to check if our _dude_ is moving.
The following is the core of this tutorial.
First, we declare an animated texture, which is the same as a normal texture, except it can have animation channels.
An animation channel defines a single animation, such as "walk", "stand", "attack", "jump", etc.
In this example there are two channels: idle and walk, shown in the snippet below.
In the DudeControl constructor we set up the channels with the following parameters: `sprite sheet name`, `number of frames per row`, `single frame width`, `single frame height`, `duration of the animation channel`, `start frame` and `end frame`.

```
private AnimatedTexture texture;
private AnimationChannel animIdle, animWalk;

public DudeControl() {
    animIdle = new AnimationChannel("newdude.png", 4, 32, 42, Duration.seconds(1), 1, 1);
    animWalk = new AnimationChannel("newdude.png", 4, 32, 42, Duration.seconds(1), 0, 3);

    texture = new AnimatedTexture(animIdle);
}

@Override
public void onAdded() {
    entity.setView(texture);
}
```

Note that `entity.setView(texture);` is called in `onAdded()` and not in the constructor.
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

We translate (move) the entity by given speed, then we check what animation to use.
If we are not moving (`speed == 0`) then set animation to idle, else set animation to walk.
Reduce the speed by 10% and if speed is less than 1, set it to 0.

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

The following is straightforward: we bind "A" and "D" to call DudeControl's left and right movement methods.

```
@Override
protected void initInput() {
    getInput().addAction(new UserAction("Right") {
        @Override
        protected void onAction() {
            player.getComponent(DudeControl.class).moveRight();
        }
    }, KeyCode.D);

    getInput().addAction(new UserAction("Left") {
        @Override
        protected void onAction() {
            player.getComponent(DudeControl.class).moveLeft();
        }
    }, KeyCode.A);
}
```

You should now be able to use a simple sprite sheet animation in your games!

The [link](https://github.com/AlmasB/FXGL/blob/master/fxgl-samples/src/main/java/tutorial/BasicGameApp4.java) to the full source code of this tutorial.

Want some more?
Explore [[Core Features]], or experiment with pre-made [games](https://github.com/AlmasB/FXGLGames).
Alternatively, try another [tutorial](https://github.com/AlmasB/FXGL/wiki/Simple-Game---Pong) on making a clone of Pong