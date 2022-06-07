This is a quick migration guide to FXGL `0.5.0`.
As per semantics based versioning scheme, future `0.5.x` releases will require from only minor to none code changes.

## Control to Component

Control and Component concepts were merged into a single Component notion.
This is similar to the Unity Entity-Component model.
Everything related controls has been changed / renamed to components, so `PlayerControl` becomes `PlayerComponent`, `getControl` becomes `getComponent`.
This is done to simplify API and remove duplicate code.
For broken imports, the easiest refactor is to:

1. remove the import,
2. rename xxxControl to xxxComponent, and
3. let IDE find the import.

You can read more in [[Entity-Component]].

## Package / module updates

FXGL now consists of fxgl-base and fxgl modules, where the base keeps code related only to the engine core.
Hence, it's easier to test and develop.
The code in the fxgl module will have an "experimental" status and will eventually either get added to fxgl-base or removed for lack of use case.
Non-core API has been moved to `com.almasb.fxgl.extra.*`. For example, `ExpireCleanComponent` is now at `com.almasb.fxgl.extra.entity.components.ExpireCleanComponent;`.

## Removed usage of class-level annotations, e.g. @SetEntityFactory, @SetGameConfig

Instead one should manually add entity factory: `getGameWorld().addEntityFactory( ... );` and manually set config class: `settings.setConfigClass(MyGameConfig.class);`
This is done to allow easy mobile/web support without code change, as classpath scanning is non-trivial on these platforms.

## AnimatedTexture

The previous tentative API (`setAnimationChannel`) has been updated to `play` and `loop`, to better facilitate state based implementations.
This means existing code that used `set` in a loop will not behave correctly when replaced with `play` or `loop` as these have different semantics.
In particular, with `loop`, one can call this when entering a looping state, e.g. IDLE, and call `play` to play a channel only once, e.g. in ATTACK state.
A reasonably complex example can be seen [here](https://github.com/AlmasB/FXGL/blob/master/fxgl-samples/src/main/java/sandbox/robots/anim/RobotComponent.java), which shows usage of animations and states.