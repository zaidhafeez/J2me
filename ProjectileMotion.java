/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.lang.Math;

/**
 * @author ZMQ-10
 */
public class ProjectileMotion extends MIDlet {

    Display display;
    ProjectileCanvas projectileCanvas;
    
    public ProjectileMotion(){
        
        display = Display.getDisplay(this);
        projectileCanvas = new ProjectileCanvas();
        
    }
    
    public void startApp() {
        
        display.setCurrent(projectileCanvas);
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
        notifyDestroyed();
    }
}

class ProjectileCanvas extends GameCanvas implements Runnable{
    
    Thread thread;
//    int speed;
//    int deltaTime;
//    int endTime;
//    int x;
//    int y;
//    int ax;
//    int ay;
//    int angle;
//    int vx;
//    int vy;
    int xPos;
    int yPos;
    
    int velocity;
    double deltatime;
    
    
    
    
    public ProjectileCanvas(){
        
        super(true);
        xPos = getWidth() / 4;
        yPos = getHeight() / 4;
        velocity = 2;
        deltatime = 0.0001;
//        v = 1;
//        t = 2;
//        speed = 2;
//        deltaTime = 1;
//        endTime = 10;
//        x = getWidth() / 4;
//        y = getHeight() / 2;
//        ay = 5;
//        ax = 0;
//        angle = 15;
//        vx = (int) (speed * Math.cos(angle * (3 / 180)));
//        vy = (int) (speed * Math.sin(angle * (3/ 180)));
        thread = new Thread(this, "gameThread");
        thread.start();
        
    }
    
    public void run(){
        
        Graphics graphics = getGraphics();
        while (true) {            
           
            int delay = 5;
            
            rendor(graphics);
            flushGraphics();
            try{
                
                Thread.sleep(50);
                
            }
            catch(Exception e){
                
                e.toString();
                
            }
            
        }
        
    }
    
    public void rendor(Graphics graphics){
        
        momentum();
        graphics.setColor(255, 0, 0);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        
        graphics.setColor(0, 255, 0);
        graphics.fillArc(xPos, yPos, getWidth() / 8, getHeight() / 8, 0, 360);
        
//         momentum(xVel, yVel);
    }
    
    public void momentum(){
        
        xPos += velocity * deltatime;
        yPos += 5 * deltatime * deltatime;
        deltatime += deltatime;
        
        
    }
    
    
}
