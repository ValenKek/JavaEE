package violation;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.validation.constraints.*;
import validation.CarNumber;
import validation.TypeOfViolation;
import validation.ValidFineValue;
import validation.ViolationTime;

@ValidFineValue
public class Violation implements Serializable{
    @NotNull(message="ID must be specified") 
    @Size(min=4,max=25)	
    private String ID;
    
    @CarNumber
    private String carNum;
    
    private String ownerName;
    
    @TypeOfViolation(typeOfLiability = "administrative")
    private String violationType;
    
    @ViolationTime
    private LocalDateTime dateTime;
    
    @Min(value=0, message="")
    private float fineInUAH;
    
    public void setCarNum(@CarNumber String carNum){
        this.carNum=carNum;
    }

    public String getCarNum(){
        return this.carNum;
    }
    public void setOwnerName(String ownerName){
        this.ownerName=ownerName;
    }
    
    @NotNull(message="Owner name must be specified at least as  \"Unknown\".") 
    @Size(min=3,max=100)
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
    public String getDateTimeAsString(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        return this.dateTime.format(dtf);
    }
    public void setFineInUAH(float fineInUAH){
        this.fineInUAH=fineInUAH;
    }
    public float getFineInUAH(){
        return this.fineInUAH;
    }
    public String getID(){
        return ID;
    }
    
    public Violation(String ID, String number, String owner, String type, LocalDateTime time, float fine){
        this.ID=ID;
        this.carNum=number;
        this.ownerName=owner;
        this.violationType=type;
        this.dateTime=time;
        this.fineInUAH=fine;
    }

    @Override
    public String toString() {
        return  "ID: "+ID+"; CarNum: " + carNum + "; ownerName: " + ownerName + "; ViolationType: " + violationType + "; Date&Time: " + dateTime + "; Fine(in₴): " + fineInUAH + ';';
    }
    
}
