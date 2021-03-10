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
import javax.microedition.rms.*;
import javax.microedition.lcdui.*;
import javax.microedition.io.*;

/**
 * @author ZMQ-10
 */
public class RecordMIDlet extends MIDlet implements CommandListener{
    
    private Display display;
    private Alert alert;
    private Form form;
    private Command exit;
    private Command ok;
    private RecordStore recordStore = null;
    
    public RecordMIDlet(){
    
        display =Display.getDisplay(this);
        exit = new Command("Exit", Command.EXIT, 1);
        ok = new Command("OK", Command.OK, 0);
        form = new Form("Mixed Record");
        form.addCommand(ok);
        form.addCommand(exit);
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
        else if(command == ok){
            
            try{

               recordStore = RecordStore.openRecordStore("File Record", true);

            }
            catch (Exception e) {

                alert = new Alert("Error Creating", e.toString(), null, AlertType.WARNING);
                alert.setTimeout(Alert.FOREVER);
                display.setCurrent(alert);

            }
            
            try {
            
                byte[] outputRecord;
                String name = "Mohammd";
                int age = 15;
                boolean gender = true;
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                dataOutputStream.writeUTF(name);
                dataOutputStream.writeInt(age);
                dataOutputStream.writeBoolean(gender);
                dataOutputStream.flush();
                
                outputRecord = outputStream.toByteArray();
                recordStore.addRecord(outputRecord, 0, outputRecord.length);
                outputStream.reset();
                outputStream.close();
                dataOutputStream.close();
                
            } 
            catch (Exception e) {
                
                alert = new Alert("Error Adding", e.toString(), null, AlertType.WARNING);
                alert.setTimeout(Alert.FOREVER);
                display.setCurrent(alert);
                
            }
            
            try {
                
                String inputString = null;
                int inputAge = 0;
                boolean inputGender = false;
                
                byte[] inputData = new byte[100];
                ByteArrayInputStream inputStream = new ByteArrayInputStream(inputData);
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                for(int x = 1; x <= recordStore.getNumRecords(); x++){
                    
                    recordStore.getRecord(x, inputData, 0);
                    inputString = dataInputStream.readUTF();
                    inputAge = dataInputStream.readInt();
                    inputGender = dataInputStream.readBoolean();
                    inputStream.reset();
                    
                }
                
                inputStream.close();
                dataInputStream.close();
                
                alert = new Alert("Reading..", inputString + " " + inputAge + " " + inputGender, null, AlertType.INFO);
                alert.setTimeout(Alert.FOREVER);
                display.setCurrent(alert);
                
            } 
            catch (Exception e) {
                
                alert = new Alert("Error Reading", e.toString(), null, AlertType.WARNING);
                alert.setTimeout(Alert.FOREVER);
                display.setCurrent(alert);
                
            }
            
            try{

               recordStore.closeRecordStore();

            }
            catch (Exception e) {

                alert = new Alert("Error Closing", e.toString(), null, AlertType.WARNING);
                alert.setTimeout(Alert.FOREVER);
                display.setCurrent(alert);

            }
            
            if(RecordStore.listRecordStores() != null){
                try{

                   RecordStore.deleteRecordStore("File Record");
                   
                }
                catch (Exception e) {

                    alert = new Alert("Error Removing", e.toString(), null, AlertType.WARNING);
                    alert.setTimeout(Alert.FOREVER);
                    display.setCurrent(alert);
                    
                }

            }
        
        }
        
    }
    
}    

