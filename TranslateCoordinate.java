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
public class TranslateCoordinate extends MIDlet {

    Display display;
    MyTranslateCanvas myTranslateCanvas;
    
    public TranslateCoordinate(){
        display = Display.getDisplay(this);
        myTranslateCanvas = new MyTranslateCanvas(this);
    }
    
    public void startApp() {
        
        display.setCurrent(myTranslateCanvas);
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
        
        notifyDestroyed();
        
    }
    
    public void exitttMID(){
        
        destroyApp(true);
        
    }

}

class MyTranslateCanvas extends Canvas implements CommandListener{
    
    Image image = null;
    Command exit;
    TranslateCoordinate translateCoordinate;
    
    public MyTranslateCanvas(TranslateCoordinate translateCoordinate){
        
        this.translateCoordinate = translateCoordinate;
        exit =new Command("Exit", Command.EXIT, 1);
        addCommand(exit);
        setCommandListener(this);
        
        try{
            
            image = Image.createImage(70, 70);
            Graphics graphics = image.getGraphics();
            graphics.setColor(255, 0, 0);
            graphics.fillArc(10, 10, 60, 50, 180, 180);
            
        }
        catch(Exception e){
            
            Alert alert = new Alert("Failure", "Image Not open", null, null);
            alert.setTimeout(Alert.FOREVER);
            translateCoordinate.display.setCurrent(alert);
            
        }
        
    }
    
    
    public void paint(Graphics graphics){
        
        if(image != null){
            
            graphics.setColor(255,255,255);
            graphics.fillRect(0, 0, getWidth(), getHeight());
            graphics.translate(90, 90);
            graphics.drawImage(image, 0, 0, Graphics.VCENTER | Graphics.HCENTER);
            
        }
        
    }
    
    public void commandAction(Command command, Displayable displayable){
        
        if(command == exit){
            
            translateCoordinate.exitttMID();
            
        }
        
    }
}
