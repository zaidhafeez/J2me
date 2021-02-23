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
public class LineCanvas extends MIDlet implements CommandListener {
    
    private Display display;
    private Form form;
    private Command ok, exit;
    private CanvasLine canvasLine;
    
    public LineCanvas(){
        canvasLine = new CanvasLine();
        display = Display.getDisplay(this);
        form = new Form("Line Draw");
        ok = new Command("Ok", Command.OK, 2);
        exit = new Command("Exit", Command.EXIT, 1);
        form.addCommand(ok);
        form.addCommand(exit);
        form.setCommandListener(this);
        
    }
    
    public void startApp() {
        display.setCurrent(canvasLine);
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
        notifyDestroyed();
    }
    
    public void commandAction(Command command, Displayable displayable){
        if(command == ok){
            display.setCurrent(form);
        }
        else if(command == exit){
            destroyApp(true);
        }
    }
    
    class CanvasLine extends Canvas implements CommandListener{
        
        Command back;
        
        public CanvasLine(){
            back = new Command("Back", Command.SCREEN, 1);
            addCommand(back);
            setCommandListener(this);
        }
        
        public void paint(Graphics graphics){
            graphics.setColor(255, 162, 177);
            graphics.fillRect(0, 0, getWidth(), getHeight());
            
            graphics.setColor(0, 0, 255);
            graphics.drawLine(0, getHeight()/2, getWidth()-1, getHeight()/2);
            
            graphics.setColor(0, 0, 255);
            graphics.drawLine(0, 0, getWidth()-1, getHeight()-1);
            
            graphics.setStrokeStyle(Graphics.DOTTED);
            graphics.setColor(0xFFFF00);
            graphics.drawLine(0, getHeight()/4, getWidth()-1, getHeight()/4);
            
        }
    
        public void commandAction(Command command, Displayable displayable){
            if(command  == back){
                display.setCurrent(this);
            }
        }
        
    }  
}
