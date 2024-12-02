import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TileWorld extends World
{
    private CameraManager camera;
    private Tile[][] entireWorld;
    private ScrollableCharacter mover;
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public TileWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1024, 640, 1, false); 
        setPaintOrder(FPSCounter.class,ScrollableCharacter.class,Tile.class);
        mover = new ScrollableCharacter();
        entireWorld = new Tile[200][200];
        createWorld();
        camera = new CameraManager(getWidth()/2 + mover.getImage().getWidth()/2,
        getWidth()/2 - mover.getImage().getWidth()/2,
        getHeight()/2 - mover.getImage().getHeight()/2 + 32,
        getHeight()/2-mover.getImage().getHeight()/2, mover,this, entireWorld, 0, 0);
        addObject(mover, getWidth()/2, getHeight()/2);
        //FPSCounter fpsCounter = new FPSCounter();
        //addObject(fpsCounter,
        //getWidth() - fpsCounter.getImage().getWidth()/2,fpsCounter.getImage().getHeight()/2);
    }
    public void createWorld(){
        //create a world
        for(int i = 0; i < entireWorld.length; i++){
            for(int j = 0; j < entireWorld[i].length; j++){
                if(j%2 == 0){
                    if(i%2 == 0){
                        entireWorld[i][j] = new WhiteTile();
                    }
                    else{
                        entireWorld[i][j] = new BlackTile();
                    }
                } else{
                    if(i%2 == 0){
                        entireWorld[i][j] = new BlackTile();
                    }
                    else{
                        entireWorld[i][j] = new WhiteTile();
                    }
                }
            }
        }
        for(int i = 0; i < entireWorld[0].length; i++){
            entireWorld[entireWorld.length - 1][i] = new BlackTile();
        }
        entireWorld[5][5] = new BackgroundTile();
    }
    public void act(){
        camera.updateCurrentScreen();
    }
}
