/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.util.*;
import java.io.IOException;
import javax.microedition.m3g.RayIntersection;

/**
 * @author ZMQ-10
 */
public class MovingObject extends MIDlet implements CommandListener {
    
    
    Form form;
    Display display;
    ImageItem splash;
    MovingCanvas movingCanvas;
    Command exit, start;
    
    
    public MovingObject(){
        
        form = new Form("Canvas Starter");
        display = Display.getDisplay(this);
        movingCanvas = new MovingCanvas(display, form);
        start = new Command("Start", Command.OK, 1);
        exit = new Command("Exit", Command.EXIT, 1);
        form.append(new Spacer(50, 100));
        splash = new ImageItem("Moving Object", null, ImageItem.LAYOUT_CENTER, null);
        try {
            
            Image image = Image.createImage("/slideshow/1.png");
            splash.setImage(image);
            
        } catch (Exception e) {
            
            System.out.println(e.toString());
            
        }
        
        form.append(splash);
        form.addCommand(exit);
        form.addCommand(start);
        form.setCommandListener(this);
        
    }
    
    public void startApp() {
        
        display.setCurrent(form); 
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
    public void commandAction(Command command, Displayable displayable){
        
        if(command == start){
            
            form.deleteAll();
            try{
                
                movingCanvas = new MovingCanvas(display, form);
                
            }catch(Exception e){
                
                e.printStackTrace();
                
            }
            display.setCurrent(movingCanvas);
            System.out.println("Start Command");
            
        }
        if(command == exit){
            
            try{
                
                destroyApp(true);
                notifyDestroyed();
                
            }catch(Exception e){
                
                System.out.println(e.toString());
                
            }
            
        }
        
    }
}

class MovingCanvas extends Canvas implements Runnable, CommandListener{
    
    Command exit;
    Display display;
    Form form;
    int XPosition;
    int YPosition;
    int width;
    int height;
    int xVelocity, yVelocity;
    int xBounds, yBounds, wBounds, hBounds;
    int positionChange;
    final int BA_BOUNCE = 1;
    int score;
    int distance;
    static int xTest, yTest,wTest,hTest;
    
    
    
    Thread thread;
    
    public MovingCanvas(Display start, Form stForm){
        
        exit = new Command("Exit", Command.EXIT, 1);
        form = stForm;
        display = start;
        
        XPosition = getWidth() / 3;
        YPosition = getHeight() / 3;
        
        width = getWidth() / 5;
        height = getHeight() /5; 
        
        xVelocity = 2;
        yVelocity = 2;
        
        xBounds = 0;
        yBounds = 0;
        
        wBounds = getWidth();
        hBounds = getHeight();
        
        xTest = getWidth()/16;
        yTest = getHeight()/16;
        wTest = getWidth()/5;
        hTest = getWidth()/5;
        
        score = 0;
        
        addCommand(exit);
        setCommandListener(this);
        
        thread = new Thread(this, "Game Thread");
        thread.start();
        
    }

    public void run() {
        while (true) { 
            
            int delayOfLoop = 1000/25;
            long loopStartTime = System.currentTimeMillis();
            runGame();
            long loopEndTime = System.currentTimeMillis();
            int loopTime = (int)(loopEndTime - loopStartTime);
            if(loopTime < delayOfLoop){
                
                try{
                    
                    thread.sleep(delayOfLoop - loopTime);
                    
                }catch(Exception e){
                    e.toString();
                }
                
            }
        }
        
    }

    protected  void paint(Graphics graphics){
        
        graphics.setColor(255, 255, 255);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.setColor(0, 255, 0);
        graphics.fillArc(XPosition, YPosition, width, height, 0, 360);
        graphics.setColor(255,0,0);
        
        graphics.fillArc(xTest, yTest, wTest, hTest, 0, 360);
//        graphics.drawString("Score", score, 10, 10);
        
        
        showScore(graphics);
        
    }
    
    void showScore(Graphics graphics){
        
        graphics.setColor(255,255,255);
        
        String score1 = String.valueOf(score);
        
        graphics.setColor(255, 0, 0);
        graphics.drawString("Score : " + score1, getWidth()/3, 0,0);
//        if(isIntersection(xTest, yTest, wTest, hTest)){
//           score += 5;
//        }
        
    }
    
