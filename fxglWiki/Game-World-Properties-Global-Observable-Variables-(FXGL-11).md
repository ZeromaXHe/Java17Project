You can declare game vars in your application class as follows:

```
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        // this creates an observable integer variable with value 3
        vars.put("lives", 3);
    }
```

```
PropertyMap state = FXGL.getWorldProperties();
```

There are five types of used values:
* `boolean`
* `int`
* `double`
* `String`
* `Object` (anything not from above falls under this category)

Each value has a name associated with it.
A raw value can be accessed by:

```
int lives = state.getInt("lives");
```

Each value is backed up by a JavaFX `Property`.
This means you can listen for changes and you can also bind to the properties for auto-updates.
A property value can be accessed by:

```
IntegerProperty livesProperty = state.intProperty("lives");
```

Finally, you can edit the values by using:

```
state.setValue("lives", 5);
```