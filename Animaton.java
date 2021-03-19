package awareness;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 
 */
public class Animaton extends MIDlet implements CommandListener {
//    AudioMidlet au = new AudioMidlet();
    Display display;
    AGameCanvas aGameCanvas;

    Form form;
    ImageItem splash;
    Command strtCmd, exitCmd, set;
   
    public Animaton() throws IOException{
        
        display = Display.getDisplay(this);
        form = new Form("Starter Canvas");
        aGameCanvas = new AGameCanvas(display, form);
        
        strtCmd = new Command("Start", Command.OK, 1);
        exitCmd = new Command("Exit", Command.EXIT, 1);
        form.append(new Spacer(50, 100));
        
        splash = new ImageItem("Walk bhi Awareness bhi", null, ImageItem.LAYOUT_CENTER, null);
//        splash.setDefaultCommand(
//         new Command("Set", Command.ITEM, 1)); 
//     // icl is ItemCommandListener   
//         splash.setItemCommandListener(icl);
         
        try{
            
            Image image = Image.createImage("/awareness/splash1.png");
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
//        au.playViaResource();
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
class AGameCanvas extends GameCanvas implements Runnable,CommandListener{
    
    Animaton animaton;
    BoxInterval boxInterval;
    Timer timer;
    
    Display display;
    Form form;
    int i = 0;
    
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
    String[] manMsg1 = {"Hii...Bob, How Are You?","Hello....Aamir I am fine..!","Have You heard About Vaccination in our area?","Yes, I have listen that","Good, Vaccines work by exposing you to a safe version of a disease.","Yes.","When the body responds to the vaccine","it builds an adaptive immune response","Okay Thanks for the information and i will go for a vaccination trial.","Sure...!! ", "It's like of Walk Bhi Awareness bhi", "Yeah...Okay I am Going,Good Bye.","Good Bye...!!"};
    int count = 0;
    int totalCount = manMsg1.length;
    int x1,y1,x2,y2,x3,y3;
    Command exit;
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
        
        createBackground();
        createHero();
        boxInterval = new BoxInterval(this);
        timer = new Timer();
        timer.scheduleAtFixedRate(boxInterval, 4000, 7000); 
        
        exit = new Command("Exit", Command.EXIT, 0);
        addCommand(exit);
        setCommandListener(this);
        
        
        thread = new Thread(this, "Game Canvas");
        thread.start();
        
    }
    
    private void createBackground() throws IOException{
        
        Graphics g = getGraphics();
        Image backgroundImage = Image.createImage("/awareness/background.png");
        
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
        
        
        Image heroImage = Image.createImage("/awareness/hero.png");

        bob = new Sprite(heroImage, 48, 48);
        
        
        bob.setPosition(getWidth() / 2 - 48 , 192);
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
            Graphics g = getGraphics();
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
//                    mBackground.move(-3, 0);
                    bob.nextFrame();
                    
                }
                else{
                    
                    setState(kStanding);
                    
                }
                
                
                graphics.setColor(0x5b1793);
                graphics.fillRect(0, 0, w, h);
                
                mLayerManager.paint(graphics, 0, 0);
                               
            }
            
            int delayOfLoop = 1000 /20;
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

                amirImage = Image.createImage("/awareness/hero.png");
                amir = new Sprite(amirImage, 48, 48);

                amir.setPosition(getWidth() + 96, 192);
                mLayerManager.insert(amir, 0);
                amir.setTransform(kRight);
                    
//                try {
//                    Thread.sleep(500);
//                } catch (Exception e) {
//                }
                 drawRectangle();
                 drawTriangle();
                 drawDialog();
                 
                
                 
                      
                    
//                     System.err.println("Abc");
                     
//                     try{
//                         thread.interrupt();
//                     }
//                     catch(Exception e){
//                          
//                     }
//                    
                 

                
//
//
            } catch (Exception e) {

                e.toString();

            }


       }

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

    public void drawRectangle(){
        Graphics g = getGraphics();
        g.setColor(255, 0, 0);
        g.fillRect(0, getHeight() / 2, getWidth(), getHeight() / 8);
        
    }
    
    public void drawTriangle(){
        Graphics g = getGraphics();
        g.setColor(255, 0, 0);
        g.fillTriangle(x1, y1, x2, y2, x3, y3);
        
    }
    
    public void drawDialog(){
        
        Graphics g = getGraphics();
        
//        String[] manMsg1 = {"Hii...Bob, How Are You?","Hello....Aamir I am fine..!","Have You heard About Vaccination in our area?","Yes, I have listen that","Good, Vaccines work by exposing you to a safe version of a disease.","Yes.","When the body responds to the vaccine","it builds an adaptive immune response","Okay Thanks for the information and i will go for a vaccination trial.","Sure...!! ", "It's like of Walk Bhi Awareness bhi", "Yeah...Okay I am Going,Good Bye.","Good Bye...!!"};
//        System.out.println(manMsg1[2]);
        
            
            
                g.setColor(0, 255, 0);
                g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
                g.drawString(manMsg1[i], 0, getHeight()/2, Graphics.TOP | Graphics.LEFT);
//                System.out.println(i);
//                System.out.println(manMsg1[i].length());
//                if(totalCount == 0){
//
//                    timer.cancel();
//                    Alert alert = new Alert("Animation end", "Story is End ", null, AlertType.INFO);
//                    alert.setTimeout(Alert.FOREVER);
//                    display.setCurrent(alert);
//                    System.out.println("Zaid");

//        } 
                 
                
            
            
        
    }
    
    public void commandAction(Command command, Displayable displayable){
        
        if(command == exit){
            
            Alert alert = new Alert("Animation end", "Story is End ", null, AlertType.INFO);
            alert.setTimeout(Alert.FOREVER);
            display.setCurrent(alert);
            
            
        }
        
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
        
//       aGameCanvas.x3 = aGameCanvas.getWidth()/4;
//       aGameCanvas.y3 = 3 * aGameCanvas.getHeight() / 4;
//        aGameCanvas.repaint();
//         aGameCanvas.drawDialog();
        aGameCanvas.i++;
        aGameCanvas.totalCount--;
        
        
        try {

            Thread.sleep(3200);
             aGameCanvas.x3 = aGameCanvas.getWidth()/4;
             aGameCanvas.y3 = 3 * aGameCanvas.getHeight() / 4;
             aGameCanvas.i++;
             aGameCanvas.totalCount--;
             System.out.println(aGameCanvas.totalCount);
//            aGameCanvas.repaint();
//            aGameCanvas.thread.interrupt();
//
        } catch (Exception ex) {
//
//            ex.printStackTrace();
//
        }
       

    }

}
        
    

