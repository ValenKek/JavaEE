
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 1
 */
public class Violation implements Serializable{
    private String ID;
    private String carNum;
    private String ownerName;
    private String violationType;
    private LocalDateTime dateTime;
    private float fineInUAH;
    
    public void setCarNum(String carNum){
        this.carNum=carNum;
    }
    public String getCarNum(){
        return this.carNum;
    }
    public void setOwnerName(String ownerName){
        this.ownerName=ownerName;
    }
    public String getOwnerName(){
        return this.ownerName;
    }
    public void setViolationType(String violationType){
        this.violationType=violationType;
    }
    public String getViolationType(){
        return violationType;
    }
    public void setDateTime(LocalDateTime datetime){
        this.dateTime = datetime;
    }
    public LocalDateTime getDateTime(){
        return this.dateTime;
    }
    public void setFineInUAH(float fineInUAH){
        this.fineInUAH=fineInUAH;
    }
    public float getFineInUAH(float fineInUAH){
        return this.fineInUAH;
    }
    public String getID(String ID){
        return ID;
    }
    
    public Violation(String number, String owner, String type, LocalDateTime time, float fine){
        this.ID=number+"_"+type.replace(" ", "_")+"_"+time.format(DateTimeFormatter.ISO_DATE);
        this.carNum=number;
        this.ownerName=owner;
        this.violationType=type;
        this.dateTime=time;
        this.fineInUAH=fine;
    }
}
