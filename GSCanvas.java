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
public class GSCanvas extends Canvas implements Runnable, CommandListener{
    
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
    
    public GSCanvas(Display start, Form stform){
        
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
        
        imageNames[0] = "/img/mo.pmg"; 
        imageNames[1] = "/slidesshow/2.png";
        imageNames[2] = "/slidesshow/3.png";
        imageNames[3] = "/slideshow/4.png";
    }
    
    private String getImage(int iNum){
        
    }
    
    public void makeImage(String imageName){
        
    }
    
    public void paint(Graphics graphics){
        
    }
    
}
