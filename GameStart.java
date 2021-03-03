/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.io.IOException;
import java.util.*;


/**
 * @author ZMQ-10
 */
public class GameStart extends MIDlet implements CommandListener {

    Display display;
    Form form;
    ImageItem splash;
    Command strtCmd, exitCmd;
    GSCanvas1 canvas;
    
    public GameStart() throws IOException{
        
        form = new Form("Starter Canvas");
        display = Display.getDisplay(this);
        canvas = new GSCanvas1(display, form);
        
        strtCmd = new Command("Start", Command.OK, 1);
        exitCmd = new Command("Exit", Command.EXIT, 1);
        form.append(new Spacer(50, 100));
        
        splash = new ImageItem("Canvas Exploration", null, ImageItem.LAYOUT_CENTER, null);
        try{
            
            Image image = Image.createImage("/slideshow/1.png");
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
    
    public void startApp() throws MIDletStateChangeException {
        
        display.setCurrent(form);
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) throws MIDletStateChangeException {
    }
    
    public void commandAction(Command command, Displayable displayable){
        
        if(command == strtCmd){
            
            form.deleteAll();
            try {
                canvas = new GSCanvas1(display, form);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            display.setCurrent(canvas);
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

class GSCanvas1 extends Canvas implements Runnable, CommandListener{
    
    private Random random;
    private Graphics buffer;
    private Image movingImage;
    private Image bufferImage;
    private int imageID;
    private String imageNames[] = new String[4];
    private final int SPEED = 10;
    private int imageXPos, imageYPos;
    private int positionChange;
    private int speedOfMove;
    private int color;
    private int imageColor;
    private boolean moveFlag;
    private String option3;
    
    private Command extCmd;
    private Display display;
    private Form form;
    private final int CDIV = 5;
    
    
    private Thread thread;
    private Image t;
    
    public GSCanvas1(Display start, Form stform) throws IOException{
        
        
        
        
        form = stform;
        display = start;
        
        imageXPos = getWidth()/2;
        imageYPos = getHeight()/2;
        
        random = new Random(System.currentTimeMillis());
        speedOfMove = SPEED;
        imageID = 0;
        moveFlag = false;
        option3 = new String("Off");
        
        setImages();
        
        color = makeColor(0);
        imageColor = makeColor(1);
        
        makeImage(getImage(0));
        
        extCmd = new Command("Exit", Command.EXIT, 1);
        addCommand(extCmd);
        setCommandListener(this);
        
        thread = new Thread(this, "Game Thread");
        thread.start();
        
    }
    
    private void setImages(){
        
        imageNames[0] = "/img/mo.png"; 
        imageNames[1] = "/slidesshow/2.png";
        imageNames[2] = "/slidesshow/3.png";
        imageNames[3] = "/slideshow/4.png";
    }
    
    private String getImage(int iNum) {
             
                    String gImage;
                    if (iNum < imageNames.length) {
            
                             gImage = imageNames[iNum];

                    }
                    else{
                            
                             gImage = imageNames[0];
            
                    }
                    
                   return gImage;
          }

          public void makeImage(String imageName) {
                    
                   bufferImage = null;
                   movingImage = null;
        
                   
                   try{
                             
//                             if(isDoubleBuffered()){
////                                 
//                                      bufferImage = Image.createImage(imageName);
//////                                       t=Image.createImage("/img/mo.png");
//                                      buffer = bufferImage.getGraphics();
////                                 
//                             }
                             
                             System.out.println(imageName);
                             movingImage = Image.createImage(imageName);
                       
                   }
                   catch(IOException e){
                       
//                       System.out.println(imageName);
                       System.out.println("image is not loaded");
                       
                   }
              
              
          }
          
          public void run(){
                    
                    while(true){
                            
                             int delayOfLoop = 1000 / 20;
                             long loopStartTime = System.currentTimeMillis();
                             runGame();
                             long loopEndTime = System.currentTimeMillis();
                             int loopTime = (int)(loopEndTime - loopStartTime);
                             if(loopTime < delayOfLoop){
                                 
                                 try{
                                     
                                     thread.sleep(delayOfLoop - loopTime);
                                     
                                 }
                                 catch(Exception e){
                                     
                                      e.toString();
                                     
                                 }
                                 
                             }
                        
                    }
              
          }
          
          public void runGame(){
                  
                   checkBoundaries();
                   switch(positionChange){
                       
                       case LEFT:
                           imageXPos   -= speedOfMove;
                           break;
                       case RIGHT:
                           imageXPos   += speedOfMove;
                           break;
                       case UP:
                           imageYPos   -= speedOfMove;
                           break;
                       case DOWN:
                           imageYPos   += speedOfMove;
                           break;
                           
                   }
                   repaint();
                   serviceRepaints();
                  
          }
          
          void checkBoundaries(){
              
              if(imageXPos < 20){
                  
                  imageXPos = 21;
                  
              }
              if(imageXPos > getWidth()){
                  
                  imageXPos = getWidth() - 1;
                  
              }
              if(imageYPos < 40){
                  
                  imageYPos = 41;
                  
              }
              if(imageYPos < getHeight()){
                  
                  imageYPos = getHeight() - 1;
                  
              }
              
              
          }
          
          protected  void paint(Graphics graphics) {
                    Graphics buffContext = graphics;
                    
//                    if(!isDoubleBuffered()){
//                        
//                        graphics = buffer;
//                        
//                    }
                    
                    graphics.setColor(color);
                    
                    graphics.fillRect(0, 0, getWidth(), getHeight());
                    
                    graphics.setColor(imageColor);
                    graphics.drawArc(25, 25 , movingImage.getWidth(), movingImage.getHeight(), 0, 360);
                    graphics.fillArc(25, 25 , movingImage.getWidth(), movingImage.getHeight(), 0, 360);
                    
                    graphics.drawImage(movingImage, 100, 100, Graphics.VCENTER | Graphics.HCENTER);
                    
//                    if(!isDoubleBuffered()){
//                        
//                        buffContext.drawImage(bufferImage, 0, 0, Graphics.TOP | Graphics.LEFT);
//                        
//                    }
                    
                    drawDivisons(graphics);
                    showPosition(graphics);
                    detectCollision(graphics);
         }
          
          void drawDivisons(Graphics graphics){
              
              graphics.setColor(0, 0, 255);
              graphics.setStrokeStyle(Graphics.DOTTED);
              graphics.drawLine(0, getHeight() / CDIV, getWidth() , getHeight() / CDIV);
              graphics.drawLine(0, 2 * getHeight() / CDIV, 2 * getWidth(), 2 * getHeight() / CDIV);
              graphics.drawLine(getWidth() / CDIV, 0, getWidth() / CDIV, getHeight());
              graphics.drawLine(2 * getWidth() / CDIV, 0, 2 * getWidth() / CDIV,2 * getHeight());
              
          }
          
          void showPosition(Graphics graphics){
              
              graphics.setColor(0xf0fff0);
              String xCoord = String.valueOf(imageXPos);
              String yCoord = String.valueOf(imageYPos);
              
              graphics.setColor(0x0f0f0f);
              graphics.drawString("Position : x : " + xCoord + "\t y: " + yCoord, 0, 0, 0);
              graphics.drawString("Option 3: " + option3, getWidth() / CDIV, 0, 0);
               
          }
          
          void detectCollision(Graphics graphics){
              
              String message = new String();
              if(imageXPos < getWidth() / CDIV && imageYPos < getHeight() / CDIV){
                  message = "Top Left";
              }
              if(imageXPos > 2 * getWidth() /CDIV && imageYPos < getHeight() / CDIV){
                  message = "Top Right";
              }
              if(imageXPos < getWidth() / CDIV && imageYPos > 2 * getHeight() / CDIV){
                  message = "Lower Left";
              }
              if(imageXPos > 2 * getWidth() / CDIV && imageYPos > 2 * getHeight() / CDIV ){
                  message = "Lower Right";
              }
              if((imageXPos > getWidth() / CDIV && imageXPos < 2 * getWidth() / CDIV ) && imageYPos > getHeight() / CDIV && imageYPos < 2 * getHeight() / CDIV){
                  message = " In The Centre";
              }
              
              graphics.drawString(message, 0, getHeight() / 2, 0);
              
          }

          protected void keyPressed(int keyCode){
              
              if(keyCode > 0 && keyCode != 6){
                  System.out.println("Key Pressed" + ((char)keyCode));
              
              switch((char)keyCode){
                  
                  case '1':
                      imageColor = makeColor(1);
                      break;
                  case '3':
                      if(moveFlag == false){
                          moveFlag = true;
                          option3 = "On";
                      }
                      else{
                          
                          moveFlag = false;
                          option3 = "Off";
                          
                      }
                      break;
                  case '5':
                      color = makeColor(1);
                      break;
                  case '7':
                      imageID++;
                      if(imageID > 3){
                          imageID = 0;
                          
                      }
                      System.out.println("Inside 7" + ((char)keyCode));
                      makeImage(getImage(imageID));
                      break;
                  case '9':
                      color = makeColor(0);
                      break;
              }
            }
            else{
          
                System.out.println("KeyPressed action " + getGameAction(keyCode));
                switch(getGameAction(keyCode)){
                    
                    case Canvas.LEFT:
                        positionChange = LEFT;
                        break;
                    case Canvas.RIGHT:
                        positionChange = RIGHT;
                        break;
                    case Canvas.UP:
                        positionChange = UP;
                        break;
                    case Canvas.DOWN:
                        positionChange = DOWN;
                        break;
                    
                }    
                
                      
            }
            
        }
          
        protected void keyReleased(int keyCode){
            if(moveFlag == false){
                positionChange = 0;
            }
        }
        
        private int makeColor(int clr){
            
            int colorVal = 0;
            if(clr == 0){
                colorVal = 0xf0fff0 ;;
            }
            if(clr == 1){
                
                colorVal = random.nextInt()&0xFFFFFF;
                
            }
            return colorVal;
        }
        
        public void commandAction(Command command, Displayable displayable){
            
            display.setCurrent(form);
            form.append("Game Over");
            
        }
}

