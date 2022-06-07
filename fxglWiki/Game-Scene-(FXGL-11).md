This page covers how high-level rendering is done in FXGL (technically in JavaFX since we use it as the graphics framework). You can also read [this](http://docs.oracle.com/javase/8/javafx/get-started-tutorial/jfx-architecture.htm#A1106328) for in-depth coverage of the JavaFX architecture, including the graphics aspect.

## Scene Graph

In order to display an entity, you need to configure and attach its view.
This is done via `ViewComponent`, as follows:

```
// this can be any JavaFX node or some custom graphics
Node node = ...

ViewComponent viewComponent = entity.getViewComponent();
viewComponent.addChild(node);
```

Most of the time you want to use an image as the view.
There is a `Texture` class which allows you to do just that, besides it's also a JavaFX node:

```java
Node node = FXGL.getAssetLoader().loadTexture("player.png");
// same as above
```

Alternatively, entity builder provides some static methods to make building easier, including setting the view:

```java
Entity entity = FXGL.entityBuilder()
                       .view("player.png")
                       .build();
```

Finally, add the entity to the game world:

```java
getGameWorld().addEntity(entity);
```

This will automatically make the game scene pick up the view and show it in the game view.

#### Scene Graph - Game View

The game view is where your world is displayed.
The coordinates in the game view can extend beyond the visible viewport, as your player moves (assuming the viewport is bound to the player), other parts of the world will become visible.
Although most of the scene and game view will consist of entities, it is possible to add a view that is not associated with any entity:

```
Node node = ...

// you can position the node using translate methods (as you normally would in JavaFX)
node.setTranslateX(100);
node.setTranslateY(100);

GameView view = new GameView(node, 0); // 0 is z index

getGameScene().addGameView(view);
```

#### Scene Graph - UI View

This view is where your HUD and various UI elements are located.
The view is fixed in the visible viewport and its coordinate system is always within `0,0` to `width,height`.
This means that while the player can move around freely in the world, the UI stays fixed in one place.
Having fixed coordinates also simplifies designing and positioning UI elements.
To add a node into the UI layer you call a method similar to above:

```java
Node node = ...

// again, position the node using setTranslate()

getGameScene().addUINode(node);
```

**Note:** Most of the FXGL API is symmetric, i.e. where there is an `add`, you can also expect a `remove`. So to remove a UI node, you call `removeUINode(node)`.

## Viewport

Viewport in FXGL takes on the responsibility of a camera.
You can obtain the reference to the viewport as follows:

```
Viewport viewport = getGameScene().getViewport();
```

You can change the `x` and `y` values of the viewport manually by calling:

```
viewport.setX(...);
viewport.setY(...);
```

One of the most useful functions is to bind the viewport to follow an entity:

```
Entity player = ...;

// distX and distY is how far the viewport origin should be from the entity
viewport.bindToEntity(player, distX, distY);
```

As the viewport follows the player, it may go off the level bounds.
You can set the bounds to how far the viewport can "wander off":

```
viewport.setBounds(minX, minY, maxX, maxY);
```