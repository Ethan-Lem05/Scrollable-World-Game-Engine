import greenfoot.*;
import java.util.*;

/**
 * Requires a tile class to be implemented with a TILE_SIDE_LENGTH constant declared
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CameraManager  
{
    //constants
    public static final int TILE_SIDE_LENGTH = 64;
    public static final int BOUNDARY_BUFFER = 2;
    private Tile[][] wholeWorld;
    private Tile[][] currScreen;
    private Actor focus;
    private World world;
    //dimensions of the current world 
    private int realX;
    private int realY;
    private int camBorderX;
    private int camBorderY;
    //shifts describe the position in the screen you reach before the screen starts scrolling
    private int rightShift;
    private int topShift;
    private int bottomShift;
    private int leftShift;
    private int offsetX;
    private int offsetY;
    //dealing with objects in world
    private ScrollableObjectMap objectMap;
    /**
     * creates an instance of a CameraManager for a world --> also keeps track of whats in the world
     * 
     * @param rightShift position on the right of the screen before screen starts scrolling
     * @param leftShift position on the left of the screen before screen starts scrolling
     * @param bottomShift position on the bottom of the screen before screen starts scrolling
     * @param topShift position on the top of the screen before screen starts scrolling
     * @param focus the actor that the camera moves around
     * @param world the world at which the scrolling takes place
     * @param wholeWorld the larger world in which the screen is only a small part of 
     * @param realX the x coordinate in the whole world
     * @paramY realY the y coordinate in the whole world
     */
    public CameraManager(int rightShift, int leftShift, int bottomShift, int topShift, 
    Actor focus, World world, Tile[][] wholeWorld, int startX, int startY){
        this.rightShift = rightShift;
        this.leftShift = leftShift;
        this.bottomShift = bottomShift;
        this.topShift = topShift;
        this.focus = focus;
        this.world = world;
        this.wholeWorld = wholeWorld;
        this.realX = startX;
        this.realY = startY;
        this.objectMap = new ScrollableObjectMap(this.world, this);
        //set initial position
        camBorderX = world.getWidth()/TILE_SIDE_LENGTH;
        camBorderY = world.getHeight()/TILE_SIDE_LENGTH;
        currScreen = new Tile[camBorderX + 2*BOUNDARY_BUFFER][camBorderY + 2*BOUNDARY_BUFFER];
    
        offsetX = 0;
        offsetY = 0;
        setInitialPosition();
    }
    /**
     * shifts the screen given the focus' position on the screen
     */
    public void updateCurrentScreen(){
        //shifts on the X-axis
        if(focus.getX() < leftShift){ // shift to the right
            if(realX != 0){
                offsetX +=  Math.abs(focus.getX() - leftShift);
                for(int i = 0; i < currScreen.length; i++){
                    for(Tile t : currScreen[i]){
                        t.move(Math.abs(focus.getX() - leftShift));
                    }
                }
                //check is last tile is past the width and remake current screen if so
                if(currScreen[currScreen.length - 2*BOUNDARY_BUFFER][0].getX() - TILE_SIDE_LENGTH/2 > world.getWidth()){
                    offsetX = 0;
                    readjustCurrentScreen(-1*BOUNDARY_BUFFER,0);
                }
                focus.setLocation(leftShift,focus.getY()); // resets the location back to the edge
            }
        }else if(focus.getX() > rightShift && realX != wholeWorld.length - currScreen.length - 1){ // shift to the left
            offsetX -= Math.abs(focus.getX() - rightShift);
            for(int i = 0; i < currScreen.length; i++){
                for(Tile t : currScreen[i]){
                    t.move(-1*Math.abs(focus.getX() - rightShift));
                }
            }
            if(currScreen[2*BOUNDARY_BUFFER][BOUNDARY_BUFFER].getX() - TILE_SIDE_LENGTH/2 < 0){
                offsetX = 0;
                readjustCurrentScreen(BOUNDARY_BUFFER,0);
            }  
            focus.setLocation(rightShift,focus.getY());
        }
        //shifts on the Y-axis       
        if(focus.getY() > bottomShift && realY != wholeWorld[0].length - currScreen[0].length - 1){ // if shiting downwards
            offsetY -= Math.abs(focus.getY() - bottomShift);
            for(int i = 0; i < currScreen.length; i++){
                for(Tile t : currScreen[i]){
                    t.setLocation(t.getX(),t.getY() - Math.abs(focus.getY() - bottomShift));
                }
            }
            if(currScreen[BOUNDARY_BUFFER][2*BOUNDARY_BUFFER].getY() - TILE_SIDE_LENGTH/2 < 0){
                offsetY = 0;
                readjustCurrentScreen(0,BOUNDARY_BUFFER);
            }
            focus.setLocation(focus.getX(), bottomShift);
        } else if(focus.getY() < topShift && realY != 0){ // if shifitng upwards
            offsetY += Math.abs(focus.getY() - topShift);
            for(int i = 0; i < currScreen.length; i++){
                for(Tile t : currScreen[i]){
                    t.setLocation(t.getX(),t.getY() + Math.abs(focus.getY() - topShift)); // shift all tiles
                }
            }
            if(currScreen[0][currScreen[0].length - 2*BOUNDARY_BUFFER].getY() - TILE_SIDE_LENGTH/2 > world.getHeight()){
                offsetY = 0;
                readjustCurrentScreen(0,-1*BOUNDARY_BUFFER);
            }
            focus.setLocation(focus.getX(), topShift); // keep focus in frame
        }
        objectMap.reloadObjects(realX, realY);
    }
    private void readjustCurrentScreen(int dx, int dy){
        realX += dx;
        realY += dy;
        if(realX + currScreen.length >= wholeWorld.length){
            realX = wholeWorld.length - currScreen.length;
        } else if(realX < 0){
            realX = 0;
        }
        if(realY + currScreen[0].length >= wholeWorld[0].length){
            realY = wholeWorld[0].length - currScreen[0].length;
        }
        else if(realY < 0){
            realY = 0;
        }
        currScreen =  new Tile[camBorderX + 2*BOUNDARY_BUFFER][camBorderY + 2*BOUNDARY_BUFFER];
        for(int i = realX; i < realX + currScreen.length; i++){
            for(int j = realY; j < realY + currScreen[0].length; j++){
                currScreen[i - realX][j - realY] = wholeWorld[i][j];
            }
        }
        renderCurrentScreen();
    }
    private void setInitialPosition(){
        //adding in the boundary buffer pixels to the first edges
        for(int i = realX; i < realX + currScreen.length; i++){
            for(int j = realY; j < realY + currScreen[0].length; j++){
                currScreen[i - realX][j - realY] = wholeWorld[i][j];
                world.addObject(wholeWorld[i][j],
                i*TILE_SIDE_LENGTH + TILE_SIDE_LENGTH/2 - BOUNDARY_BUFFER*TILE_SIDE_LENGTH + offsetX, 
                j*TILE_SIDE_LENGTH + TILE_SIDE_LENGTH/2 - BOUNDARY_BUFFER*TILE_SIDE_LENGTH + offsetY); // adds the object accounting for the boundary value
            }
        }
        objectMap.loadObjects(realX, realY);
    }
    /**
     * rerenders the entire current screen
     */
    private void renderCurrentScreen(){
        for(Tile t: (ArrayList<Tile>)world.getObjects(Tile.class)){
            world.removeObject(t);
        }

        for(int i = 0; i < currScreen.length; i++){
            for(int j = 0; j < currScreen[i].length; j++){
                world.addObject(currScreen[i][j],
                i*TILE_SIDE_LENGTH + TILE_SIDE_LENGTH/2 - BOUNDARY_BUFFER*TILE_SIDE_LENGTH + offsetX, 
                j*TILE_SIDE_LENGTH + TILE_SIDE_LENGTH/2 - BOUNDARY_BUFFER*TILE_SIDE_LENGTH + offsetY);
            }
        }
    }
    private void readjustFocusIntoFrameSmoothly(double xDestination, double yDestination){
        // TODO implement
    }
}
/**
 * deals with objects in the world such as enemies, items, and anything else that counts as an "entity" instead of a tile
 */
