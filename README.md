# Scrollable world game engine 

This is a project I developed in first year that renders a 2D world in a game engine called Greenfoot for Java. This game engine was built on a few main classes:

1. Tile: which acted as a section of the map that could be dynamically loaded and unloaded
2. Item: which were items that could be interacted with by the player. These were loaded specially since they did not necessarily have to be placed directly in the centre of a tile.
3. CaneraManager: which when instantiated in the Greenfoot, turned the world into one of the scrollable worlds where the camera and the rendering of the Tile Map was done exclusively through the CameraManager
   
The world itself is built upon 2 large 2D ordered TreeMaps for O(1) access time and simple storage. The larger one contained the entire world and was rarely iterated through as to improve performance. The smaller one made use of optimizations that converted what would have been O(n^2) operations to O(n) by only iterating through 1 axis of the viewport at a time. 

This was one of my earlier projects but holds a lot of value since it demonstrates my understanding of data structures and how I can research, design and implement complex projects. 
