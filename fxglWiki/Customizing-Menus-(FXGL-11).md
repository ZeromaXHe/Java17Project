## Custom Menus

FXGL uses the factory pattern to deal with menu objects.
To provide your own menu implementation, there are two things of note:

1. Create your menu class that extends `FXGLMenu`
2. Create your factory class that extend `SceneFactory`

### Menu class

See [CustomGameMenuSample](https://github.com/AlmasB/FXGL/blob/dev/fxgl-samples/src/main/java/intermediate/CustomGameMenuSample.java) for a standalone sample.

Also see [FXGLDefaultMenu](https://github.com/AlmasB/FXGL/blob/release/fxgl/src/main/kotlin/com/almasb/fxgl/app/scene/FXGLDefaultMenu.kt) for sample code.

```
public class MyMenu extends FXGLMenu {
    public MyMenu(MenuType type) {
        super(type);

        // code to customize the view of your menu
        getContentRoot().getChildren().addAll( ... );
    }
}
```

### Factory class

The `MenuType.MAIN_MENU` is the one shown at the start of the application (after Intro if present). When the main menu is shown, the game has not been initialized yet. The `MenuType.GAME_MENU` is the one that can be opened during gameplay.

```
public class MySceneFactory extends SceneFactory {

        @Override
        public FXGLMenu newMainMenu() {
            return new MyMenu(MenuType.MAIN_MENU);
        }

        @Override
        public FXGLMenu newGameMenu() {
            return new MyMenu(MenuType.GAME_MENU);
        }
}
```

### Usage of your factory

```
...
settings.setSceneFactory(new MySceneFactory());
```