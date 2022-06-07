FXGL, like many other 2D game frameworks, uses [jbox2d](https://github.com/jbox2d/jbox2d) to deal with physics.
We will only cover how to use jbox2d within FXGL, since the library has its own documentation.

## Adding Physics to Entities

In order to add an entity to the physics world, all we need to do is configure `PhysicsComponent` and attach it to the entity:

```java
PhysicsComponent physics = new PhysicsComponent();

physics.setBodyType(BodyType.DYNAMIC);

// these are direct jbox2d objects, so we don't actually introduce new API
FixtureDef fd = new FixtureDef();
fd.setDensity(0.7f);
fd.setRestitution(0.3f);
physics.setFixtureDef(fd);

Entity entity = ...
entity.addComponent(physics);

getGameWorld().addEntity(entity);
```

Once the entity is added to the game world, the `PhysicsComponent` will be automatically picked up by the physics world.
It is important to note that an entity with such component is now managed by the physics world.
Hence we cannot directly modify components like `PositionComponent`, `RotationComponent`, `BoundingBoxComponent`, etc.
Instead we have to modify the `PhysicsComponent`.
Say you want to move a physics supported entity.
You can do that as follows:

```java
PhysicsComponent physics = entity.getComponent(PhysicsComponent.class);
physics.setLinearVelocity(10, 0);
```

This will set entity's velocity to `10` pixels per second.
You can use meters with the following call:

```java
physics.setBodyLinearVelocity(10, 0);
```

Here `10` means meters.
To get a better feel for the API have a look at this [sample](https://github.com/AlmasB/FXGL/blob/master/samples/s10realphysics/RealPhysicsSample.java).
It shows the basic code you are likely to need in your physics based games.

## Using FXGL Physics

FXGL comes with its own collision detection and reporting mechanism.
It is not the most performant detection but it should be sufficient for most use cases.
The API is easy to work with and provides means of identifying when the collision started, when the collision ended and whether the collision is still happening.
See [this sample](https://github.com/AlmasB/FXGL/blob/master/fxgl-samples/src/main/java/s04physics/PhysicsSample.java#L119) for info on usage.

## Repositioning an Entity with Physics

When an entity has a physics component, it is a part of the physics world.
Hence, we cannot use `setPosition` to changes the entity's position.
Instead, we can use:

```
entity.getComponent(PhysicsComponent.class).reposition(...);
```