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
import java.util.Set;
import javax.annotation.Resource;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.*;

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
    
    @Resource ValidatorFactory factory;
    @Resource Validator validator;
    
    public Violation createViolation(String number, String owner, String type, LocalDateTime time, float fine){
        Violation v = new Violation(idGenerator.generate(), number, owner, type, time, fine);
        Set<ConstraintViolation<Violation>> errors = validator.validate(v);
        if(errors.isEmpty()){
            violationAddedEvent.fire(v);
        }
        else{
            for(ConstraintViolation<Violation> error : errors){
                System.out.println(error);
            }
        }
        return v;
    }
    public Violation createViolation(String ID, String number, String owner, String type, LocalDateTime time, float fine){
        Violation v = new Violation(ID, number, owner, type, time, fine);
         Set<ConstraintViolation<Violation>> errors = validator.validate(v);
        if(errors.isEmpty()){
            violationEditedEvent.fire(v);
        }
        else{
            for(ConstraintViolation<Violation> error : errors){
                System.out.println(error);
            }
        };
        return v;
    }

}
