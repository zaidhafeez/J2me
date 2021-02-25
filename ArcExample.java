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
public class ArcExample extends MIDlet {
    
    Display display;
    ArcCanvas arcCanvas;
    
     public ArcExample(){
         
         display = Display.getDisplay(this);
         arcCanvas = new ArcCanvas(this);
         
     }
    
    public void startApp() {
        
        display.setCurrent(arcCanvas);
        
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
        
        notifyDestroyed();
    }
    
    public void exitMid(){
        
        destroyApp(true);
        
    }    
}

class ArcCanvas extends Canvas implements CommandListener{
    
    ArcExample arcExample;
    Command exit;
    
    public ArcCanvas(ArcExample arcExample){
        
        this.arcExample = arcExample;
        exit = new Command("Exit", Command.EXIT, 1);
        addCommand(exit);
        setCommandListener(this);
        
    }
    
    public void paint(Graphics graphics){
        
        graphics.setColor(255, 255, 255);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.setColor(255, 0, 0);
//        graphics.fillArc(0, 0, getWidth(), getHeight(), 180, 180);
        graphics.drawArc(0, 0, getWidth(), getHeight(), 180, 180);
        
    }
    
    public void commandAction(Command command, Displayable displayable){
        
        if(command == exit){
            
            arcExample.exitMid();
            
        }
        
    }
     
}
