There are multiple ways of adding AI to your enemy entities. Whilst all are valid ways to adding AI, certain approaches are more suitable based on your use case.

## AI via Component (AI has 1 or 2 simple behaviors)

The most basic AI can be added by attaching a `Component` to an entity. An example of a simple "sensor" AI as follows:

```java
public class SensorComponent extends Component {
    @Override
    public void onUpdate(double tpf) {
        Entity player = ...
        if (getEntity().distance(player) < 100) {
            // constantly signal other AI that player is close
        } else {
            // don't signal
        }
    }
}
```

## AI via StateComponent (AI has 3+ different states)

First, add `StateComponent` to your entity. Next, implement your states:

```
private EntityState GUARD = new EntityState() {
    @Override
    protected void onUpdate(double tpf) {

    }
};
```

## AI via ActionComponent

TODO

## AI via Behavior Tree

TODO

## AI via Goal Oriented Action Planning (GOAP)

TODO