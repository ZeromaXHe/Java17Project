## Using AnimationBuilder (Example scale animation)

An example with full API is given here, though you may not need to use all of it.

```
Entity e = ...
FXGL.animationBuilder()

        // interpolator drives the animation rate
        .interpolator(Interpolators.ELASTIC.EASE_OUT())

        // common configurations
        .onCycleFinished(() -> System.out.println("Cycle finished"))
        .onFinished(() -> System.out.println("Anim finished"))
        .duration(Duration.seconds(1))
        .repeat(5)
        .scale(e)
        .origin(new Point2D(40, 40))
        .from(new Point2D(1, 1))
        .to(new Point2D(2, 2))
        .buildAndPlay();
```

## Using AnimationBuilder (Example translate animation using Shape)

```
Node node = ...
Entity e = ...

Shape shape = new QuadCurve(double startX, double startY, double controlX, double controlY, double endX, double endY);

// or any other Shape, e.g. CubicCurve(double startX, double startY, double controlX1,
            double controlY1, double controlX2, double controlY2,
            double endX, double endY)

FXGL.animationBuilder()
        .duration(Duration.seconds(5.0))
        .translate(node)
        // or .translate(e)
        .alongPath(shape)
        .buildAndPlay();

```