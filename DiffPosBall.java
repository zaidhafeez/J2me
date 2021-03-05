/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 * @author ZMQ-10
 */
public class DiffPosBall extends MIDlet {
    
    Display display;
    BallCanvas ballCanvas;
    
    
    
    public DiffPosBall(){
        
        display = Display.getDisplay(this);
        ballCanvas = new BallCanvas(this);
        
        
        
    }

    public void startApp() {
        
        display.setCurrent(ballCanvas);
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
}

class BallCanvas extends Canvas{
    
    DiffPosBall diffPosBall;
    
    int xPos;
    int yPos;
    
    int w;
    int h;
    
    public BallCanvas(DiffPosBall diffPosBall){
        this.diffPosBall = diffPosBall;
        
        xPos = getWidth() / 5;
        yPos = getHeight() / 5;
        
        w = 10;
        h = 5;
    }
    
    public void paint(Graphics graphics){
        
        int width = getWidth();
        int height = getHeight();
        
        
        graphics.setGrayScale(255);
        graphics.fillRect(0, 0, width, height);
        
        for(int i = 0; i < 5; i++){
            
            graphics.setGrayScale((8 - i) * 32 - 16);
            graphics.fillRect((xPos - i) * 5, (yPos - 10) * i, w, h);
            
            
        }
        
    }
    
}
