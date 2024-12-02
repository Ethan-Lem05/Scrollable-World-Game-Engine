import greenfoot.*;

/**
 * Write a description of class BackgroundTile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BackgroundTile extends Tile
{
    public BackgroundTile()
    {
        setImage(new GreenfootImage(TILE_SIDE_LENGTH, TILE_SIDE_LENGTH));
        getImage().setColor(new Color(200,200,250));
        getImage().fill();
    }
    protected void drawTile(GreenfootImage tileBackground)
    {
    }
}
