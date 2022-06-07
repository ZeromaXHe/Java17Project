The recommended approach is to use the Maven directory structure alongside Maven/Gradle. The source root is **"src/main/java"** and assets are placed in **"src/main/resources"**. This allows easy packaging and deployment, as all assets packaged into jar/native image will continue loading with exactly the same code.

## IntelliJ IDEA (with Maven / Gradle)

![Screenshot](https://raw.githubusercontent.com/AlmasB/git-server/master/storage/images/directory_structure.jpg)

## Structure (Uber jar)

**NOTE:** this structure is **only** for uber-jars.

project directory (typically project name)<br />
&nbsp;&nbsp;&nbsp;&nbsp;src (source code directory)<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;assets<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;textures (image files ".png", ".jpg")<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sounds (sound files ".wav")<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;music (music files ".mp3")<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;text (text files ".txt")<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ui/css (stylesheets for customizing UI elements)<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ui/fonts (fonts ".ttf", ".otf")<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(your packages / code)