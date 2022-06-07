All commonly used math methods are located in `FXGLMath`.  
Page author: [dltt83](https://github.com/dltt83)

----

# Constants
### EPSILON, `FXGLMath.EPSILON`  
Value: `1.1920928955078125E-7`  
Type: `double`
"Close to zero" epsilon value

### PI, `FXGLMath.PI`  
Value: `Math.PI` / `3.141592653589793`  
Type: `double`  
Constant number PI

### 2 PI, `FXGLMath.PI2`  
Value: `PI * 2`  
Type: `double`  
`Math.PI` value multiplied by 2

### HALF PI, `FXGLMath.HALF_PI`  
Value: `PI / 2`  
Type: `double`  
`Math.PI` value divided by 2

### Float PI, `FXGLMath.PI_F`  
Value: `(float) Math.PI` / `3.141592653589793`  
Type: `float`  
Float version of `Math.PI` value

### Float PI2, `FXGLMath.PI2_F`  
Value: `(float) Math.PI * 2`  
Type: `float`  
Float version of `PI2` value

### Float HALF_PI, `FXGLMath.HALF_PI_F`  
Value: `PI_F / 2`  
Type: `float`
Float version of `HALF_PI` value

### Euler, `FXGLMath.E`  
Value: `Math.E` / `2.718281828459045`  
Type: `double`  
Euler's number for natural base logarithms etc

----

# Functions
## Trigonometric

### sine, `FXGLMath.sin()`  
Parameters: `double radians`  
Returns: `double`  
Returns sine value for given radians
```java
sin(0.0); //@return 0.0
sin(HALF_PI); //@return 1.0
sin(PI / 4); //@return 0.7072423542137346
```

### cosine, `FXGLMath.cos()`
Parameters: `double radians`  
Returns: `double`  
Returns cosine value for given radians
```java
cos(0.0); //@return 1.0
cos(HALF_PI); //@return 1.2246467991473532E-16 (almost 0 due to inaccuracies)
cos(PI / 4); //@return 0.7069711821610654
```

### degrees sine, `FXGLMath.sinDeg()`  
Parameters: `double degrees`  
Returns: `double`  
Returns sine value for given degrees
```java
sin(0); //@return 0.0
sin(90); //@return 1.0
sin(45); //@return 0.7072423542137346
```

### degrees cosine, `FXGLMath.cosDeg()`
Parameters: `double degrees`  
Returns: `double`  
Returns cosine value for given degrees
```java
cos(0); //@return 1.0
cos(90); //@return 1.2246467991473532E-16 (almost 0 due to inaccuracies)
cos(45); //@return 0.7069711821610654
```

### sine, `FXGLMath.sinF()`  
Parameters: `double radians`  
Returns: `float`  
Returns sine value as float for given radians
```java
sin(0.0); //@return 0.0
sin(HALF_PI); //@return 1.0
sin(PI / 4); //@return 0.7072423542137346
```

### cosine, `FXGLMath.cosF()`
Parameters: `double radians`  
Returns: `float`  
Returns cosine value as float for given radians
```java
cos(0.0); //@return 1.0
cos(HALF_PI); //@return 1.2246467991473532E-16 (almost 0 due to inaccuracies)
cos(PI / 4); //@return 0.7069711821610654
```

### degrees sine, `FXGLMath.sinDegF()`  
Parameters: `double degrees`  
Returns: `float`  
Returns sine value as float for given degrees
```java
sin(0); //@return 0.0
sin(90); //@return 1.0
sin(45); //@return 0.7072423542137346
```

### degrees cosine, `FXGLMath.cosDegF()`
Parameters: `double degrees`  
Returns: `float`  
Returns cosine value as float for given degrees
```java
cos(0); //@return 1.0
cos(90); //@return 1.2246467991473532E-16 (almost 0 due to inaccuracies)
cos(45); //@return 0.7069711821610654
```

### convert to degrees, `FXGLMath.toDegrees()`
Parameters: `double radians`  
Returns `double`  
Returns value in degrees given in radians
```java
toDegrees(PI); //@return 180.0
toDegrees(HALF_PI); //@return 90.0
```

### convert to radians, `FXGLMath.toRadians()`
Parameters: `double radians`  
Returns `double`  
Returns value in degrees given in radians
```java
toDegrees(180); //@return 3.141592653589793
toDegrees(90); //@return 1.5707963267948966
```

### 2-argument arctangent, `FXGLMath.atan2()`
Parameters: `double y`, `double x`  
Returns: `double`  
Gives 2-argument arctangent for given x and y values
```java
atan2(1, 1); //@return 0.7895463267948966
atan2(1, 3); //@return 0.3232758620689655
```

### 2-argument arctangent in degrees, `FXGLMath.atan2Deg()`
Parameters: `double y`, `double x`  
Returns: `double`  
Gives 2-argument arctangent in degrees for given x and y values
```java
atan2Deg(1, 1); //@return 45.23767225540443
atan2Deg(1, 3); //@return 18.52234251500506
```

----

## Random

### Set new random, `FXGLMath.setRandom()`
Parameters: `Random random`   
Returns: `void`   
Sets new value for `FXGLMath.random`  

### Get random, `FXGLMath.getRandom()`
Returns: `Random`  
Returns current value of `FXGLMath.random`  

### Get random from seed, `FXGLMath.getRandom()`
Parameters: `long seed`  
Returns: `Random`  
Returns random value from seed

### Random in range, `FXGLMath.random()`
Parameters: `int start`, `int end`  
Returns: `int`  
Returns random int between `start` (inclusive) and `end` (inclusive)
```java
random(1, 10); //@return random number between 1 and 10
```

### Long random in range, `FXGLMath.random()`
Parameters: `long start`, `long end`  
Returns: `long`  
Returns random long between `start` (inclusive) and `end` (inclusive)

### Double random in range, `FXGLMath.random()`
Parameters: `double start`, `double end`  
Returns: `double`  
Returns random double between `start` (inclusive) and `end` (exclusive)

### Random double, `FXGLMath.randomDouble()`
Returns: `double`  
Returns random double between 0.0 (inclusive) and 1.0 (exclusive)

### Random float, `FXGLMath.randomFloat()`
Returns: `float`  
Returns random float between 0.0 (inclusive) and 1.0 (exclusive)

### Random boolean, `FXGLMath.randomBoolean()`
Returns: `boolean`  
Returns random boolean value

### Random boolean in chance, `FXGLMath.randomBoolean()`
Parameters: `double chance`  
Returns: `boolean`  
Returns boolean true if random double is less than chance given
```java
// 10% chance of crit strike
float chance = 0.1f; // 10%
if (FXGLMath.randomBoolean(chance)) {
    strikeCritical();
} else {
    strikeNormal();
}
```

### Random sign, `FXGLMath.randomSign()`
Returns: `int`  
Returns randomly either 1 or -1  
```java
// move randomly either left or right
object.moveX(randomSign() * distance)
```

### Random 2d point, `FXGLMath.randomPoint()`
Parameters: `Rectangle2D bounds`  
Returns: `Point2D`  
returns random point in 2d space bound by `bounds` rectangle  
given by minX <= x < maxX, minY <= y < maxY

### Random vector of unit length, `FXGLMath.randomVec2()`
Returns: `Vec2`  
returns random vector as vec2
```java
// move randomly in any direction
object.move(randomVec2() * distance)
```

### Random point unit distance, `FXGLMath.randomPoint2D()`
Returns: `Point2D`  
returns random unit length vector as point2d

### Random color, `FXGLMath.randomColor()`
Returns: `Color`  
returns random color using random doubles as RGB values

### Array random element, `FXGLMath.random()`
Parameters: `T[] array`  
Returns: `Optional<T>`  
returns random element of array or `Optional.empty()` if array is empty

### List random element, `FXGLMath.random()`
Parameters: `List<T> list`   
Returns: `Optional<T>`    
returns random element of list or `Optional.empty()` if list is empty

----

## Other

### Square root, `FXGLMath.sqrt()`
Parameters: `double x`  
Returns: `double`  
returns square root of given x value

### Square root float, `FXGLMath.sqrtF()`
Parameters: `float x`  
Returns: `float`  
returns square root of given x value as float

### Map value, `FXGLMath.map()`
Parameters: `double value`, `double currentRangeStart`, `double currentRangeStop`, `double targetRangeStart`, `double targetRangeStop`  
Returns: `double`  
Maps current value from current range onto target range as double
```java
map(5, 0, 10, 0, 20) //@return 10.0
map(5, 0, 10, 10, 20) //@return 15.0
```

### Clamp value, `FXGLMath.clamp()`
Parameters: `float a`, `float low`, `float high`  
Returns: `float`  
returns closest value to `a` which is within `low` and `high`

### Floor round, `FXGLMath.floor()`
Parameters: `float x`  
Returns: `int`  
Returns `x` rounded down to nearest int

### Absolute value, `FXGLMath.abs()`
Parameters: `float value`  
Returns: `float`  
Returns absolute value of given float value

### Absolute value double, `FXGLMath.abs()`
Parameters: `double value`  
Returns: `double`  
Returns absolute value of given double value

### Minimum value, `FXGLMath.min()`
Parameters: `float a`, `float b`  
Return: `float`  
Returns smallest out of given float values `a` and `b`  
no NaN check

### Maximum value, `FXGLMath.max()`
Parameters: `float a`, `float b`  
Return: `float`    
Returns largest out of given float values `a` and `b`    
no NaN check

### Quad Bezier curve (3 points), `FXGLMath.bezier()`
Parameters: `Point2D p1`, `Point2D p2`, `Point2D p3`, `double t`  
Returns: `Point2D`  
Returns point at `t` on bezier curve defined by points `p1`, `p2`, `p3`

### Cubic Bezier curve (4 points), `FXGLMath.bezier()`
Parameters: `Point2D p1`, `Point2D p2`, `Point2D p3`, `Point2D p4`, `double t`  
Returns: `Point2D`  
Returns point at `t` on bezier curve defined by points `p1`, `p2`, `p3`, `p4`

### 1D Perlin noise, `FXGLMath.noise1D()`
Parameters: `double t`  
Returns: `double`  
returns value of perlin noise at point given by `t`

### 2D simplex noise, `FXGLMath.noise2D()`
Parameters: `double x`, `double y`  
Returns: `double`  
returns value of simplex noise at point given by `x` and `y`

### 3D simplex noise, `FXGLMath.noise3D()`
Parameters: `double x`, `double y`, `double z`  
Returns: `double`  
returns value of simplex noise at point given by `x`, `y` and `z`

### Get noise generator, `FXGLMath.getNoise1DGenerator()`
Parameters: `long seed`  
Returns: `PerlinNoiseGenerator`  
returns value of noise generator

### Distance of 2 rectangles, `FXGLMath.distance()`
Parameters: `Rectangle2D rect1`, `Rectangle2D rect2`  
Returns: double  
returns distance between 2 rectangles `rect1` and `rect2`