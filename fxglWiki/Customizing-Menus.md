## Custom Menus

FXGL uses the factory pattern to deal with menu objects.
To provide your own menu implementation, there are two things of note:

1. Create your menu class that extends `FXGLMenu`
2. Create your factory class that extend `SceneFactory`

### Menu class

See [FXGLDefaultMenu](https://github.com/AlmasB/FXGL/blob/master/fxgl/src/main/kotlin/com/almasb/fxgl/app/scene/FXGLDefaultMenu.kt) for sample code.

```
public class MyMenu extends FXGLMenu {
    public MyMenu(MenuType type) {
        super(type);

        // code to customize the view of your menu
    }
}
```

### Factory class

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