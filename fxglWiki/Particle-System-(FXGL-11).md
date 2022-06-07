## Particle System API

You can use the particle system either with entities or in the UI. In both cases, you will need to configure an emitter first and then add the emitter to an appropriate object.

```
var emitter = ParticleEmitters.newExplosionEmitter(300);

emitter.setMaxEmissions(Integer.MAX_VALUE);
emitter.setNumParticles(50);
emitter.setEmissionRate(0.86);
emitter.setSize(1, 24);
emitter.setScaleFunction(i -> FXGLMath.randomPoint2D().multiply(0.01));
emitter.setExpireFunction(i -> Duration.seconds(random(0.25, 2.5)));
emitter.setAccelerationFunction(() -> Point2D.ZERO);
emitter.setVelocityFunction(i -> FXGLMath.randomPoint2D().multiply(random(1, 45)));
```

#### Particle System with Entities

Adding the emitter to an entity:

```
var entity = ...;
entity.addComponent(new ParticleComponent(emitter));
```

#### Particle System with UI

Adding the emitter to the UI:

```
ParticleSystem system = new ParticleSystem();
system.addParticleEmitter(emitter, x, y);

// you can now use system.getPane() as a Node in your scene graph
```

