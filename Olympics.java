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
public class Olympics extends MIDlet {
    
    Display display;
    OlympicsCanvas olympicsCanvas;
    
    public Olympics(){
    
        display = Display.getDisplay(this);
        olympicsCanvas = new OlympicsCanvas(this);
        
    }

    public void startApp() {
        
        display.setCurrent(olympicsCanvas);
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
    public void exMIDD(){
    
        destroyApp(false);
        notifyDestroyed();
        
    }
    
}

class OlympicsCanvas extends Canvas implements CommandListener{
    
    Command exit;
    Olympics olympics;
    Image image = null;
    
    public OlympicsCanvas(Olympics olympics){
        
        this.olympics = olympics;
        exit = new Command("Exit", Command.EXIT, 1);
        addCommand(exit);
        setCommandListener(this);
        
        try{
            image = Image.createImage(90, 90);
            Graphics graphics = image.getGraphics();

            graphics.setColor(0, 0, 255);
            graphics.drawArc(5, 5, 25, 25, 0, 360);
            graphics.setColor(0, 0, 0);
            graphics.drawArc(35, 5, 25, 25, 0, 360);
            graphics.setColor(255, 0, 0);
            graphics.drawArc(65, 5, 25, 25, 0, 360);

            graphics.setColor(255, 255, 0);
            graphics.drawArc(20, 20, 25, 25, 0, 360);
            graphics.setColor(0, 255, 0);
            graphics.drawArc(50, 20, 25, 25, 0, 360);
        }
        catch(Exception error){
            
            Alert alert = new Alert("failure", "Image Not Loaded", null, null);
            alert.setTimeout(Alert.FOREVER);
            olympics.display.setCurrent(alert);
            
        }
    }
    
    protected void paint(Graphics graphics){
        if(image != null){
            
            graphics.setColor(255, 255, 255);
            graphics.fillRect(0, 0, getWidth(), getHeight());
            graphics.drawImage(image, getWidth()/2, getHeight()/2, Graphics.VCENTER | Graphics.HCENTER);
               
        }
    }
    
    public void commandAction(Command command, Displayable displayable){
        
        if(command == exit){
            olympics.exMIDD();
        }
        
    }
}