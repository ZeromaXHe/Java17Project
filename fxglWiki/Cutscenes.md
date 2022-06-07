Currently there are two types of cutscenes: RPG (`.js`) and JRPG (`.txt`).

## RPG

The data is stored in a JavaScript source file.
This can be used when there is a choice of lines to say, e.g. a dynamic conversation (think SW:KOTOR).

TODO

## JRPG

This type is purely a conversation between two characters, e.g. player and NPC.
The data is stored in a plain text format:

```
Player: Hi!
NPC: Hello!
Player: How are you?
NPC: I'm fine. Bye.
```

Each line consists of line **owner**, a **colon** and the **line** itself, in this order.
This can be used for storytelling.