/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Layer;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;


/**
 * @author ZMQ-10
 */
public class Animaton extends MIDlet {
    
    Display display;
    AGameCanvas aGameCanvas;
    
    public Animaton() throws IOException{
        
        display = Display.getDisplay(this);
        aGameCanvas = new AGameCanvas(this);
        
        
    }

    public void startApp() {
        
        display.setCurrent(aGameCanvas);
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
}
class AGameCanvas extends GameCanvas implements Runnable{
    
    Animaton animaton;
    private boolean mtrucking;
    
    private LayerManager mLayerManager;
    
    private TiledLayer mAtmosphere;
    private TiledLayer mBackground;
    private int mAnimatedIndex;
    
    private Sprite hero;
    private int mState, mDirection;
    
    private static final int kStanding = 1;
    private static final int kRunning = 2;
    private static final int kLeft = 1;
    private static final int kRight = 2;
    
    private static final int[] kRunningSequence = {0, 1, 2};
    private static final int[] kStandingSequence = {3};
    
    Thread thread;
    
    public AGameCanvas(Animaton animaton) throws IOException{
        
        super(true);
        this.animaton = animaton;
        thread = new Thread(this, "Game Canvas");
        thread.start();
        
        mLayerManager = new LayerManager();
        int w = getWidth();
        int h = getHeight();
        mLayerManager.setViewWindow(96, 48, w, h);
        
        mtrucking = true;
        
        createAtmosphere();
        createBackground();
        createHero();
        
    }
    
    private void createBackground() throws IOException{
        Graphics g = getGraphics();
        Image backgroundImage = Image.createImage("/11.jpg");
        
        int[] map = {
        0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0,
        1, 2, 0, 0, 0, 0,
        3, 3, 2, 0, 0, 0,
        3, 3, 3, 2, 4, 1,
        5, 7, 4, 0, 5, 5,
        4, 4, 4, 4, 4, 4,
        6, 9, 1, 0, 0, 0,
        0, 6, 6, 6, 5, 5,
        10, 10, 10, 10, 10, 10   
    };
        
        
        mBackground = new TiledLayer(10, 6, backgroundImage, 60, 60);
        System.out.println(backgroundImage.getWidth());
        System.out.println(backgroundImage.getHeight());
        
        mBackground.setPosition(12, 0);
//        System.out.println(mBackground.getCell(5, 3));
        for(int i = 0; i < map.length; i++){
            System.out.println(map.length);
            int column = i % 10;
            int row = (i - column) % 10;
            mBackground.paint(g);
            
//            mBackground.setCell(column, row, map[i]);
            System.out.println(mBackground.getCell(5, 3));
            
        }
        
        mAnimatedIndex = mBackground.createAnimatedTile(8);
        mBackground.setCell(3, 0, mAnimatedIndex);
        mBackground.setCell(5, 0, mAnimatedIndex);
        mLayerManager.append(mBackground);
    }
    
    private void createAtmosphere() throws IOException{
        
        Image atmosphereImage = Image.createImage("/11.jpg");
        mAtmosphere = new TiledLayer(8, 1, atmosphereImage, atmosphereImage.getWidth(), atmosphereImage.getHeight());
        mAtmosphere.fillCells(0, 0, 8, 1, 1);
        mAtmosphere.setPosition(0, 192);
        mLayerManager.insert(mAtmosphere, 0);
        
    }
    
    private void createHero() throws IOException{
        
        Image heroImage = Image.createImage("/hero");
        hero = new Sprite(heroImage, 48, 48);
        hero.setPosition(96 + (getWidth() - 48) / 2, 192);
        hero.defineReferencePixel(24, 24);
        setDirection(kLeft);
        setState(kStanding);
        mLayerManager.insert(hero, 1);
        
    }
    
    public void run(){
        
        int w = getWidth();
        int h = getHeight();
        Graphics graphics = getGraphics();
        int frameCount = 0;
        int factor = 2;
        int animatedDelta = 0;
        
        
        while(mtrucking){
            
            if(isShown()){
                
                int keyStates = getKeyStates();
                
                if((keyStates & LEFT_PRESSED) != 0){
                    
                    setDirection(kLeft);
                    setState(kRunning);
                    mBackground.move(3, 0);
                    mAtmosphere.move(3, 0);
                    hero.nextFrame();
                    
                }
                else if((keyStates & RIGHT_PRESSED) != 0){
                    
                    setDirection(kRight);
                    setState(kRunning);
                    mBackground.move(-3, 0);
                    mBackground.move(-3, 0);
                    hero.nextFrame();
                    
                }
                else{
                    
                    setState(kStanding);
                    
                }
                
                frameCount++;
                if(frameCount % factor == 0){
                    
                    int delta = 1;
                    if(frameCount / factor < 10) delta = -1;
                    mAtmosphere.move(delta, 0);
                    if(frameCount / factor == 20) frameCount = 0;
                    mBackground.setAnimatedTile(mAnimatedIndex, 8 + animatedDelta++);
                    if(animatedDelta == 3) animatedDelta = 0;
                    
                }
                
                graphics.setColor(0x5b1793);
                graphics.fillRect(0, 0, w, h);
                
                mLayerManager.paint(graphics, 0, 0);
                
                flushGraphics();
                
            }
            
            int delayOfLoop = 1000 / 25;
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
    
    public void setVisible(int layerIndex, boolean  show){
        
        Layer layer = mLayerManager.getLayerAt(layerIndex);
        layer.setVisible(show);
        
    }
    
    public boolean isVisible(int layerIndex){
        
        Layer layer = mLayerManager.getLayerAt(layerIndex);
        return layer.isVisible();
        
    }
        
}
    

