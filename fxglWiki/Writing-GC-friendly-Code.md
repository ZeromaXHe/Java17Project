With all modern tools, languages and compilers you would think it is easy to write efficient code with low footprint. Well, it is to a certain extent, but if you want a stable application with high performance and low memory consumption, there are still some things that require programmer's attention when coding.

**Note:** tips, tricks and code snippets used here do not necessarily apply to all types of software development. These primarily target game development and should only be used after profiling your game application. In fact, some of these techniques will produce rather unreadable and ugly-looking code, so be warned. This was written for Java 8 and below. It may or may not apply to Java 9.

Before going deep into the optimized code we are going to briefly talk about why we should bother with garbage collection (GC) in the first place. Then, in later sections we'll have a look at some coding techniques that will help us in minimizing GC.

## Garbage Collection (Theory)

This is not a tutorial on Java memory types, so it'll be brief.
If you are unfamiliar with the terminology, it is best if you do some research in the area first and then come back to the tutorial.
Searching for "Java stack vs heap" should provide you with sufficient material.

Recall, in a Java application whenever the `new` keyword is used, an object will be allocated on the heap memory, provided that there is memory to allocate. Once all references to that object go out of scope, the object is marked for GC and the memory it occupies will be freed on the next GC run. There is nothing inherently wrong with GC or how it works. However, the GC call is a relatively high-cost operation. While this is perfectly fine for normal applications, which typically don't load the CPU all the time, the GC calls might cause **performance** issues in games, which tend to utilize the CPU during most of the application lifetime. Furthermore, real-time games run at 60 frames per second. Say you create `N` objects in the main loop, that is `N * 60` already. Now imagine this is being done in a loop. If the loop cycles `C` times during a frame, then that's already `C * N * 60` per second. As you can see, this can easily get out of hand without proper control. This may not affect desktop games as much as mobile games, but the issue still exists.

Another issue is **memory** consumption. In GC there are two generation (space) types of importance: young and tenured (old). In short, (actually there are a lot of clever things going on inside), objects are created in the young space and when it gets full, minor GC occurs. It cleans up all dead objects in the young space and promotes the rest to tenured. If your game produces a lot of garbage, then the young space gets filled very quickly. While it is being cleaned, some objects which should not live long might still be used and hence will be pushed into the old space. Major GC typically occurs when the old space is full, so theoretically you have a lot of garbage just sitting there and consuming memory. Generally speaking this is not an issue if the amount of RAM is sufficient on the target machine. However, you don't really want your simple game, say pac-man, to consume 1 GB of RAM.

Now back to FXGL. Ideally, we don't want GC to run at all (while the user is playing that is, we still want GC to clean up the previous level and other garbage we don't need). This is possible only theoretically - just don't create new objects and, voila, the problem is gone. In real-world settings we rely on many other libraries. For example, we use JavaFX as our rendering framework. Therefore, any rendering calls that allocate new objects will eventually trigger GC. However, we can still make changes to _our_ code to minimize the overall need for GC.

## Loops

Consider the following for-each loop:

```java
Point2D velocity = ...
for (GameEntity enemy : enemies) {
    enemy.translate(velocity);
}
```

