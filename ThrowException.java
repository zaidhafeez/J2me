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
public class ThrowException extends MIDlet implements CommandListener {
    
    private Display display;
    private Form form;
    private Command exit;
    private boolean isSafeToQuit;
    
    public ThrowException(){
        isSafeToQuit = false;
        display = Display.getDisplay(this);
        exit = new Command("Exit", Command.SCREEN, 1);
        form = new Form("Throw Exception");
        form.addCommand(exit);
        form.setCommandListener(this);
    }
    
    public void startApp() {
        display.setCurrent(form);
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) throws MIDletStateChangeException{
        if(unconditional == false){
            throw new MIDletStateChangeException();
        }
    }
    
    public void commandAction(Command command, Displayable displayable){
        if(command == exit){
            try{
                if(isSafeToQuit == false){
                    StringItem msg = new StringItem("Busy", "Please Try Again.");
                    form.append(msg);
                    destroyApp(false);
                }
                else{
                    destroyApp(true);
                    notifyDestroyed();
                }
            }
            catch(MIDletStateChangeException exception){
                isSafeToQuit = true;
            }
        }
    }
}
