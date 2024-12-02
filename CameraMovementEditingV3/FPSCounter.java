import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Date;
/**
 * Write a description of class FPSCounter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FPSCounter extends Actor
{
    GreenfootImage label;
    double fps;
    long lastTime;
    Date date;
    public FPSCounter(){
        label = new GreenfootImage(250,100);
        label.setColor(Color.RED);
        label.fill();
        label.setColor(Color.BLACK);
        label.setFont(new Font(25));
        setImage(label);
        fps = 0;
        date = new Date();
        lastTime = date.getTime();
    }
    public void act()
    {
        updateFPS();
        label.setColor(Color.RED);
        label.fill();
        label.setColor(Color.BLACK);
        label.setFont(new Font(25));
        String text = "fps: " + fps;
        label.drawString(text,0,getImage().getHeight()/2);
    }
    private void updateFPS(){
        date = new Date();
        fps = 1000/(date.getTime() - lastTime);  
        lastTime = date.getTime();
    }
}
