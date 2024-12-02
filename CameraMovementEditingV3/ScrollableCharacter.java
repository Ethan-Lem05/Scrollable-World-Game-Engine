import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ScrollableCharacter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ScrollableCharacter extends SuperSmoothMover
{
    //constants
    public static final int GRAVITY = 1;
    public static final int JUMP_VELOCITY = 20;
    public static final int RUNNING_VELOCITY = 7;
    public static final String RIGHT_KEY = "D";
    public static final String LEFT_KEY = "A";
    public static final String UP_KEY = "W";
    public static final String DOWN_KEY = "S";
    //variables
    private int velocityY;
    private int velocityX;
    /**
     * builds a mover object 
     */
    public ScrollableCharacter(){
        enableStaticRotation();
    }
    /**
     * Act - do whatever the Mover wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act(){
        setMovementValues(); // sets movement values 
        move(); // actually moves the object according to the vector formed by setMovementValues() 
    }
    /**
     *  sets the movement values for the character before they move
     */
    private void setMovementValues()
    {
        if(Greenfoot.isKeyDown(UP_KEY)){ // check jump key
            velocityY = 5;
        }
        else if(Greenfoot.isKeyDown(DOWN_KEY)){
            velocityY = -5;
        }
        else{
            velocityY = 0;
        }
        if(Greenfoot.isKeyDown(LEFT_KEY)){ // check left key
            velocityX = -5;
        }
        else if(Greenfoot.isKeyDown(RIGHT_KEY)){ // check right key
            velocityX = 5;
        }
        else{
            velocityX = 0;
        }
    }
    /**
     * A custom atan2 function that returns the correct angle for all quadrants.
     *
     * @param adj The adjacent side
     * @param opp The opposite side
     * @return The angle in radians
     */
    public static double atan(double adj, double opp) {
      double value = Math.atan2(opp, adj);
      if(adj != 0 && opp != 0){
          if (adj > 0 && opp > 0) {
            return value;
          } else if (adj > 0 && opp < 0) {
            return value;
          } else if (adj < 0 && opp < 0) {
            return value;
          } else {
            return value;
          }
      }
      
      if(adj == 0 && opp > 0)
      {
          return Math.PI/2;
      }
      else if(adj == 0 && opp < 0)
      {
          return -1*Math.PI/2;
      }
      else if(adj > 0 && opp == 0)
      {
          return 0;
      }
      else 
      {
          return -Math.PI;
      }
      
    }
    protected void move(){
        setRotation(Math.toDegrees(atan(velocityX, -1 * velocityY)));
        move(Math.sqrt(Math.pow(velocityX,2) + Math.pow(velocityY,2)));
    }
}
