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
public class Sweep extends MIDlet{
    
    Display display;
    SweepCanvas sweepCanvas;
    
    
    public Sweep(){
        
        display = Display.getDisplay(this);
        sweepCanvas = new SweepCanvas(this);
        
        
    }

    public void startApp() {
        
        display.setCurrent(sweepCanvas);
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
}

class SweepCanvas extends Canvas implements Runnable,CommandListener{
    
    private boolean mTrucking;
    Sweep sweep;
    
    private int mTheta;
    private int mBorder;
    Thread thread;
    Command exit;
    
    
    public SweepCanvas(Sweep sweep){
        
        this.sweep = sweep;
        
        mTrucking = true;
        mTheta = 0;
        mBorder = 10;
        
        exit = new Command("Exit", Command.EXIT, 1);
        addCommand(exit);
        setCommandListener(this);
        
        thread = new Thread(this, "Game Thread1");
        thread.start();
        
    }
    
    public void run(){
        while(mTrucking){
            mTheta = (mTheta + 1) % 360;
            int delayOfLoop = 1000 / 25;
            long loopStartTime = System.currentTimeMillis();
            repaint();
            long loopEndTime = System.currentTimeMillis();
            int loopTime = (int)(loopEndTime - loopStartTime);
            if(loopTime < delayOfLoop){
            
                try{
            
                    Thread.sleep(delayOfLoop - loopTime);
            
                }
                catch(Exception exception){
                
                    exception.toString();
                
                }
            
            }
        
        }
    }
    
    public void paint(Graphics graphics){
        
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
    
    public void commandAction(Command command, Displayable displayable){
        
        if(command == exit){
            mTrucking = false;
            try {
                
                sweep.destroyApp(true);
                sweep.notifyDestroyed();
                
            } catch (Exception e) {
                
                e.toString();
                
            }
            
        }
        
    }
    
}
