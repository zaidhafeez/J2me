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
public class SSCanvasExample extends MIDlet {
    
    Display display;
    SSCanvas sSCanvas;
    
    public SSCanvasExample(){
        
        display = Display.getDisplay(this);
        sSCanvas = new SSCanvas(this);
        
    }

    public void startApp() {
        
        display.setCurrent(sSCanvas);
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
    public void eexitMID(){
        
        destroyApp(false);
        notifyDestroyed();
        
    }
    
}

class SSCanvas extends Canvas implements CommandListener{
    
    Command exit;
    SSCanvasExample sSCanvasExample;
    private Image[] slides;
    private String[] captions = {"ZMQ", "Awardee",  "BROTHERS", "Initiative Network", "ZMQ Day"};
    
    private int curSlide = 0;
    
    public SSCanvas(SSCanvasExample sSCanvasExample){
        
        this.sSCanvasExample = sSCanvasExample;
        exit = exit = new Command("Exit", Command.EXIT, 1);
        addCommand(exit);
        setCommandListener(this);
        
        try{
            
            slides = new Image[5];
            slides[0] = Image.createImage("/slideshow/1.png");
            slides[1] = Image.createImage("/slideshow/2.png");
            slides[2] = Image.createImage("/slideshow/3.png");
            slides[3] = Image.createImage("/slideshow/4.png");
            slides[4] = Image.createImage("/slideshow/5.png");
            
        }
        catch(Exception e){
            Alert alert = new Alert("Failure", "Creating Image", null, null);
            alert.setTimeout(Alert.FOREVER);
            sSCanvasExample.display.setCurrent(alert);
        }
        
    }
    
    protected void paint(Graphics graphics){
        
        graphics.setColor(255, 255, 255); 
        graphics.fillRect(0, 0, getWidth(), getHeight());
        
        graphics.drawImage(slides[curSlide],  getWidth() / 2, getHeight() / 2, Graphics.HCENTER | Graphics.VCENTER);
        
        Font f = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        graphics.setFont(f);
        
        graphics.setColor(0, 0, 0); // Black
        graphics.drawString(captions[curSlide], getWidth() / 2, 0,Graphics.HCENTER | Graphics.TOP);
    
    }
    
    protected void keyPressed(int key){
        
        switch(getGameAction(key)){
            
            case Canvas.LEFT:
                if(--curSlide < 0){
                    curSlide = slides.length -1;
                }
                repaint();
                break;
                
            case Canvas.RIGHT:
                if(++curSlide >= slides.length){
                    curSlide = 0;
                }
                repaint();
                break;
        }
            
    }
    
    public void commandAction(Command command, Displayable displayable){
        
        if(command == exit){
            
            sSCanvasExample.eexitMID();
            
        }
                
    }
    
}
