There are various approaches that allow you to handle user input.

**Note:** we are only going to cover FXGL input handling. However, if you ever needed for any reason, you can obtain a reference to the underlying JavaFX Scene object: `getGameScene().getRoot().getScene()` and use JavaFX handling.

## FXGL Input Handling Model

Any input trigger (keyboard, mouse or virtual) is captured by FXGL internally and handled by a UserAction that is specified by the developer. A UserAction has three states:

* BEGIN (1 tick)
* ACTION (1 or more ticks)
* END (1 tick)

Those states correspond to the user pressing, holding and releasing a trigger. This 3-state system allows us to provide an easy high-level API for handling any type of input. Consider a UserAction for hitting a golf ball:

```java
UserAction hitBall = new UserAction("Hit") {
    @Override
    protected void onActionBegin() {
        // action just started (key has just been pressed), play swinging animation
    }

    @Override
    protected void onAction() {
        // action continues (key is held), increase swing power
    }

    @Override
    protected void onActionEnd() {
        // action finished (key is released), play hitting animation based on swing power
    }
};
```

## Registering User Action

Now that we have created an action `hitBall`, we can ask the input service to register it:

```java
@Override
protected void initInput() {
    Input input = getInput();

    input.addAction(hitBall, KeyCode.F);
}
```

**Note:** it is important that all actions are registered in `initInput()`, as this is called very early on during the FXGL initialization routine, so that menus can correctly display all the existing commands.

The second parameter to `addAction()` is the trigger. The trigger can either be a key or a mouse button.
We say that "action" is bound to "trigger", so in this case the `hitBall` action is bound to the `F` key.

## Using Trigger Listener

You can also listen for generic trigger events. The begin-action-end model is the same as above.

```
getInput().addTriggerListener(new TriggerListener() {
    @Override
    protected void onActionBegin(Trigger trigger) {
        System.out.println("Begin: " + trigger);
    }

    @Override
    protected void onAction(Trigger trigger) {
        System.out.println("On: " + trigger);
    }

    @Override
    protected void onActionEnd(Trigger trigger) {
        System.out.println("End: " + trigger);
    }
});
```

Using the above listener, you can also check which key / button is being pressed in this frame. For example, in `onAction()` you could add:

```
if (trigger.isKey()) {
    var keyTrigger = (KeyTrigger) trigger;

    // key is being pressed now
    var key = keyTrigger.getKey();

} else {
    var mouseTrigger = (MouseTrigger) trigger;

    // btn is being pressed now
    var btn = mouseTrigger.getButton();
}
```

## Querying Mouse State

You can check the cursor position at any time:

```java
Point2D cursorPointInWorld = input.getMouseXYWorld();
Point2D cursorPointInUI = input.getMouseXYUI();
```

As you can see, we can query the cursor position not only in the game world (scene) but also in the UI overlay.
For games where the screen doesn't move the coordinates will be the same.
However, imagine a platformer like Mario, where you move horizontally and the world (camera) moves with you.
Then the cursor in the world might be somewhere like x = 3400, y = 450, while the cursor in the UI coordinates will be x = 400, y = 450.
It's very useful when your game world layer needs to interact with the UI layer.
Say, you pick up a coin and then the coin slowly moves towards the UI object that counts the coins you've collected, subsequently merging with that object to create a slick animation.

## Mocking User Input

Since FXGL has its own layer of input handling mechanism, you can also mock the input.
This is quite helpful in tutorial levels or cinematic actions, i.e. where you need to do something with the player and have the code that does it, but you don't want the user to do it.
Simply call:

```java
// behaves exactly the same as if the user pressed 'W' on the keyboard
input.mockKeyPress(KeyCode.W);

// behaves exactly the same as if the user released 'W' on the keyboard
input.mockKeyRelease(KeyCode.W);
```

Mocking mouse events can be done in a similar way.
It is given that the input bindings, i.e. actions that you've registered, will be fired accordingly.

## Rebinding system actions

You can rebind the screenshot action (or any other) to your own trigger.
In `initInput()`:
```
getInput().rebind(getInput().getActionByName("Screenshot"), KeyCode.F11);
```

## Input Modifiers (CTRL, ALT, SHIFT)

An example:

```
input.addAction(action, keyCode, InputModifier.CTRL);
```

The action will only trigger when the modifier _and_ the key are pressed.

## Key Sequence (Combinations)

```
        Input input = FXGL.getInput();

        var sequence = new InputSequence(KeyCode.Q, KeyCode.W, KeyCode.E, KeyCode.R);

        // the action fires only when the sequence above (Q, W, E, R) is complete
        // useful for input combos
        input.addAction(new UserAction("Print Line") {
            @Override
            protected void onActionBegin() {
                System.out.println("Action Begin");
            }

            @Override
            protected void onAction() {
                System.out.println("On Action");
            }

            @Override
            protected void onActionEnd() {
                System.out.println("Action End");
            }
        }, sequence);
```

## Things to Remember

* Actions must have unique and meaningful names.
* One action <-> one trigger policy, i.e. an action is bound to a single trigger and only a single action can be bound to a trigger.