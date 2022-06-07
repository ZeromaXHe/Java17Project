## Achievements

To register an achievement, see below:

```
@Override
protected void initSettings(GameSettings settings) {
                                            // achievement name, description, variable name to track, variable value
    settings.getAchievements().add(new Achievement("See the world", "Move 600 pixels", "pixelsMoved", 600));
    settings.getAchievements().add(new Achievement("Killer", "Kill 3 enemies", "enemiesKilled", 3));
    // ... other settings
}
```

The first achievement above says, when a game state variable named `pixelsMoved` is greater or equal to `600`, then the `See the world` achievement is unlocked. You can listen for unlocked achievement events, as follows:

```
FXGL.getEventBus().addEventHandler(AchievementEvent.ACHIEVED, e -> {
    // do something with e.getAchievement()
});
```

## Notifications

Whenever you need to notify the player with a message, you can use the following:

```java
String message = ...
FXGL.getNotificationService().pushNotification(message);
```