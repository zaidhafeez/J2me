/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.util.*;
import java.io.IOException;

/**
 * @author ZMQ-10
 */
public class MovingObject extends MIDlet implements CommandListener {
    
    
    Form form;
    Display display;
    ImageItem splash;
    MovingCanvas movingCanvas;
    Command exit, start;
    
    
    public MovingObject(){
        
        form = new Form("Canvas Starter");
        display = Display.getDisplay(this);
        movingCanvas = new MovingCanvas(display, form);
        start = new Command("Start", Command.OK, 1);
        exit = new Command("Exit", Command.EXIT, 1);
        form.append(new Spacer(50, 100));
        splash = new ImageItem("Moving Object", null, ImageItem.LAYOUT_CENTER, null);
        try {
            
            Image image = Image.createImage("/slideshow/1.png");
            splash.setImage(image);
            
        } catch (Exception e) {
            
            System.out.println(e.toString());
            
        }
        
        form.append(splash);
        form.addCommand(exit);
        form.addCommand(start);
        form.setCommandListener(this);
        
    }
    
    public void startApp() {
        
        display.setCurrent(form);
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
    public void commandAction(Command command, Displayable displayable){
        
        if(command == start){
            
            form.deleteAll();
            try{
                
                movingCanvas = new MovingCanvas(display, form);
                
            }catch(Exception e){
                
                e.printStackTrace();
                
            }
            display.setCurrent(movingCanvas);
            System.out.println("Start Command");
            
        }
        if(command == exit){
            
            try{
                
                destroyApp(true);
                notifyDestroyed();
                
            }catch(Exception e){
                
                System.out.println(e.toString());
                
            }
            
        }
        
    }
}

class MovingCanvas extends Canvas implements Runnable, CommandListener{
    
    Command exit;
    Display display;
    Form form;
    int XPosition;
    int YPosition;
    int width;
    int height;
    int xVelocity, yVelocity;
    int xBounds, yBounds, wBounds, hBounds;
    int positionChange;
    final int BA_BOUNCE = 1;
    
    
    
    Thread thread;
    
    public MovingCanvas(Display start, Form stForm){
        
        exit = new Command("Exit", Command.EXIT, 1);
        form = stForm;
        display = start;
        
        XPosition = getWidth() / 3;
        YPosition = getHeight() / 3;
        
        xVelocity = 2;
        yVelocity = 2;
        
        xBounds = 0;
        yBounds = 0;
        
        wBounds = getWidth();
        hBounds = getHeight();
        
        addCommand(exit);
        setCommandListener(this);
        
        thread = new Thread(this, "Game Thread");
        thread.start();
        
    }

    public void run() {
        while (true) { 
            
            int delayOfLoop = 1000/25;
            long loopStartTime = System.currentTimeMillis();
            runGame();
            long loopEndTime = System.currentTimeMillis();
            int loopTime = (int)(loopEndTime - loopStartTime);
            if(loopTime < delayOfLoop){
                
                try{
                    
                    thread.sleep(delayOfLoop - loopTime);
                    
                }catch(Exception e){
                    e.toString();
                }
                
            }
        }
        
    }

    protected  void paint(Graphics graphics){
        
        graphics.setColor(255, 255, 255);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.setColor(0, 255, 0);
        graphics.fillArc(XPosition, YPosition, getWidth()/ 2, getHeight()/2, 0, 360);
        graphics.setColor(255,0,0);
//        graphics.fillArc(90, 50, 10, 10, 0, 360);
    }
    
    protected void runGame(){
        checkBoundaries();
        switch(positionChange){
            case LEFT:
                XPosition -= xVelocity;
                break;
            case RIGHT:
                 XPosition += xVelocity;
                break;
            case UP:
                YPosition -= yVelocity;
            case DOWN:
                YPosition += yVelocity;
            
            
        }
        repaint();
    }
    
    public void checkBoundaries(){
        
        if(XPosition < xBounds){
            XPosition = xBounds;
            xVelocity = -xVelocity;
        }
        else if((XPosition + getWidth()/2) > (xBounds + wBounds)){
            
            XPosition = xBounds + wBounds - getWidth()/2;
            xVelocity = -xVelocity;
            
        }
        if(YPosition < yBounds){
            YPosition = yBounds;
            yVelocity = -yVelocity;
        }
        else if(YPosition + getHeight()/2 > (yBounds + hBounds)){
            
            YPosition = yBounds + hBounds - getHeight()/2;
            yVelocity = -yVelocity;
            
        }
        
    }
    
    
    
    
    
    
    public void keyPressed(int keyCode){
        
        switch(getGameAction(keyCode)){
            
            case Canvas.LEFT:
                positionChange  = LEFT;
                break;
            case Canvas.RIGHT:
                positionChange = RIGHT;
                break;
            case Canvas.UP:
                positionChange = UP;
            case Canvas.DOWN:
                positionChange = DOWN;
                
            
        }
        
    }
    
    public void commandAction(Command command, Displayable displayable){
        
        if(command == exit){
            
            display.setCurrent(form);
            form.append("Game Over");
            
        }
        
    }
      
    
    
    
    
}
