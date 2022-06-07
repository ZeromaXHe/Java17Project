## Using Maven / Gradle

The easiest way to get FXGL into your projects is via Maven or Gradle.
Coordinates are on the [main page](https://github.com/AlmasB/FXGL#maven).

* [IntelliJ YouTube](https://youtu.be/LhmlFC6KE2Q)

## Using uber-jar

You can get the latest uber-jar from [Releases](https://github.com/AlmasB/FXGL/releases).
If you have never used an external library before, you can follow tutorials for:
* [Eclipse](https://www.youtube.com/watch?v=2kLIXDhEGo0) (old)
* [IntelliJ](https://youtu.be/LhmlFC6KE2Q)

## Version Check

When using `Developer` or `Debug` application mode, FXGL will contact the server to see if a newer version is available (by default every 7 days).
It is recommended that you use the latest version.
From `0.4.0` FXGL adheres to the following versioning scheme:

* all versions `0.4.x` (where `x` is between 0 and 9) are _mostly_ backwards API compatible. This means you can upgrade from any `0.4.x` to any `0.4.y` (`y > x`), from any `0.5.x` to any `0.5.y`, and so on with minimum changes.
* all _major_ API breaking changes will be promoted to `0.5.0`, `0.6.0`, and so on.