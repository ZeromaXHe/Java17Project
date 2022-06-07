## Achievements

In `initAchievements()` you will first need to create and register the achievement.

```java
Achievement a = new Achievement("World Traveller", "Get to the other side of the screen.");
getAchievementManager().registerAchievement(a);
```

This is done quite early during the initialization process so that achievements can be loaded into the menu.
However, at this stage they don't really do anything.
Thereafter, you can bind the achievement to certain trigger value.

```java
GameEntity player = ...
// first, get the achievement we created earlier
Achievement a = getAchievementManager().getAchievementByName("World Traveller");
// then, bind it to trigger, which is a property (x > getWidth())
a.bind(player.getPositionComponent().xProperty().greaterThan(getWidth()));
```

## Notifications

There are currently two service providers (implementations) for the notification service: `FXGLNotificationService` and `SlidingNotificationService`. You can select which provider to use via `settings.addService()`.

Whenever you need to notify the player with a message, you can use the following:

```java
String message = ...
FXGL.getNotificationService().pushNotification(message);
```