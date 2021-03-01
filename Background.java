
import javax.microedition.lcdui.Graphics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ZMQ-10
 */
public class Background {
    
    protected int width, height;
    
    public Background(int w, int h){
        
        width = w;
        height = h;
        
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    public void draw(Graphics graphics){
        // Save the old color
        int color = graphics.getColor();
        // Fill with white
        graphics.setColor(255, 255, 255);
        graphics.fillRect(0, 0, width, height);
        // Restore the old color
        graphics.setColor(color);
    
    }
    
}


