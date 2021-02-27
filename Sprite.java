/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ZMQ-10
 */
import javax.microedition.lcdui.*;

public class Sprite {
    
    //Sprite Action Flags
    public static final int SA_KILL = 0,
                            SA_RESTOREPOS = 2,
                            SA_ADDSPRITE = 4;
    
    //Sprite Bound Actions
    public static final int BA_STOP = 0,
                            BA_WRAP = 1,
                            BA_BOUNCE = 2,
                            BA_DIE = 4;
    
    protected Image[]       image;
    protected int           frame,
                            frameInc,
                            frameDelay,
                            frameTrigger;
    
    protected int           xPosition, yPosition,
                            width,height;
    
    protected int           zOrder;
    
    protected int           xVelocity, yVelocity;
    
    protected int           xBounds, yBounds, wBounds, hBounds;
    protected int           boundsAction;
    boolean                 hidden = false;

    // Sprite class constructor without support for  frame Animation
    
    public Sprite(Image img, int xPos, int yPos, int xVel, int yVel, int z, int wBnd, int hBnd, int ba){
        
        image = new Image[1];
        image[0] = img;
        setPosition(xPos, yPos);
        width = img.getWidth();
        height = img.getHeight();
        setVelocity(xVel, yVel);
        frame = 0;
        frameInc = 0;
        frameDelay = frameTrigger = 0;
        zOrder = z;
        xBounds = yBounds = 0;
        wBounds = wBnd;
        hBounds = hBnd;
        boundsAction = ba;
        
    }
    
    // Sorite class constructor with support for Frame Animation
    
    public Sprite(Image[] img, int f, int fi, int fd, int xPos, int yPos, int xVel, int yVel, int z, int wBnd, int hBnd, int ba){
        
        image = img;
        setPosition(xPos, yPos);
        width = img[f].getWidth();
        height = img[f].getHeight();
        setVelocity(xVel, yVel);
        frame = f;
        frameInc = fi;
        frameDelay = frameTrigger = fd;
        zOrder = z;
        xBounds = yBounds = 0;
        wBounds = wBnd;
        hBounds = hBounds;
        boundsAction = ba;
        
    }
    
    public int getXVelocity(){
        
        return xVelocity;
        
    }
    
    public int getYVelocity(){
        
        return yVelocity;
        
    }
    
    public void setVelocity(int xVel, int yVel){
        
        xVelocity = xVel;
        yVelocity = yVel;
        
    }
    
    public int getXPosition(){
        
        return xPosition;
        
    }
    
    public int getYPosition(){
        
        return yPosition;
        
    }
    
    public void setPosition(int xPos, int yPos){
        
        xPosition= xPos;
        yPosition = yPos;
        
    }
    
}
