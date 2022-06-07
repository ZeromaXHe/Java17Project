### Graphics & UI

* JavaFX 15
* Multi-Layer Rendering
* Parallax Background
* Canvas Particle System
* Dynamic Texture Manipulation
* Sprite Sheet Animations
* Entity / UI Animations (+ interpolators)
* Adaptive Resolution (w and w/o Fullscreen)
* Customizable Intro Video / Animation
* Customizable Main Menu / Game Menu (3 built-in menu styles)
* Customizable UI elements (Dialogs, Bars, Buttons, etc)<br/><img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/progress_bar.jpg" width="512" /><br/><img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/profile.jpg" width="512" />
* Customizable Global CSS for menus / UI elements
* Voronoi Tessellation
* Post-render Effects<br/><img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/fxgl_color.jpg" width="512" />

### Application Framework

* Global / Scoped Event Bus
* Time Management System (in-game time + real time)
* Multithreading / Async Tasks (+kotlin coroutines)
* Console / File Logging
* Performance Monitor + Profiling
* Developer Panel
* Developer Console
* CVar (globally accessible JavaFX properties)
* GC-free Object Pooling
* Annotations Metaprogramming
* Application State Machine
* DSL (domain specific language, available in Java, Kotlin and JavaScript)

### Gameplay

* Full Game Loop
* Entity Component Control System
* Quick Time Events (QTE)<br/><img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/QTE.jpg" width="256" />
* Achievement System
* Notification System<br/><img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/achievement.jpg" width="512" />
* Saving / Loading System
* User Profiles (Save/Load/Restore Game Settings)
* Level Parsers (.txt, .json [Tiled Map Editor Support](http://www.mapeditor.org/) using [jackson](https://github.com/FasterXML/jackson-databind))
* Quest Tracker<br/><img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/quests.jpg" width="512" />

### Physics

* [JBox2D](https://github.com/jbox2d/jbox2d) Integration 
* FXGL Physics (BBox + SAT)
* Unified Collision Handling (JBox2D + FXGL physics)

### AI

* [gdxAI](https://github.com/libgdx/gdx-ai) Integration
* A* Pathfinding
* GOAP (Goal-Oriented Action Planning)
* FSM (Finite State Machine)
* JavaScript Behavior Injections (for entities) + JavaScript FXGL Environment Variables<br/><br/><img src="https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/JS.jpg" width="768" />

### Input

* Key & Mouse Bindings
* Bindings Management (players can rebind actions in the game)
* Full Input Mocking

### I/O

* IO Task Mechanism (exceptionless functional paradigm)
* Networking (TCP and UDP)
* 1-1 Server-Client Support
* Asset Management (`.png`, `.jpg`, `.wav`, `.mp3`, `.txt`, `.json`, `.kv`, `.properties`, `.fxml`, `.css`, `.ttf/.otf`, custom)

If you have a use case (feature) that FXGL doesn't cover, raise an issue, carefully describing the use case.