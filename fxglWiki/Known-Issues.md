## Class file has wrong version (55.0 / 52.0)

If you use Java 8-10, you need FXGL 0.5.4. If you use Java 11, you need FXGL 11+ and let the maven-compiler know:

```
            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.0</version>
                <configuration>
                    <release>11</release>
                </configuration>
            </plugin>
```

## Linux

If you are running into a `MediaException` that says `Could not create player!`, then it is highly likely that you are missing `libavcodec.so` and `libavformat.so` or their versions are not supported. This can be fixed by following the instructions [here](https://github.com/AlmasB/FXGLGames/issues/1).

## Ubuntu 16.04 AnimationTimer runs too fast

Run the JVM with `-Dquantum.mutithreaded=false`

## Q: I get an error `X` when I run my game.

A: FXGL has a relatively robust exception handling mechanism.

<img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/error_reporter.jpg" width="480" height="250" />

First, read what the error message is, then read what the error type is.
The method where the error occurred and the line number in the file are also given.
It is likely that you can fix the issue given that information.
If not, contact details are below.

## Contact Details

Contact details are found [here](https://github.com/AlmasB/FXGL/wiki/Contact)