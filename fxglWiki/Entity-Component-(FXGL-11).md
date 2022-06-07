## Entity

Any game object that you can think of (player, coin, power-up, wall, dirt, particle, weapon, etc.) is an entity.
By itself an entity is nothing but a generic object.
Components allow us to shape that entity to be anything we like.

## Component as Data

From Google:
>a part or element of a larger whole.

In our context, a component is a part of an entity.
Strictly speaking, a component is a data structure that includes means of modifying and querying the data.
It is similar to how instance fields are added to the class definition:

```java
class Car {
    int moveSpeed;
    Color color;
}
```

The `Car` class contains an instance field `moveSpeed`, implying that it can move.
Now imagine we also have a player character, that, too, can move.
We can add `moveSpeed` to the `Player` class.
However, there are many other movable game objects.
So maybe we should use inheritance instead and have an abstract class `MovableGameObject`, or better still an interface `Movable`.
That's good and all but what if we want an object that sometimes can move and sometimes cannot, e.g. a car controlled by the player can move, but it cannot move by itself.
We can't just remove an interface and attach it back when we need it.
This is where components come in.
You may have heard about "composition over inheritance".
Well, it is the extreme version of that.
Components allow us to "attach" fields at runtime, how cool is that?
Consider the snippet above, re-written with components.

```java
Entity entity = new Entity();
entity.addComponent(new MoveSpeedComponent());
entity.addComponent(new ColorComponent());

// some time later we can make it immovable
entity.removeComponent(MoveSpeedComponent.class);

// or change a component's value like a normal field
entity.getComponentOptional(ColorComponent.class).ifPresent(color -> {
    color.setValue(Color.RED);
});
```

In addition, this approach isolates each component.
This means we can attach it to _any_ entity and modify it on its own.

Note that it is preferred to add any components that you know an entity is going to possess during entity creation.
Then you can enable / disable components as necessary.
For example, `CollidableComponent` signifies that an entity can collide with something.
Say we don't want our entity to be collidable when created.
Instead of adding the component when the entity becomes collidable, it is easier to add the component at creation and disable it.
By doing so we avoid using `getComponentOptional()` and use `getComponent()`.

## Component as Behavior

We talked about how adding a component is like adding a field.
Well, adding a component is also similar to adding a method.
Components allow us to make an entity do something, essentially defining entity behavior.
Let's say we want an entity to be a lift, so that it can carry the player to the top of a mountain.

```java
entity.addComponent(new LiftComponent());
```

A `Component` has `onUpdate()` method that can be implemented to provide functionality.
This is how a lift component can be potentially implemented (irrelevant code omitted):

```java
public class LiftComponent extends Component {
    @Override
    public void onUpdate(double tpf) {
        if (timer.elapsed(duration)) {
            goingUp = !goingUp;
            timer.capture();
        }

        entity.translateY(goingUp ? -speed * tpf : speed * tpf);
    }
}
```

Certain components will have methods that need to be triggered manually.
For example, a very simple player component:

```java
public class PlayerComponent extends Component {

    // note that this component is injected automatically
    private TransformComponent position;

    private double speed = 0;

    @Override
    public void onUpdate(double tpf) {
        speed = tpf * 60;
    }

    public void up() {
        position.translateY(-5 * speed);
    }

    public void down() {
        position.translateY(5 * speed);
    }

    public void left() {
        position.translateX(-5 * speed);
    }

    public void right() {
        position.translateX(5 * speed);
    }
}
```

So in the input handling section we can bind keys to methods `up`, `down`, etc.
The user will then be able to move the player entity via its component.
The check for required components happens during component addition, so it is important to ensure that all required components are added first.