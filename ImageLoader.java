/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.io.*;

/**
 * @author ZMQ-10
 */
public class ImageLoader extends MIDlet implements CommandListener, Runnable{
    
    private Display display;
    private Form form;
    Command exit;
    
    public ImageLoader(){
        
        display =Display.getDisplay(this);
        exit = new Command("exit", Command.EXIT, 0);
        form = new Form("Connecting...");
        form.addCommand(exit);
        form.setCommandListener(this);
        
        Thread thread = new Thread(this);
        thread.start();
        
    }
    
    public void startApp() {
        
        display.setCurrent(form);
        
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
    
    public void commandAction(Command command, Displayable displayable){
        
        if(command == exit){
            
            destroyApp(true);
            notifyDestroyed();
            
        }
        
    }
    
    public void run(){
        
        HttpConnection hc = null;
        DataInputStream in = null;
        
        try{
            
//            String url = getAppProperty();
            hc = (HttpConnection)Connector.open("https://images.all-free-download.com/images/graphicthumb/green_globe_down_arrow_557.jpg");
            int length = (int)hc.getLength();
            byte[] data = null;
            
            if(length != -1){
                
                data = new byte[length];
                in = new DataInputStream(hc.openInputStream());
                in.readFully(data);
                
            }
            else{
                
                int chunkSize = 512;
                int index = 0;
                int readLenght = 0;
                in = new DataInputStream(hc.openInputStream());
                data = new byte[chunkSize];
                do{
                    
                    if(data.length < index + chunkSize){
                        
                        byte[] newData = new byte[index + chunkSize];
                        System.arraycopy(data, 0, newData, 0, data.length);
                        data = newData;
                        
                    }
                    readLenght = in.read(data, index, chunkSize);
                    index += readLenght;
                    
                }while (readLenght == chunkSize);
                
                length = index;
                
            }
            
            Image image = Image.createImage(data, 0, length);
            ImageItem imageItem = new ImageItem(null, image, 0, null);
            form.append(imageItem);
            form.setTitle("Done.");
            
        }catch(IOException e){
            
            StringItem stringItem = new StringItem(null, e.toString());
            form.append(stringItem);
            form.setTitle("Done.");
            
        }
        finally {
            
            try {
            
                if (in != null) in.close();
                if (hc != null) hc.close();
                
            }
            catch (IOException ioe) {}
            
        }
        
    }
    
}
