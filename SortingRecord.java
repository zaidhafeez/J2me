/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
import javax.microedition.io.*;

/**
 * @author ZMQ-10
 */
public class SortingRecord extends MIDlet implements CommandListener{
    
    Display display;
    Command start;
    Command exit;
    Form form;
    Alert alert;
    RecordStore recordStore = null;
    RecordEnumeration re = null;
    
    public SortingRecord(){
        
        display = Display.getDisplay(this);
        start = new Command("Start", Command.OK, 0);
        exit = new Command("exit", Command.EXIT, 0);
        form = new Form("File1");
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
            
            notifyDestroyed();
            
        }
        else if(command == start){
            
            try {
                
                recordStore = RecordStore.openRecordStore("Data", true);
                
            } 
            catch (Exception e) {
            
                e.toString();
                
            }
            
            try{
                
                String[] name = {"Mohammad", "Zaid", "Hafeez"};
                
                for(int i = 0; i < 3; i++){
                    
                    byte[] dataName = name[i].getBytes();
                    recordStore.addRecord(dataName, 0, dataName.length);
                    
                }
                
            }
            catch (Exception e) {
            
                e.toString();
                
            }
            
            try {
                
                StringBuffer buffer = new StringBuffer();
                Comparator comparator = new Comparator();
                re = recordStore.enumerateRecords(null, comparator, false);
                while(re.hasNextElement()){
                    
                    buffer.append(new String(re.nextRecord()));
                    buffer.append("\n");
                    
                }

                alert = new Alert("Reading", buffer.toString(), null, AlertType.INFO);
                alert.setTimeout(Alert.FOREVER);
                display.setCurrent(alert);
                
            }
            catch (Exception e) {
            
                e.toString();
            
            }
            
            try {
                
                recordStore.closeRecordStore();
                
            }
            catch (Exception e) {
                
                e.toString();
                
            }
            try {
                if(RecordStore.listRecordStores() != null){
                    
                    RecordStore.deleteRecordStore("Data");
                    re.destroy();
                    
                }
                
            }
            catch (Exception e) {
                
                e.toString();
                
            }
            
        }
        
    }
    
}
class Comparator implements RecordComparator{
    
    public int compare(byte[] record1, byte[] record2){
        String s1 = new String(record1);
        String s2 = new String(record2);
        
        int comparison = s1.compareTo(s2);
        System.out.println(comparison);
        
        if(comparison == 0)
            return RecordComparator.EQUIVALENT;
        else if(comparison > 0)
            return RecordComparator.PRECEDES;
        else
            return RecordComparator.FOLLOWS;
        
    }
    
}
