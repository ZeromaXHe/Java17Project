This page will familiarize you with what, where, when and how regarding game assets. Most of the asset loading is provided by JavaFX. FXGL simply wraps the API and brings them all together in `AssetLoader`.

## Directory

There is a special directory called `assets` placed under `src` if you are not following the Maven directory structure. If you are, then it is placed under `src/main/resources`. This is necessary for the build process to easily pick up assets and package them when you distribute your game in an executable. This also works when you are developing or debugging your game in the IDE. Check [[Directory Structure]].

## Loading

After obtaining the reference to the asset loader (`getAssetLoader()`) call `load*`, where `*` can be Texture, Music, Sound, etc. First, the loader will check if the asset is in the cache and return it if it is. If the asset was not found in the cache, the asset will be loaded from the file system and at the same time placed in the cache. You can also pre-load the assets before the game starts by calling `cache()` on the loader.

Below you can find the supported types of assets:

**Note:** some assets support more formats than listed here, e.g. Texture can be loaded as `bmp`, however, this is the standardized list and assets are expected to be of that format.

## Textures

Directory: `assets/textures/`  
Format: `jpg`, `png`  
Description: any image you want to display will fall under this category.

## Music

Directory: `assets/music/`  
Format: `mp3`  
Description: long audio files, e.g. background music or recorded dialogues.

## Sounds

Directory: `assets/sounds/`  
Format: `wav`  
Description: very short audio files, e.g. sound effects that commonly occur.

## Text

Directory: `assets/text/`  
Format: `txt`  
Description: anything in textual format, e.g. text dialogues, in-game item names.

## JSON

Directory: `assets/json/`  
Format: `json`  
Description: any valid json data, e.g. Tiled map, some custom format.

## TMX

Directory: `assets/tmx/`  
Format: `tmx`  
Description: Tiled map data.

## Scripts

Directory: `assets/scripts/`  
Format: `js`  
Description: valid scripts that can be run, e.g. AI scripts or behavior, dynamic code snippets.

## Properties

Directory: `assets/properties/`  
Format: `properties`  
Description: essentially Java resource bundle, a collection of simple key values, useful for system configuration.

## KV Files

Directory: `assets/kv/`  
Format: `kv`  
Description: similar to properties but the file gets parsed into your own custom data structure, useful for entity configuration.

## AI Behavior Trees

Directory: `assets/ai/`  
Format: `tree`  
Description: this is a standard gdxAI [Behavior Tree](https://github.com/libgdx/gdx-ai/wiki/Behavior-Trees).

## CSS

Directory: `assets/ui/css/`  
Format: `css`  
Description: CSS files used to style your UI elements.

## Fonts

Directory: `assets/ui/fonts/`  
Format: `ttf`, `otf`  
Description: fonts to be used in UI.

## Icons

Directory: `assets/ui/icons/`  
Format: `jpg`, `png`  
Description: icon images to be used in taskbar or window title.

## Cursors

Directory: `assets/ui/cursors/`  
Format: `jpg`, `png`  
Description: cursor images that can be used to replace default mouse cursor.

## Binary

Directory: `assets/data/`  
Format: anything  
Description: your own asset type that can be serialized with the default Java serialization mechanism.

## Custom Assets

Directory: anywhere under `src` or if using Maven `src/main/resources`  
Format: anything  
Description: your own asset type that is serialized by you. Basically the loader gives you an `InputStream`.