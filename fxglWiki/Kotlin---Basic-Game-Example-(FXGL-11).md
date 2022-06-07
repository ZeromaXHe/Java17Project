In this tutorial we will create a very basic game.
I assume that you have completed [Get FXGL](https://github.com/AlmasB/FXGL/wiki/Get-FXGL-%28Maven%2C-Gradle%2C-Uber%29) and have a Kotlin project in your IDE with access to the latest version of FXGL.
Please note this tutorial is for **FXGL 11.0+** and **Java 11+**.
If you get lost at any point, there is full source code at the bottom of the page.

Works with ![fxgl](https://img.shields.io/badge/fxgl-11.9-blue.svg)

## Set up

Our game is called "Target Practice". The game constantly spawns rectangles that move from left to right and our task is to shoot them.

At the end of this tutorial you should have something like this:
<img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/fxgl_kt.jpg" width="607" height="540" />

I will use `package tutorial`, create a Kotlin class `KotlinGameApp` and add the following imports:

```
import com.almasb.fxgl.app.GameApplication
import com.almasb.fxgl.app.GameSettings
import com.almasb.fxgl.dsl.*
import com.almasb.fxgl.dsl.components.ProjectileComponent
import javafx.geometry.Point2D
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.util.Duration
```

## Creating a basic window

First, I would like to have a working window. The following code does just that. We extend `GameApplication` to use FXGL. We set the required settings, such as width, height and title. By clicking run in your IDE (presumably IntelliJ), you should have a basic window up.

```
class KotlinGameApp : GameApplication() {

    override fun initSettings(settings: GameSettings) {
        with(settings) {
            width = 720
            height = 640
            title = "Kotlin Game - Target Practice"
        }
    }
}

fun main() {
    GameApplication.launch(KotlinGameApp::class.java, emptyArray())
}
```

## Entity Types

An entity is a game object. You can find out more in the wiki. There are two types of entities in the game: bullet and target, so let's create an enum for them:

```
    enum class Type {
        BULLET, TARGET
    }
```

Next, we want to be able to spawn them:

```
    private fun shoot() {
        entityBuilder()
                .type(Type.BULLET)
                .at(getAppWidth() / 2.0, getAppHeight().toDouble())
                .viewWithBBox(Rectangle(10.0, 5.0, Color.BLUE))
                .collidable()
                .with(ProjectileComponent(Point2D(0.0, -1.0), 350.0))
                .buildAndAttach()
    }

    private fun spawnTarget() {
        entityBuilder()
                .type(Type.TARGET)
                .at(0.0, getAppHeight() / 2.0)
                .viewWithBBox(Rectangle(40.0, 40.0, Color.RED))
                .collidable()
                .with(ProjectileComponent(Point2D(1.0, 0.0), 100.0))
                .buildAndAttach()
    }
```

You will find that the two spawn methods are quite similar. We call an entity builder to set the type of the entity to spawn, the position (`at`), the view it's going to use (`viewWithBBox`), which is a rectangle and also we ask the view to generate a bounding box for collisions. We also set the entity to be collidable and to have a projectile component. Components define data and behaviour for each entity. The projectile component will move the entity in the given direction with given speed.

## User Input

The following code should be straightforward. When the `F` key is pressed, call `shoot()`.

```
    override fun initInput() {
        onKeyDown(KeyCode.F, "Shoot") {
            shoot()
        }
    }
```

## Spawning Targets

In FXGL, `initGame()` can be overridden to call code before the game starts. In this game, we want to set up a timer action which fires every second to spawn a target. We can do that as follows:

```
    override fun initGame() {
        run({
            spawnTarget()
        }, Duration.seconds(1.0))
    }
```

## Collisions

The last bit of code is related to adding a collision handler between bullets and targets. Essentially, we want both entities to be removed from the game world when they collide. The following code implements this logic.

```
    override fun initPhysics() {
        onCollisionBegin(Type.BULLET, Type.TARGET) { bullet, target ->
            bullet.removeFromWorld()
            target.removeFromWorld()
        }
    }
```

## Full Source Code

```
package tutorial

import com.almasb.fxgl.app.GameApplication
import com.almasb.fxgl.app.GameSettings
import com.almasb.fxgl.dsl.*
import com.almasb.fxgl.dsl.components.ProjectileComponent
import javafx.geometry.Point2D
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.util.Duration

class KotlinGameApp : GameApplication() {

    enum class Type {
        BULLET, TARGET
    }

    override fun initSettings(settings: GameSettings) {
        with(settings) {
            width = 720
            height = 640
            title = "Kotlin Game - Target Practice"
        }
    }

    override fun initInput() {
        onKeyDown(KeyCode.F, "Shoot") {
            shoot()
        }
    }

    override fun initGame() {
        run({
            spawnTarget()
        }, Duration.seconds(1.0))
    }

    override fun initPhysics() {
        onCollisionBegin(Type.BULLET, Type.TARGET) { bullet, target ->
            bullet.removeFromWorld()
            target.removeFromWorld()
        }
    }

    private fun shoot() {
        entityBuilder()
                .type(Type.BULLET)
                .at(getAppWidth() / 2.0, getAppHeight().toDouble())
                .viewWithBBox(Rectangle(10.0, 5.0, Color.BLUE))
                .collidable()
                .with(ProjectileComponent(Point2D(0.0, -1.0), 350.0))
                .buildAndAttach()
    }

    private fun spawnTarget() {
        entityBuilder()
                .type(Type.TARGET)
                .at(0.0, getAppHeight() / 2.0)
                .viewWithBBox(Rectangle(40.0, 40.0, Color.RED))
                .collidable()
                .with(ProjectileComponent(Point2D(1.0, 0.0), 100.0))
                .buildAndAttach()
    }
}

fun main() {
    GameApplication.launch(KotlinGameApp::class.java, emptyArray())
}
```