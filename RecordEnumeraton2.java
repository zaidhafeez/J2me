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
import javax.microedition.io.*;
import javax.microedition.rms.*;

/**
 * @author ZMQ-10
 */
public class RecordEnumeraton2 extends MIDlet implements CommandListener {
    
    Display display;
    RecordStore recordStore = null;
    RecordEnumeration re;
    Command start;
    Command exit;
    Form form;
    Alert alert;
    
    
    public RecordEnumeraton2(){
        
        display = Display.getDisplay(this);
        start = new Command("Start", Command.OK, 0);
        exit = new Command("Exit", Command.OK, 0);
        form = new Form("Record File");
        form.addCommand(exit);
        form.addCommand(start);
        form.setCommandListener(this);
        
    }

    public void startApp(){
        
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
            
            try {
                
                recordStore = RecordStore.openRecordStore("File", true);
                
            } catch (Exception e) {
                
                e.toString();
                
            }
            
            try{
                
                byte[] outputRecord;
                String[] name = {"Mohammad", "Zaid", "Hafeez"};
                int[] age = {12, 15, 17};
                boolean[] gender = {true, false, true};
                
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                for(int i = 0; i < 3; i++){
                    
                    dataOutputStream.writeUTF(name[i]);
                    dataOutputStream.writeInt(age[i]);
                    dataOutputStream.writeBoolean(gender[i]);
                    dataOutputStream.flush();
                    outputRecord = outputStream.toByteArray();
                    recordStore.addRecord(outputRecord, 0, outputRecord.length);
//                    System.out.println(recordStore.getRecordSize(age[i]));
                }
                
                
                outputStream.reset();
                outputStream.close();
                dataOutputStream.close();
                
                
            }
            catch(Exception e){
                
                e.toString();
                
            }
            
            try {
                
                StringBuffer buffer = new StringBuffer();
                
//                System.out.println(recordStore.getSize());
                byte[] inputData = new byte[(recordStore.getSize())];
                
                ByteArrayInputStream inputStream = new ByteArrayInputStream(inputData);
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                
                re = recordStore.enumerateRecords(null, null, false);
                
                while(re.hasNextElement()){
                    
                    recordStore.getRecord(re.nextRecordId(), inputData, 0);
                    buffer.append(dataInputStream.readUTF());
                    buffer.append("\n");
                    buffer.append(dataInputStream.readInt());
                    buffer.append("\n");
                    buffer.append(dataInputStream.readBoolean());
                    buffer.append("\n");
                    
                    alert = new Alert("Reading", buffer.toString(), null, AlertType.INFO);
                    alert.setTimeout(Alert.FOREVER);
                    display.setCurrent(alert);
                    
                    
                }
                
                inputStream.close();
                
            }
            catch (Exception e) {
            
                e.toString();
                
            }
            
            try {
                
                recordStore.closeRecordStore();
                
            } catch (Exception e){
                
                e.toString();
                
            }
            
            try{
                
                if(RecordStore.listRecordStores() != null){
                    
                    RecordStore.deleteRecordStore("File");
                    re.destroy();
                    
                }
                
            }
            catch(Exception e){
                
                e.toString();
                
            }
            
        }
        
    }
}
