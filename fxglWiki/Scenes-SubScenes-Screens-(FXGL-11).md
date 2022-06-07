When you have multiple "states" in your game: open-world movement state, in-game battle state, shop state, etc., you want to use a `SubScene`.

A reasonably simple example can be found in [Breakout](https://github.com/AlmasB/FXGLGames/blob/master/Breakout/src/main/java/com/almasb/fxglgames/breakout/NewLevelSubScene.java), where each new level animation is played in its own subscene.

## Scene Graph Hierarchy
```
MainWindow
-- javafx scene
---- FXGL scene root
------ FXGL scene content root 
-------- (sub scene root)
```