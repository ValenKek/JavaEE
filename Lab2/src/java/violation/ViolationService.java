package violation;

import idGenerators.CurrentTime;
import idGenerators.IdGenerator;
import idGenerators.NumInDbAndCurrentTime;
import idGenerators.NumberInDB;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
// * @author 1
 */
@Named
public class ViolationService {
    @Inject @NumberInDB
    private IdGenerator idGenerator;
   
    @Inject @Added
    private Event<Violation> violationAddedEvent;
    @Inject @Edited
    private Event<Violation> violationEditedEvent;
    
    /*public ViolationService() {
        this.idGenerator = new IdGeneratorByCurrentDateTime();
    }*/
    public Violation createViolation(String number, String owner, String type, LocalDateTime time, float fine){
        Violation v = new Violation(idGenerator.generate(), number, owner, type, time, fine);
        violationAddedEvent.fire(v);
        return v;
    }
    public Violation createViolation(String ID, String number, String owner, String type, LocalDateTime time, float fine){
        Violation v = new Violation(ID, number, owner, type, time, fine);
        violationEditedEvent.fire(v);
        return v;
    }

}
