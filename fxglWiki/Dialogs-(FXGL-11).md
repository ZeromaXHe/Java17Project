Sometimes we want to show a dialog in our game to obtain user input. There are a few built-in dialogs available, including a "generic" one for custom controls. Furthermore, each built-in dialog can be view-customized by providing own `DialogFactoryService`. For example:

```
settings.setEngineServiceProvider(DialogFactoryService.class, CustomDialogFactoryService.class);
```

If you want a quick sample with all of these dialogs, try out [Dialogs Sample](https://github.com/AlmasB/FXGL/blob/master/fxgl-samples/src/main/java/intermediate/DialogsSample.java). Otherwise, each built-in dialog is given below.

## Message Dialog

```
getDialogService().showMessageBox("This is a simple message box", () -> {
    // code to run after dialog is dismissed
});
```

<img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/dialog_message.png" width="500" />


## Input Dialog

```
getDialogService().showInputBox("This is an input box. You can type stuff...", answer -> {
    System.out.println("You typed: "+ answer);
});
```

<img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/dialog_input.png" width="500" />

## Error Dialog

Use the error dialog to handle expected exceptions and if possible let the user know what steps to take.

```
getDialogService().showErrorBox("This is a scary error box!", () -> {
    // code to run after dialog is dismissed
});
```

<img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/dialog_error.png" width="500" />

## Confirmation Dialog

```
getDialogService().showConfirmationBox("This is a confirmation box. Agree?", answer -> {
    System.out.println("You pressed yes? " + answer);
});
```

<img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/dialog_confirm.png" width="500" />

## Custom Dialog

```
VBox content = new VBox(
        getUIFactoryService().newText("Line 1"),
        getUIFactoryService().newText("Line 2"),
        getAssetLoader().loadTexture("brick.png"),
        getUIFactoryService().newText("Line 3"),
        getUIFactoryService().newText("Line 4")
);

Button btnClose = getUIFactoryService().newButton("Press me to close");
btnClose.setPrefWidth(300);

getDialogService().showBox("This is a customizable box", content, btnClose);
```

<img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/dialog_custom.png" width="500" />