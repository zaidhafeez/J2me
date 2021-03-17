/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package awareness;

import java.io.IOException;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Layer;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;
import javax.microedition.midlet.*;

/**
 * @author ZMQ-18
 */
public class Animated extends MIDlet {
//      AudioMidlet au = new AudioMidlet();
    Display display;
    AGameCanvas1 aGameCanvas1;
   
    
    public Animated() throws IOException{
        
        display = Display.getDisplay(this);
        aGameCanvas1 = new AGameCanvas1(this);
        
        
        
    }

    public void startApp() {
//        au.playViaResource();
        display.setCurrent(aGameCanvas1);
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
}
class AGameCanvas1 extends GameCanvas implements Runnable{
    private Layer stringLayer;
    Animated animated;
    private boolean mtrucking;
    
    private LayerManager mLayerManager;
    
//    private TiledLayer mAtmosphere;
    private TiledLayer mBackground;
//    private int mAnimatedIndex;
    
    private Sprite hero;
    private int mState, mDirection;
    
    private static final int kStanding = 1;
    private static final int kRunning = 2;
    private static final int kLeft = 1;
    private static final int kRight = 2;
    
    private static final int[] kRunningSequence = {0, 1, 2};
    private static final int[] kStandingSequence = {3};
     BoxCanvas boxCanvas;
    Image semi;
    Sprite s1;
    
    Thread thread;
    
    public AGameCanvas1(Animated animated) throws IOException{
        
        super(true);
        this.animated = animated;
        
        boxCanvas = new BoxCanvas();
//        stringLayer = new Layer(70, 70);
         
        mLayerManager = new LayerManager();
        int w = getWidth();
        int h = getHeight();
        mLayerManager.setViewWindow((getWidth() / 3), 0, w, h);
        
        mtrucking = true;
        
//        createAtmosphere();
        createBackground();
        createHero();
        
        
        
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
//        System.out.print(mBackground.getColumns());
//        System.out.println(mBackground.getRows());
//        System.out.println(backgroundImage.getWidth());
//        System.out.println(backgroundImage.getHeight());
        
        mBackground.setPosition(0, 0);
        
//        System.out.println(mBackground.getCell(5, 3));
//        for(int i = 0; i < map.length; i++){
////            System.out.println(map.length);
//            int column = i %10;
//            int row = (i - column) / 10;
//            boxCanvas.rendor(g);
            mBackground.paint(g);

            for (int row=0; row<10; row++) {
                for (int col=0; col<10; col++) {
//            
            mBackground.setCell(col, row, map[row][col]);
            
        }
        
//        mAnimatedIndex = mBackground.createAnimatedTile(20);
//        mBackground.setCell(5, 0, mAnimatedIndex);
//        mBackground.setCell(7, 0, mAnimatedIndex);
                
        mLayerManager.append(mBackground);
        
    }
   }
    
//    private void createAtmosphere() throws IOException{
//        
//        Image atmosphereImage = Image.createImage("/sky.png");
//        mAtmosphere = new TiledLayer(8, 1, atmosphereImage, atmosphereImage.getWidth(), atmosphereImage.getHeight());
//        mAtmosphere.fillCells(0, 0, 8, 1, 1);
//        mAtmosphere.setPosition(0, 192);
//        mLayerManager.insert(mAtmosphere, 0);
//        
//    }
    
    private void createHero() throws IOException{
        
        
        Image heroImage = Image.createImage("/awareness/hero.png");
        int w = heroImage.getWidth();
        int h = heroImage.getHeight();
//        int rgb[] = new int[w * h];
//
//        for (int i = 0; i < w * h; i++) {
//            rgb[i] = 0x50999999;
//        }
        
//        semi = Image.createRGBImage(rgb, w, h, true);
        
        hero = new Sprite(heroImage, 48, 48);
        
        
        hero.setPosition((getWidth() - 48) / 2, 192);
        hero.defineReferencePixel(heroImage.getWidth() / 2, heroImage.getHeight() / 2);
        setDirection(kLeft);
        setState(kStanding);
       
        mLayerManager.insert(hero, 0);
        
        
    }
   
