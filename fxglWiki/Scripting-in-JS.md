Typical use case for JavaScript code includes external scripts that can be easily changed at runtime and used in multiple projects. JS code is evaluated by the nashorn engine, so the reader is also referred to this [page](https://docs.oracle.com/javase/8/docs/technotes/guides/scripting/nashorn/api.html), which explains how to use Java code from scripts.

## Entity Behavior

It is possible to implement entity behavior in JavaScript. Essentially JS code is wrapped with a `Control`.
For example, in Java we can add a JS script as follows:

```java
Entity e = ...
e.addControl(new JSControl("gravity.js"));
```

The "gravity.js" may contain something like this:

```javascript
function onUpdate(/*Entity*/ self, /*double*/ tpf) {
    self.setY(self.getY() + 10 * tpf);
}
```

## Accessing the FXGL Infrastructure

```javascript
function someFunction() {
    // FXGL is a globally accessible object that provides the same functionality as it's Java counterpart
    FXGL.getNotificationService().pushNotification(...);

    // APP is another object that can be accessed globally and holds the reference to the GameApplication instance
    var width = APP.getWidth();
}
```
