## Formats

The following formats are supported by FXGL:

* `.tmx` - Tiled Map Editor
* `.txt` - 2d grid of data, where each character (symbol) is mapped to an entity

## Tiled Map Editor

A video tutorial is available on [YouTube](https://www.youtube.com/watch?v=37wfF9GW1vQ).

The editor can downloaded for free from [here](http://www.mapeditor.org/).
Note: make sure to "embed tilesets" into maps.
Given a `.tmx` file, say `mario1.tmx` from Tiled Map Editor, you can parse it as follows:

```java
FXGL.setLevelFromMap("mario1.tmx");
```

The objects from the file will be parsed using their type.
The parser will expect an `EntityFactory` with `@Spawns` annotation for each of the object types.
So, if you have an object with type `Platform` in your `mario1.tmx`, then the factory's

```java
@Spawns("Platform")
public Entity newPlatform(SpawnData data) {
    return ...
}
```

will be called.



## Text Format

The corresponding parser is `TextLevelParser`.
There are two ways to set up level parsing.
The recommended approach is to create an entity factory class.
If you are developing a game called RocketPower, then `RocketPowerFactory` is the recommended name for that class.

```java
public class RocketPowerFactory implements EntityFactory {

}
```

Generally, this is going to be the only class responsible for creating entities in your game.
Now, imagine you have a level file called `level1.txt`, where you have a bunch of characters that represent level data. Say you use `P` for platforms. Let's create a method in the class above that will take care of platform creation.

```java
@Spawns('P')
public Entity newPlatform(SpawnData data) {
    Entity platform = ...
    return platform;
}
```

First of all, the annotation `Spawns` signifies that this creation method will be called when `P` is encountered in the level data. The name of the method can be anything, however the required signature is as follows: `public Entity ANY_METHOD_NAME(SpawnData data)`.
The `data` object contains everything required to set up an entity before spawning it.

Finally, we just need to wire everything together.

```java
@Override
public void initGame() {
// TODO: update to 11
    TextLevelParser parser = new TextLevelParser(new RocketPowerFactory());
    Level level = parser.parse("levels/level1.txt");
    getGameWorld().setLevel(level);
}
```

For each character found in the text level file the parser will ask our factory to create an entity.
That's level parsing done! Sample code can be found [here](https://github.com/AlmasB/FXGL/blob/master/fxgl-samples/src/main/java/s06gameplay/levelparsing/LevelParsingFactorySample.java).

## Spawning Entities Using Factories

Let's go back to our platform spawning method and add the `Spawns` annotation:

```java
@Spawns("Platform")
public Entity newPlatform(SpawnData data) {
    Entity platform = ...
    return platform;
}
```

Then, we can set the main factory to our game world as follows (in `initGame()`):

```java
getGameWorld().addEntityFactory(new RocketPowerFactory());
```

Finally, we can spawn a new platform any time we want by calling:

```java
getGameWorld().spawn("Platform", x, y);
```