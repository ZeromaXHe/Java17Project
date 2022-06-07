In FXGL 11 Kotlin main() is slightly different:

```
fun main(args: Array<String>) {
    GameApplication.launch(GeoJumperApp::class.java, args)
}
```

For a full Kotlin game example, please see [OutRun](https://github.com/AlmasB/FXGLGames/tree/master/OutRun)