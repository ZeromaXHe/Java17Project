Game state is nothing more (at the moment) than a bunch of observable values that affect the game.
A reference to `GameState` can be obtained as follows:

```java
GameState state = getGameState();
```

There are five types of used values:
* `boolean`
* `int`
* `double`
* `String`
* `Object` (anything not from above falls under this category)

Each value has a name associated with it.
A raw value can be accessed by:

```java
int lives = state.getInt("lives");
```

Each value is backed up by a JavaFX `Property`.
This means you can listen for changes and you can also bind to the properties for auto-updates.
A property value can be accessed by:

```java
IntegerProperty livesProperty = state.intProperty("lives");
```

Finally, you can edit the values by using:

```java
state.setValue("lives", 5);
```