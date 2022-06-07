The game world is responsible for adding, updating and removing entities.
It also provides means of querying entities by various criteria.
Entities that are in the game world are considered `active` (this can be checked: `entity.isActive()`).
Once an entity is removed from the world, it is no longer usable and is cleaned up.
The game world instance can be obtained by calling `FXGL.getGameWorld()`.

## Adding & Removing Entities

To add an entity to the game world, so that it becomes part of the game, simply call:

```java
GameWorld world = getGameWorld();
Entity e = ...
world.addEntity(e);
```

You can remove the entity that exists in the game world in a similar way:

```java
world.removeEntity(e);
```

Each entity knows about the world it is attached to.
So, instead of the above, you can call a more convenient version:

```java
e.removeFromWorld();
```

The two calls above are semantically equivalent.

## Queries

The following snippets allow you to request specific entities from the world.
Each query has certain preconditions on the entity components.
For example, if you do a query based on `TypeComponent`, then all entities that don't have that component will be automatically filtered out from the search.
Some queries return a list of entities, others - `Optional<Entity>` signifying that such entity may not exist.

### By Type

Example: we have an enum `EntityType` that contains valid types for entities in your game.

```
List<Entity> enemies = world.getEntitiesByType(EntityType.ENEMY);
```

### By ID

Example: you might allow copies of entities. This is especially useful for RPG type games, where you have lots of duplicates. So maybe we placed multiple forges in the game world, each will have its own unique `IDComponent`. While the name is the same - "Forge", its numeric id is different.

```
Optional<Entity> forge123 = world.getEntityByID("Forge", 123);
```

### By Singleton

Example: suppose we know that there is just one entity of type "PLAYER".

```
Entity player = world.getSingleton(EntityType.PLAYER);
```

### By Random

Example: suppose we know a random enemy.

```
Optional<Entity> enemy = world.getRandom(EntityType.ENEMY);
```

Note: the return type is `Optional` since we may not have any enemies at all.

### By Component

Example: you want entities that have a specific component.

```
List<Entity> entityAbove = world.getEntitiesByComponent(MyComponent.class);
```

### By Range

Example: you want entities in a particular selection box. Useful to select multiple entities, to see if an explosive should destroy objects in a certain range, or to see if the player can interact with an object.

```
List<Entity> entitiesNearby = world.getEntitiesInRange(new Rectangle2D(50, 50, 100, 100));
```

### By Filter

Example: you have your own specifications of entities that you want that do not fall into any of the categories above.

```
List<Entity> items = world.getEntitiesFiltered(e -> e.getInt("hp") == 10);
```

## Performance

You shouldn't experience any problems with up to 1000 `Entity` objects on a single screen.
However, you might get varying results depending on the use case (especially on mobile).
If your game renders or computes less than `60` frames per second, let me know about your use case / example.