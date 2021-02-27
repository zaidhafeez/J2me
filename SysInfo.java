/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.util.*;

/**
 * @author ZMQ-10
 */
public class SysInfo extends MIDlet implements CommandListener {
    
    Display display;
    Command exit;
    Form form;
    String date, time, totalMemory, freeMemory, numColors;
    
    public SysInfo(){
        
        display = Display.getDisplay(this);
        exit = new Command("Exit", Command.EXIT, 1);
        form = new Form("SysInfo");
        
        Calendar calendar = Calendar.getInstance();
        date = Integer.toString(calendar.get(Calendar.DATE)) + ":" + Integer.toString(calendar.get(Calendar.MONTH)) + ":" + Integer.toString(calendar.get(Calendar.YEAR));
        time = Integer.toString(calendar.get(Calendar.HOUR)) + ":" + Integer.toString(calendar.get(Calendar.MINUTE)) + ":" + Integer.toString(calendar.get(Calendar.SECOND));
        
        Runtime runtime = Runtime.getRuntime();
        totalMemory = Long.toString(runtime.totalMemory());
        freeMemory = Long.toString(runtime.freeMemory());
        
        String isColor = display.isColor() ? "Yes" : "No";
        
        numColors = Integer.toString(display.numColors());
        
        form.append(new StringItem("", "Date: " + date));
        form.append(new StringItem("", "Time: " + time));
        form.append(new StringItem("", "Total Memory: " + totalMemory));
        form.append(new StringItem("", "Free Memory: " + freeMemory));
        form.append(new StringItem("", "Color: " + isColor));
        form.append(new StringItem("", "Number of Colors: " + numColors));
        
        form.addCommand(exit);
        form.setCommandListener(this);
        
    }
    
    public void startApp() {
        
        display.setCurrent(form);
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
        
        notifyDestroyed();
        
    }
    
    public void commandAction(Command command, Displayable displayable){
        
        if(command == exit){
            destroyApp(false);
        }
        
    }
}
