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
public class GameActionExample extends MIDlet {
    
    private Display display;
    private MyCanvasGame myCanvasGame;
    
    public GameActionExample(){
        display = Display.getDisplay(this);
        myCanvasGame = new MyCanvasGame(this);
    }
    

    public void startApp() {
        display.setCurrent(myCanvasGame);
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
        
    }
    
    public void exitMidletGame(){
        destroyApp(true);
        notifyDestroyed();
    }
}

class MyCanvasGame extends Canvas implements CommandListener{
    
    Command exit;
    String message;
    byte x,y = 5;
    GameActionExample gameActionExample;
    
    public MyCanvasGame(GameActionExample gameActionExample){
        x = 5;
        y = 5;
        this.gameActionExample = gameActionExample;
        message = "Use Game Keys";
        exit = new Command("Exit", Command.EXIT, 1);
        addCommand(exit);
        setCommandListener(this);
    }
    
    protected void paint(Graphics graphics){
        graphics.setColor(255, 255, 255);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.setColor(255, 0, 0);
        graphics.drawString(message, x, y, Graphics.TOP | Graphics.LEFT);
    }
    
    public void commandAction(Command command, Displayable displayable){
        if(command == exit){
            gameActionExample.exitMidletGame();
        }
    }
    
    protected void keyPressed(int key){
        switch(getGameAction(key)){
            case Canvas.UP:
                message = "up";
                y--;
                break;
            case Canvas.DOWN:
                message = "down";
                y++;
                break;
            case Canvas.LEFT:
                message = "Left";
                x--;
                break;
            case Canvas.RIGHT:
                message = "Right";
                x++;
                break;
            case Canvas.FIRE:
                message = "FIRE";
                break;
        }
        repaint();
    }
}
