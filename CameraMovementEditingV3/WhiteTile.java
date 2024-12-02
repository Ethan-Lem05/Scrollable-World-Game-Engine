import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WhiteTile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WhiteTile extends Tile
{
    /**
     * Act - do whatever the WhiteTile wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
    public void drawTile(GreenfootImage tileBackground){
        tileBackground.setColor(Color.WHITE);
        tileBackground.fill();
    }
}
