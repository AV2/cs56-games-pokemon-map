cs56-games-pokemon-map
======================

Website:
* <https://brandontran24.github.io/Pokemon-Project/>
* <https://ucsb-cs56-projects.github.io/cs56-games-pokemon-map/>

Javadoc: 
* <https://brandontran24.github.io/Pokemon-Project/docs>
* <https://ucsb-cs56-projects.github.io/cs56-games-pokemon-map/docs>


The goal of the program is to create a 2D Rendering engine in the style of the Pokemon games for Gameboy Advance. The engine will render the exact tileset from the actual games.

Currently, after a massive architectural rewrite, the player can control Professor Oak walking around on a map. It should be relatively easy to add new features in a non-coupling way.

###Features missing due to the rewrite:
1. A full map. There exists infrastructure to load maps from a file, however
2. Pikachu following the player
3. "Events", where the map changes because of player actions


![](http://i.imgur.com/MaKaaHD.png)

project history
===============
```
 W14 | bkiefer13 5pm | mliou, alecharrell |  Pokemon Map Renderer
```

## Project Structure
The source directory has 7 packages (commands, components, framework, graphics, IO, systems, and tests), along with 7 Java files in the main package (edu.ucsb.cs56.S12.sbaldwin.pokemon). The code is organized with the Entity-Component-System design pattern (see http://gameprogrammingpatterns.com/component.html for an overview), where discrete objects in the game world are represented by an Entity, each of which owns a number of Components. Entities and Components have *no* behavior; entities merely own components, and components are bags of data (state). All game behavior goes into Systems, each of which is run once per update. Systems only use and modify components directly related to their domain. For example, the RenderSystem iterates through all entities once per frame, and for each entity that has both a GraphicsComponent and a PositionComponent, draws it to the screen. 



## How to Extract from Bitmaps
In order to get the tileset from the actual games, you need to get the images of the sprites you want. These images must be bmp files; any png file can be converted into bmp files via an online converter. Put the image in the images file, and simply read that image in Renderer.java. When the images are read in, the first two coordinates are the x and y coordinates of the top left corner of the image, and the last two coordinates are the bottom right x and y coordinates of the image(respectively). Harvest textures in Renderer.java, buildings in Building.java and characters in Character.java.

## How to Run
To start the game, use ant run or mvn compile & mvn exec:java. It will compile automatically for you.

## How to Play
To move, use the WASD or Arrow keys. To interact with objects, press the H key (currently does nothing).

## F17 Final Remarks

How the code currently constructs a map:
The code first builds a map of tiles from a loaded text file. Each tile is an individual entity, made up of various components. These tiles are stored in an array of tile arrays in World.java. Next, the world builds up interactable entities--the player, and obstacles such as buildings and trees--which are stored in an arrayList of entities. In the code's current form, these entities must be hard-coded, rather than be loaded from files.   

Possible new features:
Pokemon following the player-character could be re-implemented. Loading new maps upon reaching the edge of the current map could also be added or loading a new map when the player "enters" a building. Could also implement a map editor which is currently at 500 points in issues. I would reccommend doing this for the second half of the project or pushing for this being more points as it would probably require a complete refactoring of code and how the game is built up in World.java. You could also implement a movable camera. You might want to look in the game Alva (in CS56 projects category) and see how that game implements it. 

Current Bugs:
No major bugs. Player is able to move around the map at one tilemove per key stroke. Collision doesn't have any issues.

Refactoring Opportunities:
Map tile parser can be refactored to allow for simpler, easier-to-write map files that can also load buildings/other entities. This also goes with possibly creating a map editor in the game. Definitely a lot of points here.
SystemMessenger could be refactored to allow the code to work better, solve the source of memory leaks. (Unsure if memory leaks are still an issue as of F17.)

Our Advice:
This project is built from a lot of different packages that all work together to allow the game to function. To tackle the code, and get a decent understanding of it, I would start by looking at the entity/component classes. Once you understand the entities, understanding how the world loads becomes easier. After that, figuring out how the rendering and movement systems function becomes possible. Work outwards from there, until you have a good understanding of the whole picture, including how systemMessenger, animation, and I/O works. The main files in this game are World.java and Game.java. This is where everything comes together. This project has a lot to it so definitely familiarize yourself with the code early on. A good place to start might be with adding Pikachu back in. The first thing you implement will probably take the longest as this project has quite a bit of confusing code. Another great place to start would be reorganizing the map and making it "pretty". Pretty easy points but takes a bit of time to change values in Pond_map.txt. 


# W18 final remarks

## What the project does right now:
 Using command "ant run" or "mvn exec:java" to run the project, it generates a pokemon map with player at the middle. The player can move around as long as there is no obstacle. Everything, like a tree, in the map is an entity with compounds, like position compound. The map generator basically reads a txt file from resources, and loads the map according to IDs of entities that assigned at each point. 
 
## Features could be added & possible refactoring
  Pikachu should follow the player as the player moves. Movable camera is pretty feasible as the map generator has been completely rewritten. Also, it will be very cool if player can truly interact with the map. For example, when the player hits the door of a building, he "goes inside" the building as a new map generated. I would recommend the issue #84, refactor into model & view controller pattern for the second part of the quarter, as it requires a solid understanding of the overall structure of the project. Getting this issue done is key to add more tests on the project.
  
## Bugs exit :
 No major bugs. However, lots of classes and methods need to be adjusted to further develop new features. 
 There are small bugs. For instance, some assets were not properly cutted, which results in weird things generated in the map. Try adding ID 14 in the txt file, and see what happenes. 
 
## Advice on where to begin:
 Checkout src/main/java/edu/ucsb/cs56/projects/games/pokemon/IO/MapLoader.java, to see how it reads the test.txt under src/main/resources/maps, and generates the map. The map is using a x-axis & y-axis coordinate with (0,0) at upper left concern. "Width" and "Height" tiles in txt file define the boundary of the map, and each number below represents the entity ID that shows up at that point of the map. Check out TileData.jave to see the ID nums avaiable. Try to modify ID nums in txt file and see what happens. 
  After making some modifications on map, I would start by understanding the assets, entity of the map, and how entities related to compounts. Then move to the movement system, animation of the map. At last, checkout IO, Game, World, to see how everything comes together. An overall understand of the project's structure makes everything easier. 
