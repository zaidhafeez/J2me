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
public class TextExample extends MIDlet {
    
    Display display;
    TextCanvas textCanvas;
    
    public TextExample(){
        
        display = Display.getDisplay(this);
        textCanvas = new TextCanvas(this);
        
    }

    public void startApp() {
        
        display.setCurrent(textCanvas);
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    
        notifyDestroyed();      
    
    }
    
    public void extMID(){
        
        destroyApp(true);
        
    }
}

class TextCanvas extends Canvas implements CommandListener{
    
    Command exit;
    TextExample textExample;
    
    public TextCanvas(TextExample textExample){
        
        this.textExample = textExample;
        exit = new Command("Exit", Command.EXIT, 1);
        addCommand(exit);
        setCommandListener(this);
        
    }
    
    public void paint(Graphics graphics){
        
        graphics.setColor(255,255,255);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.setColor(255, 0, 0);
        graphics.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL));
        graphics.drawString("profound statemnet", 50, 10, Graphics.HCENTER | Graphics.BASELINE);
        
    }
    
    public void commandAction(Command command, Displayable displayable){
        
        if(command == exit){
            textExample.extMID();
        }
        
    }
    
}