FXGL provides a dialogue editor, which can be downloaded from [Releases](https://github.com/AlmasB/FXGL/releases).

## Dialogue Node Types

...

### Function

When the dialogue system encounters the `FUNCTION` node, it will perform the following actions in order:

* If of this form `varName = varValue`, then: if a variable exists locally or globally (in this order), it will be set to the new value. Otherwise, a new local variable is created.
* If using `$varName`, then: the varName will be replaced by varValue (first checking local vars, then global vars).
* Call `FunctionCallHandler::handle`, which is no-op by default, but can be set by the user.

### Branch

TODO:

## Cutscenes

Cutscenes are defined in a .txt file with the following format:

```
// comments begin like in code
# also a comment

// first person talking (creates a new speaker with id = 1, you can create new speakers at any point)
1.name = Player Name

// second person talking
2.name = 2nd person talking

// will be searched for in assets/textures/
1.image = fileName.png

// no image name means "do not use portrait"
2.image = 

// speaker "1" is talking, name and image are parsed from above
1: Hello, how are you today?
2: I'm good, thanks, this is an example cutscene line.
1: Got it!

// you can also change name and image mid-cutscene

2.name = NewName

2: Hi, my name is different now!
```