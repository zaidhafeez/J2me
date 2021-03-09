/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import javax.microedition.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import javax.microedition.media.*;

/**
 * @author ZMQ-10
 */
public class AudioMidlet extends MIDlet implements CommandListener, Runnable{
    
    Display display;
    List mainScreen;
    
    public void startApp() {
        
        display = Display.getDisplay(this);
        
        if(mainScreen == null){
            
            mainScreen = new List("Audio Midlet", List.IMPLICIT);
            
            mainScreen.append("Via HTTP", null);
            mainScreen.addCommand((new Command("Exit", Command.EXIT, 0)));
            mainScreen.addCommand(new Command("Play", Command.SCREEN, 0));
            mainScreen.setCommandListener(this);
            
        }
        display.setCurrent(mainScreen);
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
    public void commandAction(Command command, Displayable displayable){
        
        if(command.getCommandType() == Command.EXIT){
            
            notifyDestroyed();
            
        }
        else{
            
            Form waitForm = new Form("LOADING...");
            display.setCurrent(waitForm);
            Thread thread = new Thread(this);
            thread.start();
            
        }
        
    }
    
    public void run(){
        
        String selection = mainScreen.getString(mainScreen.getSelectedIndex());
        boolean viaHTTP = selection.equals("Via HTTP");
        if(viaHTTP){
            
            playViaResource();
            
        }

    }
    
    public void playViaResource(){
        
        try {
            
//            String url = getAppProperty("https://www.zedge.net/ringtone/");
//            Player player = Manager.createPlayer(url);
//            player.start();

            InputStream in = getClass().getResourceAsStream("/tmobile_wav.wav");
            Player player = Manager.createPlayer(in, "audio/X-wav");
            player.setLoopCount(5);
            player.start();
            
        } catch (Exception e) {
            
            System.out.println(e.toString());
            
        }
        
        display.setCurrent(mainScreen);
        
        
    }
    
    
}
