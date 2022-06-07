We keep the JavaFX convention to append `App` after the class containing `main()`.
So if your game name is "MyGame" then the class that has `main()` should be called `MyGameApp`.

## Entity Types

Most games will have different types of entities, e.g. player, bullet, enemy, etc.
The type is likely to be an enum, so it might look like this:

```java
public enum EntityType {
    PLAYER, BULLET, ENEMY
}
```

Instead of `EntityType` you can also put `MyGameNameType` depending on what suits you better.
For example, for a Space Invaders clone I used `SpaceInvadersType` name for the enum.

## Entity Factory

It is recommended that you have a single class responsible for creating entities per collective types in your game.
For example, you might create a class for creating enemies only, then a class to create powerups only, and maybe another class for creating specifically background and decor entities.
Such a class for creating entities is called an entity factory.

```
public class MyBlockFactory implements EntityFactory {

    @Spawns("block")
    public Entity newBlock(SpawnData data) {
        return Entities.builder()
                .from(data)
                .viewFromNode(new Rectangle(70, 70))
                .build();
    }
}
```

You can add a factory to the game world as follows: `getGameWorld().addEntityFactory(new MyBlockFactory());`.
Then you can spawn an entity, using `getGameWorld().spawn("block");`, which in turn calls your factory method annotated with `@Spawns("block")`.

## Game Config

Game config is a file `config.kv` (placed under `/assets/kv/`) that contains values that affect the game / gameplay.
For example, the config may contain values of type `int`, `double`, `boolean` and `String`:

```
maxEnemies = 50
bulletSpeed = 1.5
spawnPowerups = true
defaultName = some string text here
```
You will then need to create a data structure class as follows:

```
public class Config {
    private int maxEnemies;

    public int getMaxEnemies() {
        return maxEnemies;
    }

    ...
}
```

Set your class as follows:

```
settings.setConfigClass(Config.class);
```

Finally, you can access your data object (with all the fields appropriately populated) by calling `(Config) FXGL.getGameConfig()`.

## Level Building

[[Building Levels]] - in short, use .txt for very simple levels, use Tiled editor for anything else.