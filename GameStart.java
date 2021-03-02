/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.io.IOException;

/**
 * @author ZMQ-10
 */
public class GameStart extends MIDlet implements CommandListener {

    Display display;
    Form form;
    ImageItem splash;
    Command strtCmd, exitCmd;
    GSCanvas canvas;
    
    public GameStart(){
        
        form = new Form("Starter Canvas");
        display = Display.getDisplay(this);
        canvas = new GSCanvas(display, form);
        
        strtCmd = new Command("Exit", Command.EXIT, 1);
        exitCmd = new Command("Start", Command.OK, 1);
        form.append(new Spacer(50, 100));
        
        splash = new ImageItem("Canvas Exploration", null, ImageItem.LAYOUT_CENTER, null);
        try{
            
            Image image = Image.createImage("/slideshow/1.png");
            splash.setImage(image);
            
        }
        catch(Exception e){
            
            System.out.println(e.toString());
            
        }
        form.append(splash);
        form.addCommand(strtCmd);
        form.addCommand(exitCmd);
        form.setCommandListener(this);
        
        
    }
    
    public void startApp() throws MIDletStateChangeException {
        
        display.setCurrent(form);
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) throws MIDletStateChangeException {
    }
    
    public void commandAction(Command command, Displayable displayable){
        
        if(command == strtCmd){
            
            form.deleteAll();
            canvas = new GSCanvas(display, form);
            display.setCurrent(canvas);
            System.out.println("Start Command");
            
        }
        else if(command == exitCmd){
            
            try{
                
                destroyApp(true);
                notifyDestroyed();
                
            }
            catch(Exception e){
                
                System.out.println(e.toString());
                
            }
            
        }
        
    }
    
}
