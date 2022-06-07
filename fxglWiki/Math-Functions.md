All commonly used math methods are located in `FXGLMath`.
For example, if you have an RPG where on each attack there is a 10% chance to strike critically, you could do something like this:

```java
float chance = 0.1f; // aka 10%
if (FXGLMath.randomBoolean(chance)) {
    strikeCritical();
} else {
    strikeNormal();
}
```