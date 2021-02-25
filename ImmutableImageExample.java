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
public class ImmutableImageExample extends MIDlet {
    
    Display display;
    ImmutableImageCanvas immutableImageCanvas;
    
    public ImmutableImageExample(){
        
        display = Display.getDisplay(this);
        immutableImageCanvas = new ImmutableImageCanvas(this);
        
    }

    public void startApp() {
        
        display.setCurrent(immutableImageCanvas);
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
    public void exittMID(){
        
        destroyApp(true);
        notifyDestroyed();
        
    }
    
}

class ImmutableImageCanvas extends Canvas implements CommandListener{
    
    ImmutableImageExample immutableImageExample;
    Image image = null;
    Command exit;
    
    
    public ImmutableImageCanvas(ImmutableImageExample immutableImageExample){
        
        this.immutableImageExample = immutableImageExample;
        exit = new Command("Exit", Command.EXIT, 1);
        addCommand(exit);
        setCommandListener(this);
        try{
        
            image = Image.createImage("/img/mo.png");
        
    
        }
        catch(Exception error){
            
            Alert alert = new Alert("Failure", "Image is not Loaded", null, null);
            alert.setTimeout(Alert.FOREVER);
            immutableImageExample.display.setCurrent(alert);
    
        }
    
        
    }
    
    
    public void paint(Graphics graphics){
        
        if(image != null){
            
            graphics.setColor(255,255,255);
            graphics.fillRect(0, 0, getWidth(), getHeight());
            graphics.drawImage(image, getWidth()/2, getHeight()/2, Graphics.VCENTER | Graphics.HCENTER);
            
        }
        
    }
    
    public void commandAction(Command command, Displayable displayable){
        
        if(command == exit){
            
            immutableImageExample.exittMID();
            
        }
        
    }
    
}
