Once you enable menus, you will need to turn on the saving / loading options:

```
settings.setEnabledMenuItems(EnumSet.allOf(MenuItem.class));
```

The interaction between the user and the game is already present as part of the menu.
So all that remains to be done is to implement what happens when the user saves / loads.

## Saving

The saving process is handled by overriding the following method.
In essence, we capture the state of the game that needs to be saved into a serializable object, say `data`. For example, `com.almasb.fxgl.io.serialization.Bundle`, which acts as a container where you can store other `Bundle` objects.

```java
@Override
public DataFile saveState() {
    
    // save state into `data`
    // you can add a bundle to another bundle if you need more complex data structures
    Bundle data = new Bundle("Root");
    data.put("playerHP", 30.5);
    data.put("playerGold", 100);

    return new DataFile(data);
}
```

Having done that, we wrap `data` with a `DataFile`, which provides meta information like timestamp, version, etc.
This information is then used to find / sort / validate save files during loading.

## Loading

Intuitively, the loading process is the reverse of saving.
The loading is handled by overriding the following method, which takes `DataFile` as a parameter.
As you might've guessed, this is the same object that you returned in the previous method during the saving process.

```java
@Override
public void loadState(DataFile dataFile) {
    // SomeType is the actual type of the object serialized
    // e.g. String, Bundle, HashMap, etc.
    // SomeType data = (SomeType) dataFile.getData();

    // do something with `data`
    Bundle bundle = (Bundle) dataFile.getData();
    double hp = bundle.get("playerHP");
    int gold = bundle.get("playerGold");
}
```

From the `dataFile` object you can readily obtain your `data` and then use it to load the game state.
This is all there is to the saving / loading system.
Everything else will be seamlessly provided by the framework.
A sample that shows how to use the saving / loading system can be seen [here](https://github.com/AlmasB/FXGL/blob/master/fxgl-samples/src/main/java/s05uimenus/SaveSample.java).