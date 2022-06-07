Basic 3D support is available starting from version 11.13. The first 3D game demo tutorial uses 11.15 and is on [YouTube](https://youtu.be/qGgLHvsIRds).

This page provides some general tips related to using 3D in FXGL.

## From 2D to 3D

Most 3D related calls will have a `3D` suffix compared to their 2D counterparts. For example:

```
Entity e = ...

// default 2D
e.setPosition(Point2D);

// 3D
e.setPosition3D(Point3D);
```

Here's an example of using an EntityFactory to create an entity:

```
    @Spawns("target")
    public Entity newTarget(SpawnData data) {
        var box = new Box(5, 5, 0.2);
        box.setMaterial(new PhongMaterial(Color.DARKKHAKI));

        return entityBuilder(data)
                .type(FiringRangeEntityType.TARGET)
                .bbox(new HitBox(BoundingShape.box3D(5, 5, 0.2)))
                .view(box)
                .collidable()
                .with(new ExpireCleanComponent(Duration.seconds(6)))
                .build();
    }
```

Note that there is virtually no difference compared to a 2D version, apart from using a 3D shape as view and a 3D bounding shape for the hit box.

The animation system also extends to 3D in an obvious way, simply replace Point2D with Point3D:

```
        Entity e = ...
        animationBuilder()
                .duration(Duration.seconds(0.6))
                .interpolator(Interpolators.ELASTIC.EASE_OUT())
                .scale(e)
                .from(new Point3D(0, 0, 0))
                .to(new Point3D(1, 1, 1))
                .buildAndPlay();
```