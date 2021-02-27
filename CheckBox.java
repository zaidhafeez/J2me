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
public class CheckBox extends MIDlet {
    
    Display display;
    CheckBoxCanvas checkBoxCanvas;
    
    public CheckBox(){
        
        display = Display.getDisplay(this);
        checkBoxCanvas = new CheckBoxCanvas(this);
        
    }

    public void startApp() {
        
        display.setCurrent(checkBoxCanvas);
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
    public void eexittMID(){
    
        destroyApp(false);
        notifyDestroyed();
        
    }
}

class CheckBoxCanvas extends Canvas implements CommandListener{
    
    Command exit;
    CheckBox checkBox;
    
    public CheckBoxCanvas(CheckBox checkBox){
        
        this.checkBox = checkBox;
        exit = new Command("Exit", Command.EXIT, 1);
        addCommand(exit);
        setCommandListener(this);
       
        
    }
    
    protected void paint(Graphics graphics){
        
        byte x = 5;
        byte y = 5;
        
//        while(y != getHeight()){
//            
//            graphics.setColor(255,255,255);
//            graphics.fillRect(0, 0, getWidth(), getHeight());
//            graphics.setColor(255, 0, 0);
//            graphics.fillRect(x, y, 20, 20);
//            y += 5;
//            
//        }
//        
//       
//        
//        while(x != getWidth()){
//            
//            graphics.setColor(255,255,255);
//            graphics.fillRect(0, 0, getWidth(), getHeight());
//            graphics.setColor(0, 0, 0);
//            graphics.fillRect(x, 5, 20, 20);
//            x += 5;
//        
//        }
    }
    
    public void commandAction(Command command, Displayable displayable){
        
        if(command == exit){
            checkBox.eexittMID();
        }
        
    }
    
}