    protected void runGame(){
        checkBoundaries();
        getDistance();
        
        switch(positionChange){
            case LEFT:
                XPosition -= xVelocity;
                break;
            case RIGHT:
                 XPosition += xVelocity;
                break;
            case UP:
                YPosition = YPosition - yVelocity;
            case DOWN:
                YPosition = YPosition + yVelocity;
            
            
        }
        repaint();
    }
    
    public void checkBoundaries(){
        //bounce
        if(XPosition < xBounds){
            XPosition = xBounds;
            xVelocity = -xVelocity;
        }
        else if((XPosition + getWidth()/5) > (xBounds + wBounds)){
            
            XPosition = xBounds + wBounds - getWidth()/5;
            xVelocity = -xVelocity;
            
        }
        if(YPosition < yBounds){
            YPosition = yBounds;
            yVelocity = -yVelocity;
        }
        else if(YPosition + getHeight()/5 > (yBounds + hBounds)){
            
            YPosition = yBounds + hBounds - getHeight()/5;
            yVelocity = -yVelocity;
            
        }
        //wrap
//        if((XPosition + getWidth()/2) < xBounds){
//            
//            XPosition = xBounds + wBounds;
//            
//        }
//        else if(XPosition > (xBounds + wBounds)){
//            
//            XPosition = xBounds- getWidth()/2;
//            
//        }
//        if((YPosition + getHeight()/2) < yBounds){
//            
//            YPosition = yBounds + hBounds;
//            
//        }
//        else if(YPosition > (yBounds + hBounds)){
//            
//            YPosition = yBounds - getWidth()/2;
//            
//        }
        //kill
//        if((XPosition + getWidth()/2) < xBounds || XPosition > (xBounds + wBounds) || (YPosition + getHeight()/2) < yBounds || YPosition > (yBounds + hBounds)){
//            
//        }
//          // Stop
//          if(XPosition < xBounds || XPosition > (xBounds + wBounds - getWidth()/2)){
//              
//              XPosition = Math.max(xBounds, Math.min(XPosition, (xBounds + wBounds - getWidth()/2)));
//              xVelocity = 0;
//              yVelocity = 0;
//          }
//          
//          if(YPosition < yBounds || YPosition > (yBounds + hBounds - getHeight()/2)){
//              
//              YPosition = Math.max(yBounds, Math.min(YPosition, (yBounds + hBounds - getHeight()/2)));
//              xVelocity = 0;
//              yVelocity = 0;
//              
//          }
        
    }
    
    void getDistance(){
        
        int x1 = XPosition;
        int y1 = YPosition;
        int x2 = xTest;
        int y2 = yTest;
        
        int r1 = width / 2;
        int r2 = wTest / 2;
        
        int dx = x2 - x1;
        int dy = y2 - y1;
        
        isIntersection(dx, dy, r1, r2);
        
        
    }
    
    public boolean isIntersection(int x, int y, int r11, int r22){
        int a = x;
        int b = y;
        int r1 = r11;
        int r2 = r22;
        distance = (int)Math.sqrt((a * a) + (b * b));
        if(distance < r1 + r2){
            
            
            score += 5;
            stop(r11, r22);
            return true;
            
        }
        return false;
        
    }
    
    public void stop(int a, int b){

        if((wTest + width) == (2 * a) + (2 * b))
        {
           
            Alert alert = new Alert("Game over", "score: " + score , null, null);
            alert.setTimeout(Alert.FOREVER);
            display.setCurrent(alert);
             try{
                Thread.sleep(100000);
            }
            catch(InterruptedException e){
                e.toString();
            }
        }
        
        
        
        
    }
            
                   
    
    
    
    
    
    
    public void keyPressed(int keyCode){
        
        switch(getGameAction(keyCode)){
            
            case LEFT:
                positionChange  = LEFT;
                break;
            case RIGHT:
                positionChange = RIGHT;
                break;
            case UP:
                positionChange = UP;
            case DOWN:
                positionChange = DOWN;
                
            
        }
        
    }
    
    public void commandAction(Command command, Displayable displayable){
        
        if(command == exit){
            
            display.setCurrent(form);
            form.append("Game Over");
            
        }
        
    }
      
    
    
    
    
}
