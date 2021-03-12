/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
import javax.microedition.io.*;
/**
 * @author ZMQ-10
 */
public class Searching1 extends MIDlet implements CommandListener{
    
    Display display;
    Form form;
    Command start, exit;
    RecordStore recordStore = null;
    RecordEnumeration re = null;
    Filter recordFilter = null;
    
    public Searching1(){
        
        display = Display.getDisplay(this);
        form = new Form("Record");
        start = new Command("Start", Command.OK, 0);
        exit = new Command("Exit", Command.EXIT, 0);
        form.addCommand(exit);
        form.addCommand(start);
        form.setCommandListener(this);
        
    }

    public void startApp() {
        
        display.setCurrent(form);
//        String string1String = "123";
//                          int a = Integer.parseInt(string1String);
//                            System.out.println(a);
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
            catch(Exception e){
                
                e.toString();
                
            }
            
            try {
                
                byte[] output;
                String[] name = {"Mohammd", "Zaid", "Hafeez"};
                int[] age = {12, 15, 5};
                
                
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                
                
                for(int i = 0; i < 3; i++){
                    
                    dataOutputStream.writeUTF(name[i]);
                    dataOutputStream.writeInt(age[i]);
                    dataOutputStream.flush();
                    output = byteArrayOutputStream.toByteArray();
                    recordStore.addRecord(output, 0, output.length);
                    byteArrayOutputStream.reset();
                    
                }
                byteArrayOutputStream.close();
                dataOutputStream.close();
                
                
                
            } catch (Exception e) {
                
                e.toString();
                
            }
            try {
                
//                StringBuffer buffer = new StringBuffer();

                String string = null;
                byte[] output =new byte[300];
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(output);
                DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
                if(recordStore.getNumRecords() > 0){
                    
                    recordFilter = new Filter("Hafeez");
                    re = recordStore.enumerateRecords(recordFilter, null, false);
                    
                    while (re.hasNextElement()) {
                        recordStore.getRecord(re.nextRecordId(), output, 0);
                        
                        string = dataInputStream.readUTF() + " " + dataInputStream.readInt();
//                        buffer.append(dataInputStream.readUTF());
//                        buffer.append(dataInputStream.readInt());
//                        buffer.append("\n");
//                        dataInputStream.reset();
                            
                            
                        
                        
                        Alert alert = new Alert("Found", string, null, AlertType.WARNING);
                        alert.setTimeout(Alert.FOREVER);
                        display.setCurrent(alert);
                        
                    }
                    
                    byteArrayInputStream.close();
                    
                }
                
            }
            catch (Exception e) {
            
                e.toString();
            
            }
            
            try{
                
                recordStore.closeRecordStore();
                
            }
            catch(Exception e){
                
                e.toString();
                
            }
            
            try {
                
                if(RecordStore.listRecordStores() != null){
                    
                    RecordStore.deleteRecordStore("File");
                    re.destroy();
                    recordFilter.filterClose();
                    
                }
                
            } 
            catch(Exception e) {
            
            
            }
        }
        
    }
}

class Filter implements RecordFilter{
    
    String search = null;
    
    ByteArrayInputStream byteArrayInputStream;
    DataInputStream dataInputStream;
    
    public Filter(String search){
        
        this.search = search;
        
    }
    
    public boolean matches(byte[] suspect){
        
        String string = null;
        try{
           
            byteArrayInputStream = new ByteArrayInputStream(suspect);
            dataInputStream = new DataInputStream(byteArrayInputStream);
            string = dataInputStream.readUTF();
        
        } 
        catch(Exception e){
            
            return false;
            
        }
        if(string != null && string.equals(search)){
            
            return true;
            
        }
        else{
            
            return false;
            
        }
        
    }
    
    public void filterClose(){
    
        try{
            
            if(byteArrayInputStream != null){
                
                byteArrayInputStream.close();
                
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
