The established way to communicate between parts of your game is to use an event bus.

## Event Bus

There is a single global event bus that brings all FXGL modules together, including your game. You can obtain the reference to it as follows:

```java
EventBus bus = FXGL.getEventBus();
```

Then you can either add (register) or remove (unregister) a handler for certain event types.

```java
EventHandler<MyGameEvent> handler = event -> {
    // do something with event
};

// start listening for MyGameEvent.ANY and subsequently handling them
getEventBus().addEventHandler(MyGameEvent.ANY, handler);

// when no longer interested in those events or cleaning up
getEventBus().removeEventHandler(MyGameEvent.ANY, handler);
```

## Creating Custom Events

All the events that get passed between the modules are standard JavaFX events.
This means that you can easily create new custom events by simply extending JavaFX `Event` class.

So, let's create our own event.
Imagine that you have a game where the player can pick up various things by going over them (essentially colliding with them).
You might have implemented the collision handler in a different class (which is the preferred way).
However, now you need to communicate this back to the game app class and using events is a very neat way of doing so.
Consider the `PickupEvent` class below:

```java
public class PickupEvent extends Event {

    public static final EventType<PickupEvent> ANY
            = new EventType<>(Event.ANY, "PICKUP_EVENT");

    public static final EventType<PickupEvent> COIN
            = new EventType<>(ANY, "COIN_PICKUP");

    public static final EventType<PickupEvent> POWERUP
            = new EventType<>(ANY, "POWERUP_PICKUP");

    private Entity pickup;

    public PickupEvent(EventType<? extends Event> eventType, Entity pickup) {
        super(eventType);
        this.pickup = pickup;
    }

    public Entity getPickup() {
        return pickup;
    }
}
```

We first extend `Event`, then we define types of our custom event.
This allows us to add a subtype, so to speak, to the event because perhaps we want to know whether it was a coin or a powerup that we picked up.
Of course we could query the pickup itself but with event types we can later make the event bus listen for particular events and filter out those we don't need.
Therefore, it is best to use event types where appropriate.
As you can see, the event object also carries the entity that we picked up, so this demonstrates how you "attach" objects to the event.

## Firing Events

Given an event, you can fire it as easily as:

```java
getEventBus().fireEvent(event);
```

The event will go through the entire FXGL infrastructure, but only event handlers registered for the event type will be notified.

## Firing Events on Condition

We can slightly automate the process of firing events based on certain special conditions.
We can do so by adding an `EventTrigger` to game world.

```java
getEventBus().addEventTrigger(new EventTrigger<EntityEvent>(
    () -> player.getRightX() > enemy.getX(),
    () -> new EntityEvent(Events.PASSED, player, enemy)
));
```

This is a rather contrived example but should give you an idea how triggers work.
We first pass a predicate function that is checked every frame.
If the function returns true, in our case player passes some special enemy, then the trigger produces an event
based on the supplier function.
The event is then fired normally.

**Note:** by default the triggers fire only once. This can be changed in the constructor by passing the number of times to fire, as well as the interval between events.

If an event is quite general, you might do this in the main loop by scanning through all enemies for example.
However, triggers might come in handy when you need to play a cut-scene or fire some special event that may occur only once.