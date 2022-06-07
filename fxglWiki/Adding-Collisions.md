This tutorial builds on previous [[Adding Images and Sounds]].
Please complete it first if you haven't done so.

## Tutorial Brief

Works with ![fxgl](https://img.shields.io/badge/fxgl-0.3.8+-blue.svg)

1. Add a coin entity.
2. Add a collision handler between the player and the coin.

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
Entities.builder()
        .type(EntityType.COIN)
        .at(500, 200)
        .viewFromNodeWithBBox(new Circle(15, Color.YELLOW))
        .with(new CollidableComponent(true))
        .buildAndAttach(getGameWorld());
```

We set its type by calling `.type()`.
Then we use `viewFromNodeWithBBox()` to ask FXGL to generate the entity bounding box from its view, which is a yellow circle.
Finally, we attach `CollidableComponent` to mark the entity that it should participate in collision detection and handling.

## Add a collision handler

We are going to slightly modify our player entity creation.
You should now have enough knowledge to understand all following method calls.

```
player = Entities.builder()
        .type(EntityType.PLAYER)
        .at(300, 300)
        .viewFromTextureWithBBox("brick.png")
        .with(new CollidableComponent(true))
        .buildAndAttach(getGameWorld());
```

Now, both player and coin are ready to participate in collision detection.
The last thing to add is the actual handler, i.e. the code that runs when player and coin collide.

```
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
```

We attach the handler to the physics world.
The handler constructor takes two entity types.
We passed `PLAYER` first and then `COIN`.
Therefore, in `onCollisionBegin()` and other overriddable callbacks the order of entities will be `player` and then `coin`.
The only thing we call in this example is `coin.removeFromWorld();`, which removes the coin entity from the game world.

You now have the knowledge to make two entities collide with each other and listen for their collision events.
Well done!

[Link](https://github.com/AlmasB/FXGL/blob/0.4.0/fxgl-samples/src/main/java/tutorial/BasicGameApp3.java) to the full source code of this tutorial. Enjoy!

Want some more?
Follow a more complex video [tutorial](https://www.youtube.com/watch?v=yrRfma4a_7U).
Explore [[Core Features]], or experiment with pre-made [games](https://github.com/AlmasB/FXGLGames).