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
public class ArcDraw extends MIDlet implements CommandListener {
    
    private Display display;
    private DrawArc drawArc;
    
    public ArcDraw(){
        drawArc = new DrawArc();
        display = Display.getDisplay(this);
    }
    
    public void startApp() {
        display.setCurrent(drawArc);
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
    public void commandAction(Command command, Displayable displayable){
        
    }
    
class DrawArc extends Canvas implements CommandListener{
    
    public DrawArc(){
    
    }
    
    public void paint(Graphics graphics){
        
        graphics.setColor(255, 162, 117);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        
        graphics.setColor(0, 0, 255);
        graphics.fillArc(0, 0, getWidth()/2, getHeight()/2, 0, 90);
        graphics.setStrokeStyle(Graphics.DOTTED);
        graphics.setColor(0xFFFF00);
        graphics.drawRect(0, 0, getWidth()/2, getHeight()/2);
        
        graphics.setStrokeStyle(Graphics.SOLID);
        graphics.setColor(0, 0, 255);
        graphics.fillArc(getWidth()/2, 0, getWidth()/2, getHeight()/2, 0, -90);
        graphics.setStrokeStyle(Graphics.DOTTED);
        graphics.setColor(0xFFFF00);
        graphics.drawRect(getWidth()/2, 0, getWidth()/2, getHeight()/2);
        
        graphics.setStrokeStyle(Graphics.SOLID);
        graphics.setColor(0, 0, 255);
        graphics.fillArc(0, getHeight()/2, getWidth(), getHeight()/2, -90, -180);
        graphics.setStrokeStyle(Graphics.DOTTED);
        graphics.setColor(0xFFFF00);
        graphics.drawRect(0, getHeight()/2, getWidth(), getHeight()/2);
        
    }
    public void commandAction(Command command, Displayable displayable){
        
    }
}
}
