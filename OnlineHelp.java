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
public class OnlineHelp extends MIDlet implements CommandListener {
    
    private Display display;
    private Form form;
    private TextBox helpmesg;
    private Command back, help, exit;
    
    public OnlineHelp(){
        display = Display.getDisplay(this);
        back = new Command("Back", Command.BACK, 2);
        exit = new Command("Exit", Command.EXIT, 1);
        help = new Command("Help", Command.HELP, 3);
        form = new Form("Online Help Example");
        helpmesg = new TextBox("Online Help", "Press Back to return to the previous screen or press Exit to close this program.", 81, 0);
        helpmesg.addCommand(back);
        form.addCommand(exit);
        form.addCommand(help);
        form.setCommandListener(this);
        helpmesg.setCommandListener(this);
    }
    
    public void startApp() {
        display.setCurrent(form);
        
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
    
    public void commandAction(Command command, Displayable displayable){
        if(command == back){
            display.setCurrent(form);
        }
        else if(command == exit){
            destroyApp(false);
            notifyDestroyed();
        }
        else if(command == help){
            display.setCurrent(helpmesg);
        }
    }
}
