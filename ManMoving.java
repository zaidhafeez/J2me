/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.microedition.midlet.*;
import javax.microedition.lcdui.game.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.Sprite;

/**
 * @author ZMQ-10
 */
public class ManMoving extends MIDlet{

    Display display;
    MovingMan movingMan;
    
    public ManMoving() throws Exception{
        
        display = Display.getDisplay(this);
        movingMan = new MovingMan(this);
        
        
    }
    
    public void startApp() {
        
        display.setCurrent(movingMan);
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
        
        destroyApp(false);
        notifyDestroyed();
        
    }
}

class MovingMan extends GameCanvas implements Runnable{
    
    private int currentX, currentY;
    private int width;
    private int height;
    Image image;
    Thread thread;
    
    Sprite Man;
    
    
    ManMoving manMoving;
    
    
    
    public MovingMan(ManMoving manMoving) throws Exception{
        
        super(true);
        
        this.manMoving = manMoving;
        width = getWidth();
        height = getHeight();
        currentX = getWidth() / 2;
        currentY = getHeight() / 2;
        try{
            
            image = Image.createImage("/hero.png");
        }
        catch(Exception e){
            
            e.toString();
            
        }
        
        Man = new Sprite(image, 48, 48);
        thread = new Thread(this, "Game Thread");
        thread.start();
    }
    
    public void run(){
        
        Graphics graphics = getGraphics();
        while(true){
            int delayOfLoop = 1000/15;
            long startLoopTime = System.currentTimeMillis();
            input();
            draw(graphics);
            long endLoopTime = System.currentTimeMillis();
            int loopTime = (int)(endLoopTime - startLoopTime);
            if(loopTime < delayOfLoop){
                
                try {
                    
                    Thread.sleep(delayOfLoop - loopTime);
                    
                } catch (Exception e) {
                
                    e.toString();
                    
                }
                
            }
        }
        
    }
    
    public void input(){
        
        int keyPressed = getKeyStates();
        Man.setFrame(0);
        
        if((keyPressed & LEFT_PRESSED) != 0){
            System.out.println(LEFT_PRESSED);
            currentX = Math.max(0, currentX - 1);
            Man.setFrame(1);
            
        }
        
        if((keyPressed & RIGHT_PRESSED) != 0){
            
            if(currentX + 5 < width){
                
                currentX = Math.min(width, currentX + 1);
                Man.setFrame(2);
//                Man.setFrame(3);
//                Man.setFrame(4);
            
            }
           
        }
        
        if((keyPressed & UP_PRESSED) != 0){
            
            currentY = Math.max(0, currentY - 1);
            Man.setFrame(2);
            
        }
        
        if((keyPressed & DOWN_PRESSED) != 0){
            
            if(currentY + 10 < height){
                
                currentY = Math.min(height, currentY + 1);
                Man.setFrame(1);
                
            }
            
        }
        
    }
    
    private void draw(Graphics graphics){
        
        graphics.setColor(255, 255, 255);
        graphics.fillRect(0, 0, width, height);
        
        graphics.setColor(0x0000ff);
        Man.setPosition(currentX, currentY);
        Man.paint(graphics);
//        System.out.println(Man.getRawFrameCount());
        
        flushGraphics();
        
        
        
    }
    
} 
