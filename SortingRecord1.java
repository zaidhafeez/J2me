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
public class SortingRecord1 extends MIDlet implements CommandListener{
    
    Display display;
    Command start, exit;
    Form form;
    Alert alert;
    RecordStore recordStore = null;
    RecordEnumeration re = null;
    Comparator1 comparator = null;
    
    public SortingRecord1(){
        
        display = Display.getDisplay(this);
        start = new Command("Start", Command.OK, 0);
        exit = new Command("Exit", Command.EXIT, 1);
        form = new Form("Mixed Data");
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
                
               recordStore = RecordStore.openRecordStore("Mixed Data Record", true); 
                
            } 
            catch (Exception e) {
                
                e.toString();
                
            }
            try{

                byte[] output;
                String[] name = {"Mohammad", "Zaid", "Hafeez"};
                int[] age = {15, 10, 20};
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                for(int i = 0; i < 3; i++){
                    
                    dataOutputStream.writeUTF(name[i]);
                    dataOutputStream.writeInt(age[i]);
                    dataOutputStream.flush();
                    output = outputStream.toByteArray();
                    recordStore.addRecord(output, 0, output.length);
                    outputStream.reset();
                }
                
                
                outputStream.close();
                dataOutputStream.close();
                
            } 
            catch (Exception e) {
                
                e.toString();
                
            }
            try{
                
//                String[] inputString = new String[3];
//                int z = 0;
                StringBuffer buffer = new StringBuffer();
                
                byte[] data = new byte[300];
                
                ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                comparator = new Comparator1();
                re = recordStore.enumerateRecords(null, comparator, false);
                
                while(re.hasNextElement()){
                    
                    recordStore.getRecord(re.nextRecordId(), data, 0);
                    buffer.append(dataInputStream.readUTF());
                    buffer.append(dataInputStream.readInt());
                    buffer.append("\n");
                    dataInputStream.reset();
                    
                    alert = new Alert("Reading", buffer.toString(), null, AlertType.INFO);
                    alert.setTimeout(Alert.FOREVER);
                    display.setCurrent(alert);
                    
                }
//                inputStream.reset();
//                inputStream.close();
//                dataInputStream.close();
                
                
            }
            catch(Exception e){
            
                e.toString();
            
            }
            try {
                
                recordStore.closeRecordStore();
                
            } catch (Exception e){
                
                e.toString();
                
            }
             try{
                
                if(RecordStore.listRecordStores() != null){
                    
                    RecordStore.deleteRecordStore("Mixed Data Record");
                    comparator.compareClose();
                    re.destroy();
                    
                }
                
            }
            catch(Exception e){
                
                e.toString();
                
            }
            
        }
        
    }
}
class Comparator1 implements RecordComparator{
    
    byte[] comparatorInputData = new byte[300];
    ByteArrayInputStream comparatorInputStream = null;
    DataInputStream comparatorInputDataType = null;
    
    public int compare(byte[] record1, byte[] record2){
        
        int record1int, record2int;
        try{
            
            int maxLen = Math.max(record1.length, record2.length);
            
            if(maxLen > comparatorInputData.length){
                
                comparatorInputData = new byte[maxLen];
                
            }
            
            comparatorInputStream = new ByteArrayInputStream(record1);
            comparatorInputDataType = new DataInputStream(comparatorInputStream);
            
            comparatorInputDataType.readUTF();
            record1int = comparatorInputDataType.readInt();
            
            comparatorInputStream = new ByteArrayInputStream(record2);
            comparatorInputDataType = new DataInputStream(comparatorInputStream);
            
            comparatorInputDataType.readUTF();
            record2int = comparatorInputDataType.readInt();
            
            if(record1int == record2int)
                return RecordComparator.EQUIVALENT;
            else if(record1int < record2int)
                return RecordComparator.PRECEDES;
            else
                return RecordComparator.FOLLOWS;
            
        }
        catch(Exception e){
            
            return RecordComparator.EQUIVALENT;
            
        }
        
    }
    
    public void compareClose(){

        try {
            
            if(comparatorInputStream != null){
            
                comparatorInputStream.close();
            
            }
            if(comparatorInputDataType != null){
            
                comparatorInputDataType.close();
                
            }
        
        }
        catch(Exception e){
            
            e.toString();
            
        }
    
    }

}
