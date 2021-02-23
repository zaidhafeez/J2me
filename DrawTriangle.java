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
public class DrawTriangle extends MIDlet implements CommandListener {
    
    private Display display;
    private List list;
    private Command ok, exit;
    private CanvasDraw canvasDraw;
    
    public DrawTriangle(){
        canvasDraw = new CanvasDraw();
        display = Display.getDisplay(this);
        list = new List("Draw Triangle", List.IMPLICIT);
        ok = new Command("Ok", Command.OK, 2);
        exit = new Command("Exit", Command.EXIT, 1);
        list.append("Draw Triangle", null);
        list.addCommand(ok);
        list.addCommand(exit);
        list.setCommandListener(this);
    }
    
    public void startApp() {
        display.setCurrent(list);
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
        notifyDestroyed();
    }
    
    public void commandAction(Command command, Displayable displayable){
        int listItemIndex = list.getSelectedIndex();
        if(command == ok){
            display.setCurrent(canvasDraw);
        }
        else if(command == exit){
            destroyApp(true);
        }
    }

class CanvasDraw extends Canvas implements CommandListener{
    
    Command back;
    
    public CanvasDraw(){
        back = new Command("Back", Command.BACK, 1);
        addCommand(back);
        setCommandListener(this);
    }
    
    public void paint(Graphics graphics){
        graphics.setColor(0, 0, 255);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.setColor(255, 0, 0);
        
        graphics.drawString("Draw Triangle", getWidth()/2, 5, Graphics.HCENTER | Graphics.TOP);
        graphics.fillTriangle(getWidth()/4, 100, 90, 180, 180, 90);
    }
    
    public void commandAction(Command command, Displayable displayable){
            if(command == back){
                display.setCurrent(list);
            }
        }
    }
}
