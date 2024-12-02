import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Mover here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Mover extends SuperSmoothMover
{
    //constants
    public static final int GRAVITY = 1;
    public static final int JUMP_VELOCITY = 20;
    public static final int RUNNING_VELOCITY = 7;
    public static final String RIGHT_KEY = "D";
    public static final String LEFT_KEY = "A";
    public static final String UP_KEY = "space";
    public static final String DOWN_KEY = "S";
    //variables
    private int velocityY;
    private int velocityX;
    private boolean isLanded;
    private Platform landedPlatform;
    /**
     * builds a mover object 
     */
    public Mover(){
        enableStaticRotation();
    }
    /**
     * Act - do whatever the Mover wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act(){
        checkForPlatform(); // checks to see if this has landed on a platform, or on the ground
        setMovementValues(); // sets movement values 
        //lands on platform 
        collideWithPlatform();
        move(); // actually moves the object according to the vector formed by setMovementValues() 
    }
    /**
     * checks to see if the actor is at the edge of the world or has landed on a platform
     */
    private void checkForPlatform(){
        landedPlatform = (Platform)this.getOneIntersectingObject(Platform.class);
        if(this.getY() + getImage().getHeight()/2 + GRAVITY > getWorld().getHeight() || landedPlatform != null) //&& this.getY() + getImage().getHeight()/2 > landedPlatform.getY() - landedPlatform.getImage().getHeight()/2)){
        {    isLanded = true;
        }
        else { 
            isLanded = false;
        }
    }
    /**
     *  sets the movement values for the character before they move
     */
    private void setMovementValues()
    {
          if(landedPlatform != null && velocityY > 0 && getY() != landedPlatform.getY() - getImage().getHeight()/2 - landedPlatform.getImage().getHeight()/2 + 5){
            velocityY = 0;
            setLocation(getX(), landedPlatform.getY() + getImage().getHeight()/2 + landedPlatform.getImage().getHeight()/2);
            landedPlatform = null;
            isLanded = false;
        } 
        //changes in velocity
        if(isLanded){ // if not landed
            if(Greenfoot.isKeyDown(UP_KEY)){ // check jump key
                velocityY = JUMP_VELOCITY;
            }
            else{
                velocityY = 0;
            }
            if(Greenfoot.isKeyDown(LEFT_KEY)){ // check left key
                velocityX = -1 * RUNNING_VELOCITY;
            }
            else if(Greenfoot.isKeyDown(RIGHT_KEY)){ // check right key
                velocityX = RUNNING_VELOCITY;
            }
            else{
                velocityX = 0;
            }
        } else {
            if(Greenfoot.isKeyDown(LEFT_KEY)){ // check left key
                velocityX = -1 * RUNNING_VELOCITY;
            }
            else if(Greenfoot.isKeyDown(RIGHT_KEY)){ // check right key
                velocityX = RUNNING_VELOCITY;
            } else {
                velocityX = 0;
            }
            velocityY = Math.max(velocityY - GRAVITY,-20); // always falling
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
    private void collideWithPlatform(){
        if(isLanded) {
            if(landedPlatform != null){ // if landed on platform
                setLocation(getX(),landedPlatform.getY() - getImage().getHeight()/2 - landedPlatform.getImage().getHeight()/2 + 5);
            } else { // if landed on ground
                setLocation(getX(),getWorld().getHeight() - getImage().getHeight()/2);
            }
        }
        //horizontal hit detection
        for(int i = -1*getImage().getHeight()/2; i < getImage().getHeight()/2; i += 20)
        {
            Platform temp = (Platform)getOneObjectAtOffset((int)Math.signum(velocityX)*(getImage().getWidth()/2 + 10),i,Platform.class);
            if(temp != null)
            {
                isLanded = false;
                setLocation(temp.getX() - Math.signum(velocityX)*(temp.getImage().getWidth()/2 + getImage().getWidth()/2 + 10),
                getY());
            }
        }
    }
}
