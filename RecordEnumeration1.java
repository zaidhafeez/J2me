/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;

/**
 * @author ZMQ-10
 */
public class RecordEnumeration1 extends MIDlet implements CommandListener{
    
    Display display;
    Command start;
    Command exit;
    Form form;
    Alert alert;
    RecordStore recordStore = null;
    RecordEnumeration re = null;
    
    public RecordEnumeration1(){
        
        display = Display.getDisplay(this);
        start = new Command("Start", Command.OK, 0);
        exit = new Command("Exit", Command.EXIT, 1);
        form = new Form("Record File");
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
        
        if(command == exit){
            
            destroyApp(true);
            notifyDestroyed();
            
        }
        else if(command == start){
        
            try{
                
                recordStore = RecordStore.openRecordStore("File", true);
                
            }
            catch (Exception e) {
                
                alert = new Alert("Not Creating", e.toString(), null, AlertType.WARNING);
                alert.setTimeout(Alert.FOREVER);
                display.setCurrent(alert);
            
            }
            
            try{
                
                String[] name = {"Mohammad ", "Zaid ", "Hafeez"};
                
                for(int i = 0; i < 3; i++){
                    
                    byte[] dataName = name[i].getBytes();
                    recordStore.addRecord(dataName, 0, dataName.length);
                    
                }
                
            } 
            catch (Exception e) {
            
                alert = new Alert("Not Loading", e.toString(), null, AlertType.WARNING);
                alert.setTimeout(Alert.FOREVER);
                display.setCurrent(alert);
                
            }
            
            
            try {
                
                StringBuffer buffer = new StringBuffer();
                re = recordStore.enumerateRecords(null, null, false);
                
                while(re.hasNextElement()){
                    
                    buffer.append(new String(re.nextRecord()));
                    buffer.append("\n");
                    
                }
                
                alert = new Alert("Reading", buffer.toString(), null, AlertType.INFO);
                alert.setTimeout(Alert.FOREVER);
                display.setCurrent(alert);
                
            } catch (Exception e) {
                
                alert = new Alert("Not Reading", e.toString(), null, AlertType.WARNING);
                alert.setTimeout(Alert.FOREVER);
                display.setCurrent(alert);
                
            }
            
            try {
                
                recordStore.closeRecordStore();
                
            } 
            catch (Exception e) {
                
                alert = new Alert("Not Closing", e.toString(), null, AlertType.WARNING);
                alert.setTimeout(Alert.FOREVER);
                display.setCurrent(alert);
            
            }
            
            try {
                
                if(RecordStore.listRecordStores() != null){
                    
                    recordStore.deleteRecordStore("File");
                    re.destroy();
                    
                }
                
            } catch (Exception e) {
                
                alert = new Alert("Not Deleting", e.toString(), null, AlertType.WARNING);
                alert.setTimeout(Alert.FOREVER);
                display.setCurrent(alert);
                
            }
        
        }
        
    }
}
