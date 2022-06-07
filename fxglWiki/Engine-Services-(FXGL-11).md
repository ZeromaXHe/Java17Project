Page author: https://github.com/jo372

An `EngineService` is an engine unit of code that hooks into the engine's lifecycle. The following methods allow providing custom callbacks.

```
abstract class EngineService : Updatable, SerializableType {

    /**
     * Called during the engine initialization phase, after
     * all services were added and dependencies marked with [Inject] injected.
     * This is called on a background thread.
     */
    open fun onInit() {}

    /**
     * Called when the engine is fully initialized and just before the main loop.
     * This occurs once per application lifetime.
     * This is called on a JavaFX thread.
     */
    open fun onMainLoopStarting() { }

    /**
     * Called when initGame(), initPhysics(), initUI() all completed and
     * the game is ready to be played.
     * This is called on a background thread.
     */
    open fun onGameReady(vars: PropertyMap) { }

    /**
     * Called on a JavaFX thread at each engine tick in any scene.
     */
    override fun onUpdate(tpf: Double) { }

    /**
     * Called on a JavaFX thread at each engine tick _only_ in game scene.
     */
    open fun onGameUpdate(tpf: Double) { }

    /**
     * Called just before the engine exits and the application shuts down.
     */
    open fun onExit() { }

    /**
     * Called just before the main loop is paused.
     */
    open fun onMainLoopPausing() { }

    /**
     * Called just after the main loop is resumed.
     */
    open fun onMainLoopResumed() { }

    override fun write(bundle: Bundle) { }

    override fun read(bundle: Bundle) { }
}
```

## Built-in Engine Services

**FXGLAssetLoaderService**

Handles all resource (asset) loading operations.

**FXGLApplication.GameApplicationService**

A high-level service that provides a bridge between the engine and the game.

**FXGLDialogService**

A Dialog Service allows you to display text to the user, including confirmation and input dialog boxes.

**IOTaskExecutorService**

The IOTaskExecutorService allows you run IO tasks synchronously and asyncronously.

**FileSystemService**

The File System Service is a standard IO wrapper that allows you to read, write and check the existence of directories and files.

**LocalizationService**

Provides functions to localize Strings to the given Language using String or StringBinding. Also supports lazy loading of language data.

**SystemBundleService**

It saves FXGL system data into a file. 

**SaveLoadService**

Responsible for save/load operations to/from data files.  Also responsible for write/read IO operations of save files.

**FXGLUIFactoryServiceProvider**

FXGL provider of UI factory service.

**FXGLDialogFactoryServiceProvider**

Generates User Interface for Dialog Boxes.

**AudioPlayer**

General audio player service that supports playback of sound and music objects.  It can also control volume of both.

**NotificationServiceProvider**

Shows Notifications to the Player

**AchievementService**

Responsible for registering and updating achievements.  
Achievements can only be registered via Settings, before the engine starts.  

**CutsceneService**

Manages and plays cutscenes

**MiniGameService**

Manages and plays minigames

**NetService**

All operations that can be performed via networking.

**UpdaterService**

 Checks if there is a newer version of FXGL than the one being used.
 
**DevService**

Provides developer options when the application is in DEVELOPER or DEBUG modes.  
In RELEASE mode all public functions are NO-OP and return immediately.

**MultiplayerService**

Provides entity, input, event replication on a remote machine.