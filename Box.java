package awareness;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.GameCanvas;

/**
 * @author ZMQ-18
 */
//public class Box extends MIDlet {
//    
//    Display display;
//    BoxCanvas boxCanvas;
//    
//    public Box(){
//        
//        display = Display.getDisplay(this);
//        boxCanvas = new BoxCanvas();
//    }
//    
//    public void startApp() {
//        
//        display.setCurrent(boxCanvas);
//        
//    }
//    
//    public void pauseApp() {
//    }
//    
//    public void destroyApp(boolean unconditional) {
//        
//        notifyDestroyed();
//        
//    }
//}
class BoxCanvas extends GameCanvas implements Runnable{
    
    Thread thread;
    
    public BoxCanvas(){
        
        super(true);
        thread = new Thread(this);
        thread.start();
        
    }
    
    public void run(){
        
        Graphics g = getGraphics();
        
        while (true) {            
            
            int a = 20;
//            rendor(g);
            try {
                Thread.sleep(a);
            } catch (Exception e) {
            }
            
        }
        
        
    }
    
   
    
   
    
    
    
}
