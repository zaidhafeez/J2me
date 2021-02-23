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
public class FirstProgram extends MIDlet implements CommandListener {
    
    private Display display;
    private TextBox textBox;
    private Command quitCommand;
    
    public void startApp() {
        display = Display.getDisplay(this);
        quitCommand = new Command("QUIT", Command.SCREEN, 1);
        textBox = new TextBox("WELCOME", "hello world", 40, 0);
        textBox.addCommand(quitCommand);
        textBox.setCommandListener(this);
        display.setCurrent(textBox);
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
    public void commandAction(Command choice, Displayable displayable){
        if(choice == quitCommand){
            destroyApp(false);
            notifyDestroyed();
        }
    }
    
}
