### Framework (`fxgl`)

* Unified 2D / 3D API
* Desktop (Win, Mac, Linux) + mobile (Android 8+, iOS 11+)
* Asset Management (`.png`, `.jpg`, `.wav`, `.mp3`, `.txt`, `.json`, `.lang`, `.properties`, `.fxml`, `.css`, `.ttf/.otf`, custom)
* Performance Monitor + Profiling
* Developer Panel
* Developer Console
* DSL (domain specific language, available in Java and Kotlin)
* CVar (globally accessible JavaFX properties)
* Scrollable (Parallax) Background
* Adaptive Resolution (w and w/o Fullscreen)
* Customizable Intro Video / Animation
* Customizable Main Menu / Game Menu
* Application State Machine
* User Profiles (Save/Load/Restore Game Settings)
* Variable framerate game loop
* Cross-platform audio support
* Custom 3D shapes and vertex manipulation API

### Scene & UI (`fxgl-ui`)

* JavaFX 16
* Notification System<br/><img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/achievement.jpg" width="256" />
* Customizable UI elements (Dialogs, Bars, Buttons, etc)<br/><img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/progress_bar.jpg" width="256" /><br/><img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/profile.jpg" width="256" />
* Customizable Global CSS for menus / UI elements
* 9-slice UI generation

### Gameplay (`fxgl-gameplay`)

* Narrative dialogue and cutscene system
* Shop and inventory API
* Quick Time Events (QTE)<br/><img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/QTE.jpg" width="256" />
* Achievement System

### Game Objects and Physics (`fxgl-entity`)

* Entity-Component model
* [JBox2D](https://github.com/jbox2d/jbox2d) integration for rigid body dynamics
* FXGL light-weight physics (BBox + SAT)
* Unified Collision Handling (JBox2D + FXGL physics)
* A* Pathfinding
* Procedural world generation
* SenseAI (hearing)
* Level Parsers (.txt, .tmx [Tiled Map Editor Support](http://www.mapeditor.org/))
* Particle System

### Input (`fxgl-input`)

* Key, mouse, controller, touchscreen Bindings
* Bindings management (players can rebind actions in the game)
* Input mocking

### I/O (`fxgl-io`)

* Networking (TCP and UDP)
* 1-1 and 1-n Server-Client Support
* Saving / Loading System
* Cross-platform (desktop + mobile) file system access

### Common (`fxgl-core`)

* Global / Scoped Event Bus
* FSM (Finite State Machine)
* Animations + interpolators
* IO Task Mechanism (exceptionless functional paradigm)
* Time Management System (in-game time + real time)
* Multithreading / Async Tasks
* Console / File Logging
* Post-render Effects<br/><img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/fxgl_color.jpg" width="256" />
* Dynamic Texture Manipulation
* Sprite Sheet Animations
* GC-free Object Pooling
* Serialization