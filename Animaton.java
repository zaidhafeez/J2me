/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;


/**
 * @author ZMQ-10
 */
public class Animaton extends MIDlet implements CommandListener{
    
    Display display;
    Form form;
    ImageItem splash;
    Command strtCmd, exitCmd;
    AGameCanvas aGameCanvas;
   
    BoxInterval boxInterval;
    
    public Animaton() throws IOException{
        
        display = Display.getDisplay(this);
        aGameCanvas = new AGameCanvas(display, form);
        
        form = new Form("Play Anmation");
        
        strtCmd = new Command("Start", Command.OK, 1);
        exitCmd = new Command("Exit", Command.EXIT, 1);
        form.append(new Spacer(50, 100));
        
        splash = new ImageItem(null, null, Item.LAYOUT_CENTER, null);
        
        try{
            
            Image image = Image.createImage("/splash1.png");
            splash.setImage(image);
         
        }
        catch(IOException e){
            
            System.out.println(e.toString());
            
        }
        
        form.append(splash);
        form.addCommand(strtCmd);
        form.addCommand(exitCmd);
        form.setCommandListener(this);
        
    }

    public void startApp() {
        
//       AudioMidlet au = new AudioMidlet();
        display.setCurrent(form);
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
    public void commandAction(Command command, Displayable displayable){
        
        if(command == strtCmd){
            
            form.deleteAll();
            try {
                
                aGameCanvas = new AGameCanvas(display, form);
                
            } catch (IOException ex) {
                
                ex.printStackTrace();
                
            }
            
            display.setCurrent(aGameCanvas);
//            System.out.println("Start Command");
            
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
class AGameCanvas extends GameCanvas implements Runnable{
    
    Animaton animaton;
    BoxInterval boxInterval;
    Timer timer;
    private Display display;
    private Form form;
    
    private boolean mtrucking;
    
    private LayerManager mLayerManager;
    
    private TiledLayer mBackground;
    
    private Sprite bob;
    private Sprite amir;
    private int mState, mDirection;
    
    private static final int kStanding = 1;
    private static final int kRunning = 2;
    private static final int kLeft = 1;
    private static final int kRight = 2;
    
    private static final int[] kRunningSequence = {0, 1, 2};
    private static final int[] kStandingSequence = {3};
    int count = 0;
    
    int x1, y1,x2,y2,x3,y3;
    
    Thread thread;
    
    public AGameCanvas(Display start, Form stform) throws IOException{
        
        super(true);

        form = stform;
        display = start;
        
        mLayerManager = new LayerManager();
        int w = getWidth();
        int h = getHeight();
        mLayerManager.setViewWindow((getWidth() / 3), 0, w, h);
        
        mtrucking = true;
        
        thread = new Thread(this, "Game Canvas");
        thread.start();
        createBackground();
        createHero();
        
       

    }
    
    private void createBackground() throws IOException{
        
        Graphics g = getGraphics();
        Image backgroundImage = Image.createImage("/background.png");
        
        int[][] map =  {{1,2,3,4,5,6,7,8,9,10},
                       {11,12,13,14,15,16,17,18,19,20},
                       {21,22,23,24,25,26,27,28,29,30},
                       {31,32,33,34,35,36,37,38,39,40},
                       {41,42,43,44,45,46,47,48,49,50},
                       {51,52,53,54,55,56,57,58,59,60},
                       {61,62,63,64,65,66,67,68,69,70},
                       {71,72,73,74,75,76,77,78,79,80},
                       {81,82,83,84,85,86,87,88,89,90},
                       {91,92,93,94,95,96,97,98,99,100},
        };
        
        mBackground = new TiledLayer(10, 10, backgroundImage, 32, 32);
        mBackground.setPosition(0, 0);
        mBackground.paint(g);

        for (int row=0; row<10; row++) {
            for (int col=0; col<10; col++) {

                mBackground.setCell(col, row, map[row][col]);
            
            }
            
        mLayerManager.append(mBackground);
        
        }
        
    }
    
    private void createHero() throws IOException{

        Graphics g = getGraphics();
        Image heroImage = Image.createImage("/hero.png");

        bob = new Sprite(heroImage, 48, 48);
        bob.setPosition((getWidth()/3), 192);
        bob.defineReferencePixel(heroImage.getWidth() / 2, heroImage.getHeight() / 2);
        setDirection(kLeft);
        setState(kStanding);
       
        mLayerManager.insert(bob, 0);
       
    }
   
    public void run(){
        
        int w = getWidth();
        int h = getHeight();
        Graphics graphics = getGraphics();

        while(mtrucking){
           
            checkBoundary();
            
            
            if(isShown()){
                
                int keyStates = getKeyStates();
                
                if((keyStates & LEFT_PRESSED) != 0){
                    
                    setDirection(kLeft);
                    setState(kRunning);
                    mBackground.move(-3, 0);
                    bob.nextFrame();
                    
                    
                }
                else if((keyStates & RIGHT_PRESSED) != 0){
                    
                    setDirection(kLeft);
                    setState(kRunning);
                    mBackground.move(-3, 0);
                    bob.nextFrame();
                    
                }
                else{
                    
                    setState(kStanding);
                    
                }
                
                graphics.setColor(0x5b1793);
                graphics.fillRect(0, 0, w, h);
                
                mLayerManager.paint(graphics, 0, 0);
                
            }
            
            int delayOfLoop = 1000 /8;
            long startLoopTime = System.currentTimeMillis();
            
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
    
    public void setDirection(int newDirection){
        
        if(newDirection == mDirection)return;
        if(mDirection == kLeft){
            
            bob.setTransform(Sprite.TRANS_MIRROR);
            
        }
        else if(mDirection == kRight){
            
            bob.setTransform(Sprite.TRANS_NONE);
            
        }
        mDirection = newDirection;
        
    }
    
    public void setState(int newState){
        
        if(newState == mState)return;
        switch(newState){
            
            case kStanding:
                bob.setFrameSequence(kStandingSequence);
                bob.setFrame(0);
                break;
            case kRunning:
                bob.setFrameSequence(kRunningSequence);
                break;
            default:
                break;
            
        }
        
        mState = newState;
        
    }
    
//    public void setVisible(int layerIndex, boolean  show){
//        
//        Layer layer = mLayerManager.getLayerAt(layerIndex);
//        layer.setVisible(show);
//        
//    }
//    
//    public boolean isVisible(int layerIndex){
//        
//        Layer layer = mLayerManager.getLayerAt(layerIndex);
//        return layer.isVisible();
//        
//    }
    
    public boolean checkBoundary(){
        Image amirImage;
        Graphics g = getGraphics();
        
        if(mBackground.getX() + getWidth() < getWidth()){
            
            mBackground.setPosition(getWidth()/3, 0);
            count++;   
            
           
            
        }
        if(count == 2){
           
             
            try {

                amirImage = Image.createImage("/hero.png");
                amir = new Sprite(amirImage, 48, 48);

                amir.setPosition(getWidth() + 96, 192);
                mLayerManager.insert(amir, 0);
                amir.setTransform(kRight);



                boxInterval = new BoxInterval(this);
                timer = new Timer();
                timer.schedule(boxInterval, 500, 4000); 

                try{
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.toString();
                }


            } catch (Exception e) {

                e.toString();

            }
           
      
       }
//        try{
//            Thread.sleep(100000);
//        } catch (Exception e) {
//            e.toString();
//        }
       flushGraphics();
       
        return true;
    }
    

    public void trianglePosition(){
        
        x1 = getWidth() / 2; 
        y1 = getHeight() / 2 + getHeight() / 8;
        x2 = getWidth() / 2 + getWidth() / 6;
        y2 = getHeight() / 2 + getHeight() / 8;
        x3 = (getWidth() / 2) + (getWidth() / 4);
        y3 =  3 * (getHeight() / 4); 
        
    }
    
    public void paint(Graphics g){
        
        g.setColor(255,0,0);
        g.fillRect(getWidth() / 2, getHeight() / 2 , getWidth()/2, getHeight()/8);
        g.fillTriangle(x1, y1, x2, y2, x3, y3);
        g.setColor(0, 255, 0);
        String bobMsg = "Hello How Are You";
        g.drawString(bobMsg, getWidth() / 2 + 5, getHeight() / 2 + 2, Graphics.TOP | Graphics.LEFT);
        
    }
      
}
class BoxInterval extends TimerTask{
    AGameCanvas aGameCanvas;
    
    public BoxInterval(AGameCanvas aGameCanvas)
    {
        this.aGameCanvas=aGameCanvas;
       
    }

    public void run() {
        
        aGameCanvas.trianglePosition();
        aGameCanvas.repaint();
       
        try {
            
            Thread.sleep(1500);
            aGameCanvas.x3 = aGameCanvas.getWidth()/4;
            aGameCanvas.y3 = 3 * aGameCanvas.getHeight() / 4;
            aGameCanvas.repaint();
            
        } catch (InterruptedException ex) {
            
            ex.printStackTrace();
            
        }
        
    }
    
}
    

