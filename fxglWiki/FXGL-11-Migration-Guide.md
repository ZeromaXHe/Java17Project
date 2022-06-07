* Every `getXXXX()` has moved to `FXGL.*`, so `getInput()` is now `FXGL.getInput()`. This is to unify all `get` calls and let the IDE help you find classes in FXGL.

## Modularity

You can use FXGL 11 with both modular and non-modular Java 11 projects. For non-modular simply add the library and you are good to go.

For modular, please define `module-info.java` as follows:

```
open module app.name {
    requires com.almasb.fxgl.all;
}
```