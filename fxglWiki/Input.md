In this tutorial we go through various approaches that allow you to handle user input.

**Note:** we are only going to cover FXGL input handling. However, if you ever needed for any reason, you can obtain a reference to the underlying JavaFX Scene object: `getGameScene().getRoot().getScene()` and use JavaFX handling.

## FXGL Input Handling Model

Any input (currently keyboard & mouse) is captured by FXGL internally and handled by a UserAction that is specified by the developer. A UserAction has three states:

* BEGIN (1 tick)
* ACTION (1 or more ticks)
* END (1 tick)

Those states correspond to the user pressing, holding and releasing a key or a mouse button. This 3-state system allows us to provide an easy high-level API for handling any type of input. Consider a UserAction for hitting a golf ball:

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

## Using Input Bindings

There might be cases where the code in UserAction grows too big and you want to isolate it in a method.
Perhaps your "action" is based on a method in the first place.
In situations like these you can use an alternative way of input handling.
Consider the example below:

```java
//                                    (action name -> trigger)
input.addInputMapping(new InputMapping("Hit", KeyCode.F));
```

Instead of creating a UserAction, we create an InputMapping from the action name to the key / mouse button that will be the trigger for that action.

**Note:** here action name is the same as the one you'd typically pass into the UserAction constructor.

So, we basically make a promise to the input service by saying that when the user presses that trigger key, there will a method(-s) with that signature to call at runtime.

```java
@OnUserAction(name = "Hit", type = ActionType.ON_ACTION_BEGIN)
public void hitBegin() {
    // action just started (key has just been pressed), play swinging animation
}

@OnUserAction(name = "Hit", type = ActionType.ON_ACTION)
public void hit() {
    // action continues (key is held), increase swing power
}

@OnUserAction(name = "Hit", type = ActionType.ON_ACTION_END)
public void hitEnd() {
    // action finished (key is released), play hitting animation based on swing power
}
```

As you can see the rest is very similar to what we've already used with UserAction. In order for input bindings to work properly, methods must be `public void` and must not take any arguments. The name of the method is irrelevant, but the annotation's `name` field must be exactly the same as the action name you used when registering an input mapping. In this case it's "Hit". Finally the action type marks the method for execution at a specific state: when action begun, during or when ended.

By default, only the App instance is searched for these annotations.
You can call `input.scanForUserActions()` and pass an instance of any other class to search that instance for the mentioned annotations.

## Querying Keyboard/Mouse State

Sometimes it is not convenient to use callbacks: maybe because you don't have access to them, or maybe the code needs to be executed some place else. In those cases you can query various details directly. For example, you can ask the input service whether a key or a mouse button is held (being pressed) at this moment in time:

```java
if (input.isHeld(KeyCode.W)) {
    System.out.println("W is currently held");
}

if (input.isHeld(MouseButton.PRIMARY)) {
    System.out.println("Left mouse button is currently held");
}
```

**Note:** because these keys / buttons are not registered as bindings, they cannot be changed by the user in the control settings. So, this should be used for very special cases and not typical user input.

You can also check the cursor position at any time:

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
```java
getInput().rebind(getInput().getActionByName("Screenshot"), KeyCode.F11);
```

## Things to Remember

* Actions must have unique and meaningful names.
* One action <-> one trigger policy, i.e. an action is bound to a single trigger and only a single action can be bound to a trigger.