class ScrollableObjectMap {
    private TreeMap<Integer,TreeMap<Integer,ArrayList<ScrollableObject>>> objectMap;
    private int[][] objectsOnScreen;
    //constructor variables
    private World world;
    private CameraManager camera;
    private int tileWidth;
    private int boundarySize;
    //calculated variables
    int worldWidth;
    int worldHeight;
    /**
     * @param world the world in which the objects are present in 
     * @param camera the camera which loads the different objects
     */
    public ScrollableObjectMap (World world, CameraManager camera) {
        objectMap = new TreeMap<Integer,TreeMap<Integer,ArrayList<ScrollableObject>>>();
        this.world = world;
        this.camera = camera;
        this.tileWidth = camera.TILE_SIDE_LENGTH;
        this.boundarySize = camera.BOUNDARY_BUFFER;
        worldWidth = world.getWidth() + 2*boundarySize*tileWidth;
        worldHeight = world.getHeight() + 2*boundarySize*tileWidth;
        
    }
    /**
     * loads the objects that are currently on the screen and places them at the localX and localY
     * 
     * @param localX the localX at which the camera is located
     * @param localY the localY at which the camera is located
     */
    public void loadObjects(int localX, int localY) {
        //sets up the subMap temp that allows us to add to the world easily
        TreeMap<Integer,TreeMap<Integer,ArrayList<ScrollableObject>>> temp;
        temp = (TreeMap<Integer,TreeMap<Integer,ArrayList<ScrollableObject>>>)objectMap
        .subMap(localX - tileWidth*boundarySize, localX + worldWidth + tileWidth*boundarySize);
        for(int i = ((Integer[])(temp.keySet().toArray()))[0]; i < temp.size(); i++){
            temp.put(i,(TreeMap<Integer,ArrayList<ScrollableObject>>)temp // cuts each of the tree maps down to just within
            .get(i)
            .subMap(localY - tileWidth*boundarySize, localY + worldHeight + tileWidth*boundarySize));
        }
        
        //add the objects to the screen
        for(int i = ((Integer[])(temp.keySet().toArray()))[0]; i < temp.size(); i++) {
            for(int j = ((Integer[])(temp.get(i).keySet().toArray()))[0]; j < temp.get(i).size(); j++) {
                ArrayList<ScrollableObject> objectsAtIJ = temp.get(i).get(j);
                for(ScrollableObject s : objectsAtIJ)
                    world.addObject(s, i % (tileWidth*boundarySize), j % (tileWidth*boundarySize));
            }
        }
    }
    /**
     * reloads screen
     * 
     * @param localX the localX at which the camera is located
     * @param localY the localY at which the camera is located
     */
    public void reloadObjects(int localX, int localY) {
        for(ScrollableObject s : world.getObjects(ScrollableObject.class)) {
            world.removeObject(s);
        }
        loadObjects(localX, localY);
    }
    public void scrollObjects(int scrollAmt) {
        for(ScrollableObject s : world.getObjects(ScrollableObject.class)) {
            s.move(-1*scrollAmt);
        }
    }
    public void addObject(ScrollableObject o, int x, int y) {
        objectMap.get(x).get(y).add(o);
    }
    public void removeObject(ScrollableObject o, int x, int y) {
        objectMap.get(x).get(y).remove(o);
        world.removeObject(o);
    }
    public List<ScrollableObject> getObjectsAtLocation(int x, int y){
        return objectMap.get(x).get(y);
    }
}


