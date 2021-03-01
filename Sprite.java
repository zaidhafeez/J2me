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
        hBounds = hBnd;
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
    
    public int getWidth(){
        
        return width;
        
    }
    
    public int getHeight(){
        
        return height;
        
    }
    
    public int getZorder() {
            
        return zOrder;
    }
    
    protected void incFrame(){
        
        if(frameDelay > 0 && --frameTrigger <= 0){
            
            // Reset the frame trigger

            frameTrigger = frameDelay;
            
            // Increment the frame
            
            frame += frameInc;
            
            if(frame >= image.length){
                frame = 0;
            }
            else if(frame < 0){
                frame = image.length - 1;
            }
            
        }
        
    }
    
    public int update(){
        
        int action = 0;
        
        incFrame();
        // Increment the frame
        int xPos = xPosition;
        int yPos = yPosition;
        xPos += xVelocity;
        yPos += yVelocity;
        
        // Check the bounds
        // Wrap?

        if(boundsAction == Sprite.BA_WRAP){
            
            if(xPos + width < xBounds){
                
                xPos = xBounds + wBounds;
                
            }
            else if(xPos < (xBounds + wBounds)){
                
                xPos = xBounds - width;
                
            }
            if(yPos + height < yBounds){
                
                yPos = yBounds + hBounds;
                
            }
            else if(yPos < (yBounds + hBounds)){
                
                yPos = yBounds - height;
                
            }
            
        }
        // Bounce?
        else if(boundsAction == Sprite.BA_BOUNCE){
            
            boolean bounce = false;
            
            int xVel = xVelocity;
            int yVel = yVelocity;
            
            if(xPos < xBounds){
             
                bounce = true;
                xPos = xBounds;
                xVel = -xVel;
                
            }
            else if((xPos + width) > (xBounds + wBounds)){
                
                bounce = true;
                xPos = xBounds + wBounds - width;
                xVel = -xVel;
                
            }
            
            if(yPos < yBounds){
                
                bounce = true;
                yPos = yBounds;
                yVel = -yVel;
                
            }
            else if((yPos + height) > (yBounds + hBounds)){
            
                bounce = true;
                yPos = yBounds + hBounds - height;
                yVel = -yVel;
            
            }
            if(bounce){
                setVelocity(xVel, yVel);
            }
            
        }
        // Die?
        else if(boundsAction == Sprite.BA_DIE){
            
            if((xPos + width) < xBounds || xPos > wBounds || (yPos + height) < yBounds || yPos > hBounds){
                
                action |= Sprite.SA_KILL;
                return action;
                
            }
            
        }
        // Stop (default)
        else{
            
            if(xPos < wBounds || xPos > (xBounds + wBounds - width )){
                
                xPos = Math.max(xBounds, Math.min(xPos, xBounds + wBounds - width));
                setVelocity(0, 0);
                
            }
            if(yPos < hBounds || yPos > (yBounds + hBounds - height)){
                
                yPos = Math.max(yBounds, Math.min(yPos, yBounds + hBounds - height));
                setVelocity(0, 0);
                
            }
        }
        
        setPosition(xPos, yPos);
        return action;
    }
    
    public void draw(Graphics graphics){
        // Draw the current frame
        if(!hidden){
            
            graphics.drawImage(image[frame], xPosition, yPosition,
                                Graphics.TOP | Graphics.LEFT);
            
        }
        
    }
    
    protected Sprite addSprite(int action){
        
        return null;
        
    }
    
    protected boolean testCollison(Sprite test){
        // Check for collision with another sprite
        if(test != this){
            
            return intersects(test.getXPosition(), test.getYPosition(),
                   test.getWidth(), test.getHeight());
            
        }
        
        return false;
    }
    
    protected boolean intersects(int xTest, int yTest, int wTest, int hTest){
        
        return ((xTest > xPosition && xTest < (xPosition + width))) ||
                (xPosition > xTest && xPosition < (xTest + wTest)) &&
                ((yTest > yPosition && yTest < (xPosition + height))) ||
                (yPosition > yTest && yPosition < (yTest + height));   
        
    }
    
}
