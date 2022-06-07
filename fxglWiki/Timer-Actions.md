In games you often want to run an action after some period of time or at some interval.
For example, you can use the following to schedule some action to run only once after 1 second.
The action will run on the JavaFX Thread.

```java
getMasterTimer().runOnceAfter(() -> {
    // code to run once after 1 second
}, Duration.seconds(1));
```

If you want something to run constantly, you can use:

```java
getMasterTimer().runAtInterval(() -> {
    // code to run every 300 ms
}, Duration.millis(300));
```

Note that these actions are controlled by the `PLAY` state timer,
which means if your game is paused then the timer is also paused.
Each state (`GAME_MENU`, `MAIN_MENU`, `PAUSED`, etc) has its own timer, so you can choose the one you want.

Once you schedule an action, the reference to that action is returned.

```java
TimerAction timerAction = getMasterTimer().runAtInterval(() -> {
    // ...
}, Duration.seconds(0.5));
```

You can pause and resume a timer action when needed.

```java
timerAction.pause();
timerAction.resume();
```

Finally, if you no longer need the action, you can expire it.

```java
timerAction.expire();
```