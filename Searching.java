/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
import javax.microedition.io.*;

/**
 * @author zaid
 */
public class Searching extends MIDlet implements CommandListener{
    
    Display display;
    RecordStore recordStore = null;
    RecordEnumeration re = null;
    RecordFilter filter = null;
    Form form;
    Command exit, start;
    
    public Searching(){
        
        display = Display.getDisplay(this);
       
        exit = new Command("Exit", Command.EXIT, 0);
        start = new Command("Start", Command.OK, 1);
        
        form = new Form("Search Record");
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
        Filter filt = null;
                
        if(command == exit){
            
            destroyApp(true);
            notifyDestroyed();
            
        }
        else if(command == start){
            
            try{
                
                recordStore = RecordStore.openRecordStore("DataTable", true);
                
            }
            catch(Exception e){
                
                e.toString();
                
            }
            
            try{
                
                
                String[] name = {"Mohammd", "Zaid", "Hafeez"};
                for(int i = 0; i < 3; i++){
                    
                    byte[] output = name[i].getBytes();
                    recordStore.addRecord(output, 0, output.length);
                    
                    
                }
            }
             catch(Exception e){
                     
                e.toString();
                     
             }
            
            try{
                
//                byte[] reading = new byte[300];
//                ByteArrayInputStream inputStream = new ByteArrayInputStream(reading);
//                DataInputStream dataInputStream = new DataInputStream(inputStream);
                filter = new Filter("ha");
                
                re = recordStore.enumerateRecords(filter, null, false);
                if(re.numRecords() > 0){
                    
                    String string = new String(re.nextRecord());
                    
                    Alert alert = new Alert("found", string, null, AlertType.WARNING);
                    alert.setTimeout(Alert.FOREVER);
                    display.setCurrent(alert);
                    
                }
                
                
            }
            catch(Exception e){
                
                e.toString();
                
            }
            
            try{
                
                recordStore.closeRecordStore();
                
            }
            catch(Exception e){
                e.toString();
            }
            
            try{
                
                if(RecordStore.listRecordStores() != null){
                    
                    RecordStore.deleteRecordStore("DataTable");
                    re.destroy();
                    filt.filterClose();
                    
                }
                
                
            }
            catch(Exception e){
                
                e.toString();
                
            }
            
            
        }
        
        
    }
    
}

class Filter implements RecordFilter{
    
    String search = null;
    ByteArrayInputStream inputStream =null;
    DataInputStream dataInputStream = null;
    
    public Filter(String search){
        
        this.search = search.toLowerCase();
        
    }
    
    public boolean matches(byte[] suspect){
        
        String string = new String(suspect).toLowerCase();
        if(string != null && string.equals(search)){
            return true;
        }
        else{
            return false;
        }
        
    }
    
    public void filterClose(){
        
        try{
            
            if(inputStream != null){
                
                inputStream.close();
                
            }
            if(dataInputStream != null){
                
                dataInputStream.close();
                
            }
            
        }
        catch(Exception e){
            e.toString();
        }
    }
    
}