At first glance, it seems absolutely normal. However, [recall](http://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.14.2) that during code generation the for-each loop (for non-arrays) is replaced with the standard iterator loop and after the replacement looks like this:

```java
Point2D velocity = ...
for (Iterator<GameEntity> it = enemies.iterator(); it.hasNext(); ) {
    GameEntity enemy = it.next();
    enemy.translate(velocity);
}
```

Depending on the underlying implementation, `iterator()` is likely to produce a new iterator on each call. Here's how `ArrayList::iterator()` is implemented:

```java
public Iterator<E> iterator() {
    return new Itr();
}
```

As you can see, each time the aforementioned for-each loop is executed, a new iterator object is allocated. Now you know why the contrived (but possible) code in the example below is a nightmare:

```java
for (Entity e1 : entitiesA) {
    for (Entity e2 : entitiesB) {

    }
}
```

Before leaping to the solution, let's consider another example, similar to the original:

```java
Point2D velocity = ...
enemies.forEach(enemy -> {
    enemy.translate(velocity);
});
```

Again, seems very reasonable: we avoided the `iterator()` but still retained the syntactic sugar. Let us check inside `forEach()`. The `ArrayList` data structure is very popular, so I'm going to examine that particular implementation of `List`. 

```java
// irrelevant code omitted
for (int i=0; modCount == expectedModCount && i < size; i++) {
    action.accept(elementData[i]);
}
```

Great, it internally does not use an iterator and there are no object allocations. Not so fast! We forgot to consider that `enemy -> enemy.translate(velocity)` is a stateful lambda operation.

**Note:** recall that [stateful lambda](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html) is one that relies on some global (global here means outside of the lambda scope) state. In the case above, `velocity` is that global state, hence the lambda is stateful.

So `velocity` needs to be captured into the lambda resulting in an anonymous class creation for every element in the loop. If we had a stateless lambda, a singleton (just one object) would have been generated and reused.

The solution for these cases is rather simple - use the old-fashioned indexed loop:

```java
Point2D velocity = ...
for (int i = 0; i < enemies.size(); i++) {
    enemies.get(i).translate(velocity);
}
```

**Note:** another solution would be to use a custom data structure, e.g. [Array](https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/utils/Array.java) from libGDX.

It's worth noting that while this does what we need, there's a change in semantics. In previous cases, adding an element during iteration would have resulted in `ConcurrentModificationException`. With the indexed loop it won't throw the exception and it is possible that the loop is extended. You might even get stuck in the loop if you are constantly adding elements to the list you are iterating over, so be careful.

## Object Reuse

Consider the JavaFX `Point2D` class, which works both as a 2d point and a 2d vector data structure. This is something you are likely to use often in FXGL: whether to compute a trajectory of a projectile or to position an entity. `Point2D`, as many of the JavaFX data structure classes, is immutable. Therefore, to alter its values you must create a new instance:

```java
Point2D pos1 = ...
Point2D pos2 = ...
Point2D projectileVelocity = pos2.subtract(pos1).normalize().multiply(projectileSpeed);
```

Each call in the chain above produces a new instance of `Point2D`. We already know that doing this in an update loop will result in a lot of garbage. We can replace this with a GC-friendly alternative by reusing the same mutable object (`Vec2` from JBox2D):

```java
Vec2 pos1 = ...
Vec2 pos2 = ...
Vec2 projectileVelocity = pos2.subtractLocal(pos1).normalizeLocal().multiplyLocal(projectileSpeed);
```

**Note:** currently there is no need for FXGL to have its own math library since we have access to JBox2D.

First of all, note how the calls have *Local* at the end. This signifies that the object (on which the method is invoked) is going to be modified after the call is finished. The next important thing is that we now lost information about `pos2`, because we reused the object to store some new information. So when you are reusing an object you must take these things into account and ensure that you are not accidentally rewriting data you will need in the future. This is one of the drawbacks of mutable data structures.

## Using Direct References (Object Reuse v.2)

Generally we do not like giving direct access to private fields, especially containers. Instead we return a wrapper container which has all the objects from the original collection. Unfortunately, in order to do this properly we have to create a new object, because if we were to the the same cached container, it would be changed every time the call is made. So most of the time using the direct reference to the container / field with a warning not to modify the object should be sufficient.

```java
// this is the actual list within the world, hence the user must not alter the list
List<Entity> entities = getGameWorld().getEntities();
```

Alternatively, to emphasize that the returned list must not be modified, we could write our own version of the list, say `ReadOnlyList`, that only allows modifications within the package / class. Basically we limit the visibility of state modifying methods and only expose getters / queries. It's worth remembering that using the same list with the same iterator across the system can be dangerous, e.g. unwanted nested iterations. So the library user must always read any available documentation to prevent such situations.

## Object Pool

Instead of creating objects during the game, it is sometimes beneficial to pre-create objects that get used often and store them in a "pool". In a normal application you would only use pools for heavy objects - ones that take a long time to initialize. However, to minimize GC in games we can store pretty much anything. Using the example above:

```java
Vec2 pos1 = ...
Vec2 pos2 = ...
Vec2 projectileVelocity = FXGL.getPooler().get(Vec2.class);
projectileVelocity.set(pos2);

projectileVelocity.subtractLocal(pos1).normalizeLocal().multiplyLocal(projectileSpeed);

// do something with velocity ...
// now return back to pool
FXGL.getPooler().put(projectileVelocity);
```

In short, we get to play the role of JVM: we create an object (not really) and we also destroy it (not really). Yay, it's like C++ memory management all over again, but it _will_ save you from unnecessary GC runs when used properly.

Also check out libGDX wiki on [Memory Management](https://github.com/libgdx/libgdx/wiki/Memory-management), which also has a few examples on how to use pools.

## JavaFX Objects

We can't completely get rid of them as they are the bridge API between FXGL and JavaFX, however, we can:

* Use own version where possible
* Cache objects where possible

In some cases there are no workarounds as most JavaFX data structures are immutable.