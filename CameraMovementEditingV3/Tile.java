import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Tile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Tile extends Actor
{
    //constants 
    public static final int TILE_SIDE_LENGTH = 64;
    protected int locationX;
    protected int locationY;
    protected int sideLength;
    protected GreenfootImage tileImage;
    /**
     * creates a tile object using the tile side length. The decoration the design of the tile 
     * handled by the subclass
     */
    public Tile()
    {
        sideLength = TILE_SIDE_LENGTH;
        tileImage = new GreenfootImage(TILE_SIDE_LENGTH,TILE_SIDE_LENGTH);
        drawTile(tileImage);
        setImage(tileImage);
    }
    /**
     * ensures that all child classes implement its own image
     */
    protected abstract void drawTile(GreenfootImage tileBackground); // must add its own image
}
