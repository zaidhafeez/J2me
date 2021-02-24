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
public class KeyCodeExample extends MIDlet{
    
    private Display display;
    private MyCanvas myCanvas;
    
    public KeyCodeExample(){
        display = Display.getDisplay(this);
        myCanvas = new MyCanvas(this);
    }
    
    
    protected void startApp() {
        display.setCurrent(myCanvas);
    }

    protected void pauseApp() {
    }

    protected void destroyApp(boolean unconditional) {
    }
    
    public void exitMidlet(){
        destroyApp(true);
        notifyDestroyed();
    }
    
}

class MyCanvas extends Canvas implements CommandListener{
    
    private Command exit;
    private String direction = null;
    private KeyCodeExample keyCodeExample;
    
    public MyCanvas(KeyCodeExample keyCodeExample){
        direction = "2=up 8=dn 4=lt 6=rt";
        this.keyCodeExample =keyCodeExample;
        exit = new Command("Exit", Command.EXIT, 1);
        addCommand(exit);
        setCommandListener(this);
    }
    
    protected void paint(Graphics graphics){
        graphics.setColor(255, 255, 255);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.setColor(255,0,0);
        graphics.drawString(direction, 0, 0, Graphics.TOP | Graphics.LEFT);
    }
    
    public void commandAction(Command command, Displayable displayable) {
        if(command == exit){
            keyCodeExample.exitMidlet();
        }
    }
    protected void keyPressed(int key){
        switch(key){
            case KEY_NUM2:
                direction ="up";
                break;
            case KEY_NUM8:
                direction ="down";
                break;
            case KEY_NUM4:
                direction ="left";
                break;
            case KEY_NUM6:
                direction ="right";
                break;
        }
        repaint();
    }
}