    public void run(){
        
        int w = getWidth();
        int h = getHeight();
        Graphics graphics = getGraphics();
        int frameCount = 0;
        int factor = 2;
        int animatedDelta = 0;
        
        
        while(mtrucking){
            checkBoundary();
            
            if(isShown()){
                
                int keyStates = getKeyStates();
                
                if((keyStates & LEFT_PRESSED) != 0){
                    
                    setDirection(kLeft);
                    setState(kRunning);
                    mBackground.move(3, 0);
//                    mAtmosphere.move(3, 0);
                    hero.nextFrame();
//                    playViaResource();
                    
                    
                }
                else if((keyStates & RIGHT_PRESSED) != 0){
                    
                    setDirection(kLeft);
                    setState(kRunning);
                    mBackground.move(3, 0);
//                    mBackground.move(-3, 0);
                    hero.nextFrame();
                    
                }
                else{
                    
                    setState(kStanding);
                    
                }
                
                frameCount++;
                if(frameCount % factor == 0){
                    
                    int delta = 1;
                    if(frameCount / factor < 10) delta = -1;
                    
//                    mAtmosphere.move(delta, 0);
//                    mBackground.move(delta, 0);
//                    hero.setFrameSequence(kRunningSequence);
//                    hero.move(3, 0);
                    
                    if(frameCount / factor == 20) frameCount = 0;
//                    mBackground.setAnimatedTile(mAnimatedIndex, 8 + animatedDelta++);
                    if(animatedDelta == 3) animatedDelta = 0;
                    
                }
                
                graphics.setColor(0x5b1793);
                graphics.fillRect(0, 0, w, h);
                
                mLayerManager.paint(graphics, 0, 0);
                
                
                flushGraphics();
                
            }
            
            int delayOfLoop = 1000 /25;
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
            
            hero.setTransform(Sprite.TRANS_MIRROR);
            
        }
        else if(mDirection == kRight){
            
            hero.setTransform(Sprite.TRANS_NONE);
            
        }
        mDirection = newDirection;
        
    }
    
    public void setState(int newState){
        
        if(newState == mState)return;
        switch(newState){
            
            case kStanding:
                hero.setFrameSequence(kStandingSequence);
                hero.setFrame(0);
                break;
            case kRunning:
                hero.setFrameSequence(kRunningSequence);
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
    
    public void checkBoundary(){
        Graphics g = getGraphics();
        if(mBackground.getX() > getWidth() / 3){
            
           
               
               mBackground.setPosition(0, 0);
//               animated.display.setCurrent(boxCanvas);
                
               
               try {
                semi = Image.createImage("/awareness/hero.png");
                s1 = new Sprite(semi, 48, 48);
                s1.setPosition(getWidth(), 192);
                 mLayerManager.insert(s1, 0);
            } catch (Exception e) {
            }
              
           
        }
        
           
       
    }
      public void rendor(Graphics g){
        
        g.setColor(255,255,255);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(255,0,0);
        g.fillRect(getWidth() / 2, getHeight() / 2 , getWidth()/2, getHeight()/8);
        g.fillTriangle(getWidth() / 2, getHeight() / 2 + getHeight() / 8 , (getWidth() / 2) + getWidth() / 6 , getHeight() / 2 + getHeight() / 8, (getWidth() / 2) + (getWidth() / 4), 3 * (getHeight() / 4));
//        g.drawLine(getWidth() /2, getHeight() / 2 + getHeight() / 8,(getWidth() / 2) + getWidth() / 6 , getHeight() / 2 );
        g.setColor(0, 255, 0);
        String str = "Hello How Are You";
        g.drawString(str, getWidth() / 2 + 5, getHeight() / 2 + 2, Graphics.TOP | Graphics.LEFT);
    }
        
        
    
    
}


    
                   
//    public void playViaResource(){
//        
//        try {
//            
////            String url = getAppProperty("https://www.zedge.net/ringtone/");
////            Player player = Manager.createPlayer(url);
////            player.start();
//
//            InputStream in = getClass().getResourceAsStream("/tmobile_wav.wav");
//            Player player = Manager.createPlayer(in, "audio/X-wav");
//            player.setLoopCount(5);
//            player.start();
//            
//        } catch (Exception e) {
//            
//            System.out.println(e.toString());
//            
//        }
//    }
    
//    public Design() {
//    try {
//
//        image = Image.createImage("asc.png");
//        int w = image.getWidth();
//        int h = image.getHeight();
//        int rgb[] = new int[w * h];
//
//        for (int i = 0; i < w * h; i++) {
//            rgb[i] = 0x50999999;
//        }
//
//        semi = Image.createRGBImage(rgb, w, h, true);
//
//
//    } catch (IOException ex) {
//        ex.printStackTrace();
//    }
//}
    


    




