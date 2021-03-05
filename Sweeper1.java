/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.midlet.*;

/**
 * @author ZMQ-10
 */
public class Sweeper1 extends MIDlet implements CommandListener{

    Display display;
    SweepCanvas1 sweepCanvas1;
    Command exit;
    
    
    public Sweeper1(){
        
        display = Display.getDisplay(this);
        sweepCanvas1 =new SweepCanvas1();
        
        exit = new Command("Exit", Command.EXIT, 1);
        sweepCanvas1.addCommand(exit);
        sweepCanvas1.setCommandListener(this);
        
    }
    
     public void startApp() {
        
        display.setCurrent(sweepCanvas1);
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
    public void commandAction(Command command, Displayable displayable){
        
        if(command == exit){
            
            sweepCanvas1.mTrucking = false;
            destroyApp(false);
            notifyDestroyed();
            
        } 
    }
}
class SweepCanvas1 extends GameCanvas implements Runnable {
    
    boolean mTrucking;
    int mTheta;
    int mBorder;
    Sweeper1 sweeper;
    Thread thread;
    
    
    public SweepCanvas1(){
        
        super(true);
        
//        this.sweeper = sweeper;
        mTrucking = true;
        mTheta = 0;
        mBorder = 10;
        
        thread = new Thread(this, "Game Thread");
        thread.start();
        
    }
    
    public void run(){
       
        Graphics g = getGraphics();
        
        while(mTrucking){
            
            mTheta = (mTheta + 1) % 360;
            int delayOfLoop = 1000/25;
            long startLoopTime = System.currentTimeMillis();
            rendor(g);
            flushGraphics();
            long endLoopTime = System.currentTimeMillis();
            int loopTime = (int)(endLoopTime - startLoopTime);
            if(loopTime < delayOfLoop){
                
                try{
                    
                    Thread.sleep(delayOfLoop - loopTime);
                    
                }
                catch(Exception e){
                    
                    e.toString();
                    
                }
                
            }
            
        }
        
    }
    
    public void rendor(Graphics graphics){
        
        int width = getWidth() - 1;
        int height = getHeight() - 1;
        
        graphics.setGrayScale(255);
        graphics.fillRect(0, 0, width, height);
        
        int x = mBorder;
        int y = mBorder;
        
        int w = width - mBorder * 2;
        int h = height - mBorder * 2;
        
        for(int i = 0; i < 8; i++){
            graphics.setGrayScale((8 - i) * 32 - 16);
            graphics.fillArc(x, y, w, h, mTheta + i * 10, 10);
            graphics.fillArc(x, y, w, h, (mTheta + 180) % 360 + i * 10, 10);
            
        }     
        
    }
}


