package mobileapplication2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.util.*;

/**
 * @author ZMQ-10
 */
public class GSCanvas extends Canvas implements Runnable, CommandListener {

    private Random random;
    private Graphics buffer;
    private Image movingImage;
    private Image bufferImage;
    private int imageID;
    private String imageNames[] = new String[4];
    private final int SPEED = 4;
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
    private final int CDIV = 3;

    private Thread thread;

    public GSCanvas(Display start, Form stform) {

        form = stform;
        display = start;

        imageXPos = getWidth() / 2;
        imageYPos = getHeight() / 2;

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

          private void setImages() {

                    imageNames[0] = "/img/mo.pmg";
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
                             
                             if(isDoubleBuffered()){
                                 
                                      bufferImage = Image.createImage(imageName);
                                      buffer = bufferImage.getGraphics();
                                 
                             }
                             
                             movingImage = Image.createImage(imageName);
                       
                   }
                   catch(Exception e){
                       
                       
                       
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
                    
                    if(!isDoubleBuffered()){
                        
                        graphics = buffer;
                        
                    }
                    
                    graphics.setColor(color);
                    
                    graphics.fillRect(0, 0, getWidth(), getHeight());
                    
                    graphics.setColor(imageColor);
                    graphics.drawArc(imageXPos - movingImage.getWidth() / 2, imageYPos - movingImage.getHeight() / 2, movingImage.getWidth(), movingImage.getHeight(), 180, 360);
                    graphics.fillArc(imageXPos - movingImage.getWidth() / 2, imageYPos - movingImage.getHeight() / 2, movingImage.getWidth(), movingImage.getHeight(), 180, 360);
                    
                    graphics.drawImage(movingImage, imageXPos, imageYPos, Graphics.VCENTER | Graphics.HCENTER);
                    
                    if(!isDoubleBuffered()){
                        
                        buffContext.drawImage(bufferImage, 0, 0, Graphics.TOP | Graphics.LEFT);
                        
                    }
                    
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

}
